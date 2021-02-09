package com.riyanurdiansyah.skripsidisya.guru.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.admin.activity.AdminActivity;
import com.riyanurdiansyah.skripsidisya.admin.adapter.SiswaAdminAdapter;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.GuruActivity;
import com.riyanurdiansyah.skripsidisya.guru.ui.DetailBacaHurufGuruActivity;
import com.riyanurdiansyah.skripsidisya.model.Baca;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class BacaHurufGuruAdapter extends RecyclerView.Adapter<BacaHurufGuruAdapter.ViewHolder> {

    List<Baca> baca;
    Context context;

    public BacaHurufGuruAdapter(List<Baca> baca, Context context) {
        this.baca = baca;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_baca_huruf_guru, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.et_desc.setText(baca.get(position).getPenjelasan());
        Glide.with(context)
                .load(ApiClient.MATERI_BACA + baca.get(position).getMateri())
                .into(holder.iv_foto);

        String foto = ApiClient.MATERI_BACA + baca.get(position).getMateri();
        String penjelasan = baca.get(position).getPenjelasan();
        int metode = baca.get(position).getMetode_id();
        int id = baca.get(position).getId();

        holder.cv_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailBacaHurufGuruActivity.class)
                        .putExtra("id", baca.get(position).getId())
                        .putExtra("materi", foto)
                        .putExtra("penjelasan", penjelasan)
                        .putExtra("metode", metode)
                );
            }
        });

        holder.cv_tambah.setOnLongClickListener(new View.OnLongClickListener() {
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
                        Call<AllResponse> delete = api.deleteBacaHuruf(String.valueOf(id));
                        delete.enqueue(new Callback<AllResponse>() {
                            @EverythingIsNonNull
                            @Override
                            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                                Toast.makeText(context, response.body().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, GuruActivity.class));
                            }

                            @EverythingIsNonNull
                            @Override
                            public void onFailure(Call<AllResponse> call, Throwable t) {
                                holder.dialog.dismiss();
                                Toast.makeText(context, "" + t.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return baca.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_foto;
        EditText et_desc;
        CardView cv_tambah;
        Dialog dialog;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_foto = itemView.findViewById(R.id.ivBacaHurufGuru);
            et_desc = itemView.findViewById(R.id.etDescHurufGuru);
            cv_tambah = itemView.findViewById(R.id.cvTambahBaca);

            dialog = new Dialog(itemView.getContext());
        }
    }
}
