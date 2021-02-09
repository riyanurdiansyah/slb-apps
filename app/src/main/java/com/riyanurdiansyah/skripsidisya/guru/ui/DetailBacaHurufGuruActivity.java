package com.riyanurdiansyah.skripsidisya.guru.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.GuruActivity;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DetailBacaHurufGuruActivity extends AppCompatActivity {

    ImageView iv_foto;
    EditText et_penjelasan;
    Button btn_ubah;

    TextView tv_title;

    String materi, penjelasan;
    int metode, id;

    public static final int IMG_REQUEST = 2;
    Window window;

    String mediaPath;
    String postPath;
    int kode = 0;

    ApiInterface api;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_baca_huruf_guru);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        api = ApiClient.getClient().create(ApiInterface.class);
        tv_title = findViewById(R.id.tvTittle);
        iv_foto = findViewById(R.id.ivDetail);
        et_penjelasan = findViewById(R.id.etDetail);
        btn_ubah = findViewById(R.id.btnUbahDetail);

        Intent i = getIntent();

        tv_title.setText("Halaman Detail");

        materi = i.getStringExtra("materi");
        penjelasan = i.getStringExtra("penjelasan");
        metode = i.getIntExtra("metode", 0);
        id = i.getIntExtra("id", 0);

        Glide.with(this)
                .load(materi)
                .into(iv_foto);

        et_penjelasan.setText(penjelasan);

        iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, IMG_REQUEST);
            }
        });

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kode == 0) {
                    uploadTanpaFoto();
                } else {
                    uploadFoto();
                }
            }
        });
    }

    private void uploadFoto() {
        String penjelasans = et_penjelasan.getText().toString();

        File foto = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), foto);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("materi", foto.getName(), requestBody);

        Call<AllResponse> upload = api.updateFotoBaca(partImage,
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id)),
                RequestBody.create(MediaType.parse("text/plain"), penjelasans),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(metode)));

        upload.enqueue(new Callback<AllResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                tampilToast(response.body().getMessage());
                startActivity(new Intent(getApplicationContext(), GuruActivity.class));
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<AllResponse> call, Throwable t) {
                tampilToast("" + t.toString());
            }
        });
    }

    private void tampilToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void uploadTanpaFoto() {
        String penjelasans = et_penjelasan.getText().toString();

        Call<AllResponse> update = api.updateBaca(String.valueOf(id), penjelasans);
        update.enqueue(new Callback<AllResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                tampilToast(response.body().getMessage());
                startActivity(new Intent(getApplicationContext(), GuruActivity.class));
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<AllResponse> call, Throwable t) {
                tampilToast("" + t.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            // Ambil Image Dari Galeri dan Foto
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(Objects.requireNonNull(selectedImage),
                    filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            iv_foto.setImageURI(data.getData());
            iv_foto.setImageBitmap(BitmapFactory
                    .decodeFile(mediaPath));

            cursor.close();
            postPath = mediaPath;
            kode = 1;
        }
    }
}