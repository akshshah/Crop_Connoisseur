package com.example.cropconnoisseur;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowAllTopicsDialog extends DialogFragment {

    private static final String TAG = "ShowAllTopicsDialog";
    public interface SelectTopic{
        void onSelectTopic(String topic);
    }

    private SelectTopic selectTopic;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_all_topics,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Choose a topic");

        ListView listView = view.findViewById(R.id.topicsListView);
        ArrayList<String> topics = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.topics)));
        topics.set(0,"All");
        Log.d(TAG, "onCreateDialog: " + topics);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,topics);

        listView.setAdapter(adapter);

        try {
            selectTopic = (SelectTopic) getActivity();
        }catch (Exception e){
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectTopic.onSelectTopic(topics.get(position));
                dismiss();
            }
        });

        return builder.show();
    }
}
