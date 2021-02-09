package com.riyanurdiansyah.skripsidisya;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SlidersAdapter extends SliderViewAdapter<SlidersAdapter.MyViewHolder> {

    List<SliderItem> sliderItems;
    Context context;

    public SlidersAdapter(List<SliderItem> sliderItems, Context context) {
        this.sliderItems = sliderItems;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_slider, null, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .load(sliderItems.get(position).getImage())
                .into(holder.foto);
    }

    @Override
    public int getCount() {
        return sliderItems.size();
    }

    public static class MyViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView foto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.imageSlide);
        }
    }
}
