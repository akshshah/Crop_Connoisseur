package com.example.cropconnoisseur.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.cropconnoisseur.Model.Test;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.databinding.TestCardLayoutBinding;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

public class TestCardAdapter extends Item<TestCardAdapter.ViewHolder> {

    private Context context;
    private Test test;
    private TestCardLayoutBinding binding;

    public TestCardAdapter(Context context, Test test) {
        this.context = context;
        this.test = test;
    }

    @NonNull
    @Override
    public ViewHolder createViewHolder(@NonNull View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        View view = viewHolder.itemView;

        binding = TestCardLayoutBinding.bind(view);

        binding.testColor.setBackgroundColor(Color.parseColor(test.getTestColor()));
        binding.testComment.setText(test.getTestComment());
    }

    @Override
    public int getLayout() {
        return R.layout.test_card_layout;
    }

    class ViewHolder extends GroupieViewHolder {

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
        }
    }
}
