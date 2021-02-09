package com.riyanurdiansyah.skripsidisya.admin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.model.User;

import java.util.List;

public class GuruAdminAdapter extends RecyclerView.Adapter<GuruAdminAdapter.ViewHolder> {

    List<User> guru;
    Context context;

    public GuruAdminAdapter(List<User> guru, Context context) {
        this.guru = guru;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_admin_guru, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_nama.setText(guru.get(position).getNama());
        holder.tv_alamat.setText(guru.get(position).getAlamat());
        holder.tv_nig.setText(guru.get(position).getUsername() );

        Glide.with(context)
                .load(ApiClient.IMAGE_PROFIL + guru.get(position).getFoto())
                .into(holder.iv_foto);
    }

    @Override
    public int getItemCount() {
        return guru.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_foto;
        TextView tv_nama, tv_alamat, tv_nig;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_foto = itemView.findViewById(R.id.ivGuruAdmin);
            tv_nama = itemView.findViewById(R.id.tvNamaGuruAdmin);
            tv_alamat = itemView.findViewById(R.id.tvAlamatGuruAdmin);
            tv_nig = itemView.findViewById(R.id.tvNigGuruAdmin);

        }
    }
}
