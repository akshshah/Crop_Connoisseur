package com.example.cropconnoisseur.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.cropconnoisseur.Model.MoistureData;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.Utils.AddMoistureCommentDialog;
import com.example.cropconnoisseur.Utils.TableRowAdapter;
import com.example.cropconnoisseur.databinding.FragmentMoistureBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class MoistureFragment extends Fragment {
    private static final String TAG = "MoistureFragment";

    private FragmentMoistureBinding binding;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket socket;
    private BluetoothDevice bluetoothDevice;

    InputStream inputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;

    volatile boolean stopWorker;

    ArrayList<MoistureData> moistureDataArrayList;
    private TableRowAdapter adapter;

    private FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_moisture, container, false);
        binding = FragmentMoistureBinding.bind(view);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        findBT();

        moistureDataArrayList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        binding.tableRecyclerView.setLayoutManager(layoutManager);
        adapter = new TableRowAdapter(moistureDataArrayList,getActivity());
        binding.tableRecyclerView.setAdapter(adapter);

        binding.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(!bluetoothAdapter.enable()){
                        bluetoothAdapter.enable();
                    }
                    findBT();
                } else {
                    closeBT();
                }
            }
        });

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        getActivity().registerReceiver(mReceiver, filter);

        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMoistureCommentDialog commentDialog = new AddMoistureCommentDialog();

                try{
                    double value = Double.parseDouble(binding.data.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putDouble("reading",value);
                    commentDialog.setArguments(bundle);
                    commentDialog.show(getActivity().getSupportFragmentManager(),"Add a comment");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.tableRecyclerView.setVisibility(View.GONE);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MoistureData").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                moistureDataArrayList = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    MoistureData moistureData = snapshot1.getValue(MoistureData.class);
                    moistureDataArrayList.add(moistureData);
                }

                adapter.setData(moistureDataArrayList);
                binding.progressBar.setVisibility(View.GONE);
                binding.tableRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }




    void findBT() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null)
        {
            Toast.makeText(getActivity(), "Bluetooth not supported in this device", Toast.LENGTH_SHORT).show();
            binding.status.setVisibility(View.INVISIBLE);
        }
        else{
            if(!bluetoothAdapter.isEnabled())
            {
                binding.toggle.setChecked(false);
                binding.status.setVisibility(View.INVISIBLE);
            }
            else{

                binding.toggle.setChecked(true);
                binding.status.setVisibility(View.VISIBLE);

                binding.status.setVisibility(View.VISIBLE);

                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                if(pairedDevices.size() > 0)
                {
                    for(BluetoothDevice device : pairedDevices)
                    {
                        if(device.getName().equals("HC-05"))
                        {
                            bluetoothDevice = device;
                            break;
                        }
                    }
                }

                if(bluetoothDevice != null){
                    binding.status.setText("Bluetooth Device Found");
                    try {
                        openBT();
                    } catch (IOException e) {
                        Toast.makeText(getActivity(), "Time out occurred", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    binding.status.setText("Bluetooth Device Not Found");
                }
            }

        }

    }

    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        socket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        socket.connect();
        inputStream = socket.getInputStream();

        beginListenForData();
    }

    void beginListenForData() {
        binding.data.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = inputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            binding.data.setText(data);
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    void closeBT() {
        stopWorker = true;
        binding.status.setVisibility(View.INVISIBLE);
        binding.status.setText("");
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
        }

        try{
            inputStream.close();
            socket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        binding.toggle.setChecked(false);
                        break;
                    case BluetoothAdapter.STATE_ON:
                    case BluetoothAdapter.STATE_TURNING_ON:
                        binding.toggle.setChecked(true);
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Moisture Sensor");
    }
}