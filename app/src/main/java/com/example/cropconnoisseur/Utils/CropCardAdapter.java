package com.example.cropconnoisseur.Utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.cropconnoisseur.Fragments.CropDetailsFragment;
import com.example.cropconnoisseur.Model.Crop;
import com.example.cropconnoisseur.ModelActivity;
import com.example.cropconnoisseur.R;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

public class CropCardAdapter extends Item<CropCardAdapter.ViewHolder> {
    private Context context;
    private Crop crop;

    private ImageView cropImage;
    private TextView cropName;
    private CardView cropCard;
    private String fragmentName;

    public CropCardAdapter(Context context, Crop crop,String fragmentName) {
        this.context = context;
        this.crop = crop;
        this.fragmentName = fragmentName;
    }

    @NonNull
    @Override
    public ViewHolder createViewHolder(@NonNull View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        View view = viewHolder.itemView;
        cropImage = view.findViewById(R.id.cropImage);
        cropName = view.findViewById(R.id.cropName);
        cropCard = view.findViewById(R.id.cropCard);

        cropName.setText(crop.getCropName());
        cropImage.setImageResource(crop.getCropDrawable());

        if(fragmentName.equals("AllCrops")){
            cropCard.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("crop",crop);

                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                CropDetailsFragment cropDetailsFragment = new CropDetailsFragment();
                cropDetailsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.contentFrame, cropDetailsFragment).addToBackStack(null);
                fragmentTransaction.commit();

            });
        }
        else if(fragmentName.equals("ModelCrops")){
            Bundle bundle = new Bundle();
            bundle.putString("cropName",crop.getCropName().toLowerCase());
            cropCard.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_selectCropFragment_to_detectDiseaseFragment,bundle));
        }

    }

    @Override
    public int getLayout() {
        return R.layout.crop_card;
    }

    class ViewHolder extends GroupieViewHolder {

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
        }
    }


}
