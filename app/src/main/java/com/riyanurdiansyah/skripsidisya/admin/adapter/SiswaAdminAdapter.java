package com.riyanurdiansyah.skripsidisya.admin.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.admin.activity.AdminActivity;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.model.User;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class SiswaAdminAdapter extends RecyclerView.Adapter<SiswaAdminAdapter.ViewHolder> {

    List<User> siswa;
    Context context;

    public SiswaAdminAdapter(List<User> siswa, Context context) {
        this.siswa = siswa;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_admin_siswa, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_nama.setText(siswa.get(position).getNama());
        holder.tv_alamat.setText(siswa.get(position).getAlamat());
        holder.tv_jk.setText(siswa.get(position).getJenis_kelamin());
        holder.tv_nis.setText(siswa.get(position).getUsername());
        holder.tv_kelas.setText(siswa.get(position).getKelas());

        Glide.with(context)
                .load(ApiClient.IMAGE_PROFIL + siswa.get(position).getFoto())
                .into(holder.iv_foto);

        String username = siswa.get(position).getUsername();

        holder.btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+siswa.get(position).getNo_hp()));
                context.startActivity(intent);
            }
        });

        holder.cv_siswa.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.dialog.show();
                holder.dialog.setContentView(R.layout.layout_konfirmasi_hapus);
                Objects.requireNonNull(holder.dialog.getWindow()).setBackgroundDrawableResource(
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
                        Call<AllResponse> delete = api.deleteUser(username);
                        delete.enqueue(new Callback<AllResponse>() {
                            @EverythingIsNonNull
                            @Override
                            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                                String status = response.body().getStatus();
                                if (status.equals("success")) {
                                    Toast.makeText(context, response.body().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, AdminActivity.class));
                                } else {
                                    Toast.makeText(context, response.body().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, AdminActivity.class));
                                }
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
                return false;
            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialog.show();
                holder.dialog.setContentView(R.layout.layout_ubah_password);
                Objects.requireNonNull(holder.dialog.getWindow()).setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                holder.dialog.setCancelable(false);

                Button btn_ya = (Button) holder.dialog.findViewById(R.id.btnUbahPass);
                Button btn_no = (Button) holder.dialog.findViewById(R.id.btnBatalPass);

                EditText et_pass = (EditText) holder.dialog.findViewById(R.id.etUbahPassAdmin);
                EditText et_pass2 = (EditText) holder.dialog.findViewById(R.id.etUbahPassAdmin2);


                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog.dismiss();
                    }
                });

                btn_ya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pass = et_pass.getText().toString();
                        String pass2 = et_pass2.getText().toString();
                        if (pass.length() < 8 && pass2.length() < 8) {
                            Toast.makeText(context, "Password terlalu pendek",
                                    Toast.LENGTH_SHORT).show();
                        } else if (!pass.equals(pass2)) {
                            Toast.makeText(context, "Masukkan password dengan benar",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                            Call<AllResponse> ubahPass = api.ubahPass(username, pass);
                            ubahPass.enqueue(new Callback<AllResponse>() {
                                @EverythingIsNonNull
                                @Override
                                public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                                    assert response.body() != null;
                                    Toast.makeText(context, response.body().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                @EverythingIsNonNull
                                @Override
                                public void onFailure(Call<AllResponse> call, Throwable t) {
                                    Toast.makeText(context, ""+t.toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        holder.dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return siswa.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_foto;
        TextView tv_nama, tv_kelas, tv_nis, tv_jk, tv_alamat;
        CardView cv_siswa;
        Dialog dialog;
        Button btn_call, btn_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_foto = itemView.findViewById(R.id.ivSiswaAdmin);
            tv_nama = itemView.findViewById(R.id.tvNamaSiswaAdmin);
            tv_kelas = itemView.findViewById(R.id.tvKelasSiswaAdmin);
            tv_nis = itemView.findViewById(R.id.tvNisSiswaAdmin);
            tv_jk = itemView.findViewById(R.id.tvJkSiswaAdmin);
            tv_alamat = itemView.findViewById(R.id.tvAlamatSiswaAdmin);
            cv_siswa = itemView.findViewById(R.id.cvLayoutSiswa);
            btn_call = itemView.findViewById(R.id.btnCallSiswaAdmin);
            btn_edit = itemView.findViewById(R.id.btnEditSiswaAdmin);

            dialog = new Dialog(itemView.getContext());

        }
    }
}
