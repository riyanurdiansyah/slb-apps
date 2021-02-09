package com.riyanurdiansyah.skripsidisya.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.riyanurdiansyah.skripsidisya.LoadingDialog;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;
import com.riyanurdiansyah.skripsidisya.model.registrasi.RegistrasiRequest;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import worker8.com.github.radiogroupplus.RadioGroupPlus;

public class TambahUserActivity extends AppCompatActivity {

    Window window;
    ImageView iv_back;
    RegistrasiRequest registrasiRequest;
    EditText et_username, et_nama, et_password;

    String username, nama, password, role, jenis_kelamin;
    String hp = "";
    String tgl_lahir = "";
    String alamat = "-";
    String kelas = "-";
    String foto = "default.jpg";

    RadioGroupPlus rg_role;
    Button btn_registrasi;

    Spinner spin_jk;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_user);

        loadingDialog = new LoadingDialog(this);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        rg_role = findViewById(R.id.rgRoleAdmin);
        btn_registrasi = findViewById(R.id.btnRegisAdmin);
        iv_back = findViewById(R.id.ivBackAdmin);
        et_nama = findViewById(R.id.etNamaRegis);
        et_username = findViewById(R.id.etUsernameRegis);
        et_password = findViewById(R.id.etPasswordRegis);
        spin_jk = findViewById(R.id.spinnerJkRegis);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
            }
        });

        btn_registrasi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                int id = 0;
                if (rg_role != null) {
                    id = rg_role.getCheckedRadioButtonId();
                }
                switch (id) {
                    case R.id.rbSiswa:
                        role = "2";
                        registrasi();
                        break;
                    case R.id.rbGuru:
                        role = "3";
                        registrasi();
                        break;
                    default:
                        loadingDialog.stopLoadingDialog();
                        tampilToast("Silahkan pilih role yang tersedia");
                }
            }
        });
    }

    private void registrasi() {
        username = et_username.getText().toString();
        nama = et_nama.getText().toString();
        password = et_password.getText().toString();
        jenis_kelamin = spin_jk.getSelectedItem().toString();

        if (username.isEmpty()) {
            loadingDialog.stopLoadingDialog();
            tampilToast("Username tidak boleh kosong");
        } else if (nama.isEmpty()) {
            loadingDialog.stopLoadingDialog();
            tampilToast("Nama tidak boleh kosong");
        } else if (password.isEmpty()) {
            loadingDialog.stopLoadingDialog();
            tampilToast("Password tidak boleh kosong");
        } else if (password.length() < 8) {
            loadingDialog.stopLoadingDialog();
            tampilToast("Password terlalu pendek");
        } else if (jenis_kelamin.equals("Pilih")) {
            loadingDialog.stopLoadingDialog();
            tampilToast("Silahkan pilih jenis kelamin");
        } else {

            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<AllResponse> register = api.register(username, nama, password, hp,
                    jenis_kelamin, tgl_lahir, alamat, kelas, foto, role);
            register.enqueue(new Callback<AllResponse>() {

                @EverythingIsNonNull
                @Override
                public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                    loadingDialog.stopLoadingDialog();
                    assert response.body() != null;
                    String status = response.body().getStatus();
                    if (status.equals("success")) {
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        tampilToast(response.body().getMessage());
                        finish();
                    } else {
                        tampilToast(response.body().getMessage());
                    }
                }

                @EverythingIsNonNull
                @Override
                public void onFailure(Call<AllResponse> call, Throwable t) {
                    tampilToast("" + t.toString());
                }
            });
        }
    }

    private void tampilToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}