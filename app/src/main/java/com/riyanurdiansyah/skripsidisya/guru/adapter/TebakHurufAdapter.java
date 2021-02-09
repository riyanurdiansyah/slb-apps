package com.riyanurdiansyah.skripsidisya.guru.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.riyanurdiansyah.skripsidisya.model.Tebak;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TebakHurufAdapter extends RecyclerView.Adapter<TebakHurufAdapter.ViewHolder> {

    List<Tebak> tebak;
    Context context;

    public TebakHurufAdapter(List<Tebak> tebak, Context context) {
        this.tebak = tebak;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_tebak_huruf, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(ApiClient.MATERI_TEBAK + tebak.get(position).getSoal())
                .into(holder.iv_foto);

        holder.tv_jawaban.setText(tebak.get(position).getJawaban());

        String id = String.valueOf(tebak.get(position).getId());

        holder.cv_tebak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.dialog.show();
                holder.dialog.setCancelable(false);
                holder.dialog.setContentView(R.layout.layout_konfirmasi_hapus);
                holder.dialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
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
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<AllResponse> hapus = apiInterface.deleteTbkHuruf(id);
                        hapus.enqueue(new Callback<AllResponse>() {
                            @Override
                            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                                Toast.makeText(context, response.body().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, GuruActivity.class));
                            }

                            @Override
                            public void onFailure(Call<AllResponse> call, Throwable t) {
                                Toast.makeText(context, "" + t.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.dialog.dismiss();
                    }
                });
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tebak.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_foto;
        TextView tv_jawaban;
        CardView cv_tebak;
        Dialog dialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_foto = itemView.findViewById(R.id.ivTbkHurufGuru);
            tv_jawaban = itemView.findViewById(R.id.tvJawabanTbkHurufGuru);
            cv_tebak = itemView.findViewById(R.id.cvTebakHurufGuru);

            dialog = new Dialog(itemView.getContext());
        }
    }
}
