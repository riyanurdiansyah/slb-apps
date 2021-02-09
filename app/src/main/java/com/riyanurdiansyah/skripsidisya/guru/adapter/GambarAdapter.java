package com.riyanurdiansyah.skripsidisya.guru.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.GuruActivity;
import com.riyanurdiansyah.skripsidisya.model.Gambar;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class GambarAdapter extends RecyclerView.Adapter<GambarAdapter.ViewHolder> {

    List<Gambar> gambars;
    Context context;

    public GambarAdapter(List<Gambar> gambars, Context context) {
        this.gambars = gambars;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_gambar, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_nama.setText(gambars.get(position).getNama());

        Glide.with(context)
                .load(ApiClient.GAMBAR + gambars.get(position).getFoto())
                .into(holder.iv_foto);

        int id = gambars.get(position).getId();

        holder.iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = position;
                holder.dialog.show();
                Objects.requireNonNull(holder.dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                holder.dialog.setContentView(R.layout.layout_detail_gambar);
                holder.dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary);
                holder.dialog.setCancelable(false);

                ImageView foto = (ImageView) holder.dialog.findViewById(R.id.ivGambarDetail);
                ImageView close = (ImageView) holder.dialog.findViewById(R.id.ivFotoCloseDetail);
                TextView nama = (TextView) holder.dialog.findViewById(R.id.tvGambarDetail);

                nama.setText(gambars.get(position).getNama());

                Glide.with(holder.itemView.getContext())
                        .load(ApiClient.GAMBAR + gambars.get(index).getFoto())
                        .into(foto);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog.dismiss();
                    }
                });
            }
        });

        holder.iv_foto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.dialog.show();
                holder.dialog.setContentView(R.layout.layout_konfirmasi_hapus);
                holder.dialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                holder.dialog.setCancelable(false);

                Button btn_ya = (Button) holder.dialog.findViewById(R.id.btnYaKonfirmasi);
                Button btn_no = (Button) holder.dialog.findViewById(R.id.btnBatalKonfirmasi);

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog.dismiss();
                    }
                });

                btn_ya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog.dismiss();
                        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                        Call<AllResponse> hapus = api.deleteGambar(String.valueOf(id));
                        hapus.enqueue(new Callback<AllResponse>() {
                            @EverythingIsNonNull
                            @Override
                            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, GuruActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                            @EverythingIsNonNull
                            @Override
                            public void onFailure(Call<AllResponse> call, Throwable t) {
                                Toast.makeText(context, ""+t.toString(), Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, GuruActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                    }
                });

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return gambars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_foto;
        TextView tv_nama;
        Dialog dialog;
        CardView cv_gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dialog = new Dialog(itemView.getContext());
            iv_foto = itemView.findViewById(R.id.ivGambar);
            tv_nama = itemView.findViewById(R.id.tvNamaGambar);
            cv_gambar = itemView.findViewById(R.id.cvGambarGuru);
        }
    }
}
