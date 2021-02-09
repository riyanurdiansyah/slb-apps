package com.riyanurdiansyah.skripsidisya.guru.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.riyanurdiansyah.skripsidisya.model.Kategori;
import com.riyanurdiansyah.skripsidisya.siswa.gambar.GambarActivity;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {

    List<Kategori> kategori;
    Context context;

    public KategoriAdapter(List<Kategori> kategori, Context context) {
        this.kategori = kategori;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_kategori, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(ApiClient.KATEGORI_GAMBAR + kategori.get(position).getFoto())
                .into(holder.iv_foto);

        holder.tv_nama.setText(kategori.get(position).getNama());

        holder.iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GambarActivity.class)
                        .putExtra("kategori", kategori.get(position).getId())
                        .putExtra("title", kategori.get(position).getNama())
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategori.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama;
        ImageView iv_foto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.tvNamaKategoriGambar);
            iv_foto = itemView.findViewById(R.id.ivKategoriGambar);
        }
    }
}
