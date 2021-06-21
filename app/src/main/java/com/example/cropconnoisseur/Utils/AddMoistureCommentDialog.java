package com.example.cropconnoisseur.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.cropconnoisseur.CommentsActivity;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.databinding.DialogAddMoistureCommentBinding;

public class AddMoistureCommentDialog extends DialogFragment {

    private DialogAddMoistureCommentBinding binding;
    private Double moisture;

    public interface AddComment {
        void onAddingComment(String comment, String moisture);
    }

    private AddComment addComment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        moisture = bundle.getDouble("reading");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_moisture_comment, null);

        binding = DialogAddMoistureCommentBinding.bind(view);
        binding.moisture.setText(String.valueOf(moisture));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setView(view);

        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmpty = false;
                closeKeyboard();

                if (binding.comment.getText().toString().trim().equals("")) {
                    binding.comment.setError("Please enter comment");
                    isEmpty = true;
                } else {
                    binding.comment.setError(null);
                    isEmpty = false;
                }

                if (!isEmpty) {
                    try {
                        addComment = (AddComment) getActivity();
                        addComment.onAddingComment(binding.comment.getText().toString(),binding.moisture.getText().toString());
                        dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return builder.create();
    }

    public void closeKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = getActivity().getCurrentFocus();
            if(currentFocusedView != null){
                imm.hideSoftInputFromWindow(currentFocusedView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
