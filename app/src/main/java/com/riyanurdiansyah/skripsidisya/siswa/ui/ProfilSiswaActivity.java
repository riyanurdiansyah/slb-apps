package com.riyanurdiansyah.skripsidisya.siswa.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.admin.adapter.SiswaAdminAdapter;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.model.User;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;
import com.riyanurdiansyah.skripsidisya.session.SessionManager;
import com.riyanurdiansyah.skripsidisya.siswa.SiswaActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class ProfilSiswaActivity extends AppCompatActivity {

    Window window;
    ImageView iv_foto, iv_back;
    TextView tv_nis, tv_nama;
    TextInputEditText et_kelas, et_jk, et_hp, et_tanggal, et_alamat;
    SessionManager sessionManager;

    Button btn_edit;

    int edit = 0;
    String kelas, jk, hp, tanggal, alamat, foto, nama, nis;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    String sUser, sHp, sAlamat, sTgl, sJk;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_siswa);

        Locale localeID = new Locale("in", "ID");
        sessionManager = new SessionManager(this);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", localeID);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        btn_edit = findViewById(R.id.ivEditProfil);
        iv_foto = findViewById(R.id.ivProfil);
        iv_back = findViewById(R.id.ivBackProfil);
        tv_nis = findViewById(R.id.tvNisProfil);
        tv_nama = findViewById(R.id.tvNamaProfil);
        et_kelas = findViewById(R.id.etKelasProfil);
        et_jk = findViewById(R.id.etJkProfil);
        et_hp = findViewById(R.id.etHpProfil);
        et_tanggal = findViewById(R.id.etTanggalProfil);
        et_alamat = findViewById(R.id.etAlamatProfil);

        nis = sessionManager.getUserDetail().get("username");

        getById();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
            @Override
            public void onClick(View v) {
                if (edit == 1) {
                    sUser = tv_nis.getText().toString();
                    sHp = et_hp.getText().toString();
                    sJk = Objects.requireNonNull(et_jk.getText()).toString();
                    sTgl = Objects.requireNonNull(et_tanggal.getText()).toString();
                    sAlamat = Objects.requireNonNull(et_alamat.getText()).toString();

                    saveData();
                    btn_edit.setBackgroundResource(R.drawable.ic_edit);
                    btn_edit.setText("");
                    et_alamat.setEnabled(false);
                    et_hp.setEnabled(false);
                    et_tanggal.setEnabled(false);
                    iv_foto.setEnabled(false);
                    edit = 0;
                } else {
                    btn_edit.setTextSize(12);
                    btn_edit.setTextColor(Color.RED);
                    btn_edit.setText("SAVE");
                    btn_edit.setBackgroundResource(0);
                    et_alamat.setEnabled(true);
                    et_hp.setEnabled(true);
                    et_tanggal.setEnabled(true);
                    iv_foto.setEnabled(true);
                    edit = 1;
                }
            }
        });
        et_tanggal.setOnTouchListener((v, event) -> {
            showDate();
            return true;
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SiswaActivity.class));
            }
        });
    }

    private void getById() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<List<User>> siswa = api.getUserById(nis);
        siswa.enqueue(new Callback<List<User>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> data = response.body();
                nama = data.get(0).getNama();
                kelas = data.get(0).getKelas();
                jk = data.get(0).getJenis_kelamin();
                hp = data.get(0).getNo_hp();
                tanggal = data.get(0).getTgl_lahir();
                alamat = data.get(0).getAlamat();
                foto = data.get(0).getFoto();

                tv_nis.setText(nis);
                tv_nama.setText(nama);
                et_kelas.setText(kelas);
                et_jk.setText(jk);
                et_hp.setText(hp);
                et_tanggal.setText(tanggal);
                et_alamat.setText(alamat);

                Glide.with(getApplicationContext())
                        .load(ApiClient.IMAGE_PROFIL + foto)
                        .into(iv_foto);
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                tampilToast("" + t.toString());
            }
        });
    }

    private void tampilToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void saveData() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<AllResponse> update = api.updateSiswa(sUser, sHp, sJk, sTgl, sAlamat);

        update.enqueue(new Callback<AllResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                assert response.body() != null;
                Toast.makeText(getApplicationContext(),
                        response.body().getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SiswaActivity.class));
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<AllResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDate() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    et_tanggal.setText(dateFormatter.format(newDate.getTime()));
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}