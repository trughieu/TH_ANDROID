package com.example.nguyendangquang_19dh110604;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodRVHolder> {
    ArrayList<Food> arrayList ;
    private RestaurantAdapter.OnRestaurantItemClickListener mListener;

    public FoodAdapter(ArrayList<Food> arrayList) {
        this.arrayList = arrayList;
    }

    public FoodAdapter(ArrayList<Food> arrayList, RestaurantAdapter.OnRestaurantItemClickListener mListener) {
        this.arrayList = arrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public FoodRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_food,parent,false);
        return new FoodRVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRVHolder holder, int position) {
        Food food = arrayList.get(position);
        if (food != null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference profileRef = storageReference.child("foods/"+
                    food.getImage());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(holder.image);
                }
            });
            holder.name.setText(food.getName());
            holder.rate.setText("Rate: " .concat(String.valueOf(food.rate)));
            holder.price.setText(String.valueOf(food.getPrice()));
        }

    }

    @Override
    public int getItemCount() {
        if (arrayList != null)
            return arrayList.size();
        return 0;
    }

    public class FoodRVHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,price,rate;
        public FoodRVHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgdes_top_food);
            name = itemView.findViewById(R.id.tvdes_top_food);
            price  = itemView.findViewById(R.id.tvPrice_top_Food);
            rate    = itemView.findViewById(R.id.tvRate_des_top_food);
        }
    }
}