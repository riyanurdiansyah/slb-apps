package com.riyanurdiansyah.skripsidisya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.riyanurdiansyah.skripsidisya.admin.activity.AdminActivity;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.GuruActivity;
import com.riyanurdiansyah.skripsidisya.model.login.LoginRequest;
import com.riyanurdiansyah.skripsidisya.model.login.LoginResponse;
import com.riyanurdiansyah.skripsidisya.session.SessionManager;
import com.riyanurdiansyah.skripsidisya.siswa.SiswaActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class LoginActivity extends AppCompatActivity {

    Window window;
    EditText et_nomor, et_pass;
    Button btn_masuk;
    LoadingDialog loadingDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inisialisasi class loading dialog
        loadingDialog = new LoadingDialog(this);

        sessionManager = new SessionManager(this);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        //inititalisasi xml
        et_nomor = findViewById(R.id.etNomorLogin);
        et_pass = findViewById(R.id.etPasswordLogin);
        btn_masuk = findViewById(R.id.btnMasuk);

        //function login
        btn_masuk.setOnClickListener(v -> {
            loadingDialog.startLoadingDialog();

            //inisialisasi variabel
            //ambil data dari edit text
            String username = et_nomor.getText().toString();
            String password = et_pass.getText().toString();

            //cek kondisi isi edit text
            if (username.isEmpty()) {
                //close loading dialog
                loadingDialog.stopLoadingDialog();

                //jika kolom nomor induk kosong
                tampilToast("Nomor induk tidak boleh kosong");
            } else if (password.isEmpty()) {
                //close loading dialog
                loadingDialog.stopLoadingDialog();

                //jika kolom password kosong
                tampilToast("Password tidak boleh kosong");
            } else {
                //jika tidak ada yg kosong
                //panggil method login
                login(username, password);
            }
        });
    }

    private void login(String username, String password) {
        //inisialisasi model & interface
        //pannggil librari retrofit
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> login = api.login(username, password);
        login.enqueue(new Callback<LoginResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //close loading dialog
                loadingDialog.stopLoadingDialog();

                //jika sukses terhubung dgn database
                //tangkap response dari rest api
                //response tidak boleh kosong
                assert response.body() != null;
                String status = response.body().getStatus();
                //jika status response success
                if (status.equals("success")) {
                    //inisialisasi session
                    // sessionManager = new SessionManager(LoginActivity.this);
                    LoginRequest data = response.body().getData();
                    sessionManager.createSession(data);
                    //ambil data dari session
                    String nama = sessionManager.getUserDetail().get("nama");
                    String role = sessionManager.getUserDetail().get("role_id");

                    tampilToast(""+role);
                    //cek kondisi role id user atau admin
                    assert role != null;
                    if (role.equals("1")) {
                        tampilToast("Selamat datang admin");
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    } else if(role.equals("2")) {
                        tampilToast("Selamat datang"+" " +nama);
                        startActivity(new Intent(getApplicationContext(), SiswaActivity.class));
                    } else {
                        tampilToast("Selamat datang"+" " +nama);
                        startActivity(new Intent(getApplicationContext(), GuruActivity.class));
                    }
                    finish();

                } else {
                    tampilToast(response.body().getMessage());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                tampilToast(""+t.toString());
            }
        });
    }

    private void tampilToast(String data) {
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
    }
}