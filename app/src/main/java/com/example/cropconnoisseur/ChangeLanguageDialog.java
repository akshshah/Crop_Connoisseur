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

public class ChangeLanguageDialog extends DialogFragment {
    private static final String TAG = "ChangeLanguageDialog";

    public interface ChangeLanguage{
        void onChangeLanguage(String language);
    }

    private ChangeLanguage changeLanguage;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_language, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Select Language");

        ListView listView = view.findViewById(R.id.languageListView);
        ArrayList<String> languages = new ArrayList<>(Arrays.asList("English","हिंदी"));
        Log.d(TAG, "onCreateDialog: " + languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,languages);

        listView.setAdapter(adapter);

        try {
            changeLanguage = (ChangeLanguage) getActivity();
        }catch (Exception e){
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String lan = languages.get(position).toLowerCase();
                Log.d(TAG, "onItemClick: " + lan);
                changeLanguage.onChangeLanguage(lan);
                //Toast.makeText(getActivity(), languages.get(position), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return builder.show();
    }
}
