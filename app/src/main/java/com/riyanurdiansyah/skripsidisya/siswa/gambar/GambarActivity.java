package com.riyanurdiansyah.skripsidisya.siswa.gambar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.riyanurdiansyah.skripsidisya.LoadingDialog;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.GuruActivity;
import com.riyanurdiansyah.skripsidisya.guru.adapter.GambarAdapter;
import com.riyanurdiansyah.skripsidisya.guru.adapter.KategoriAdapter;
import com.riyanurdiansyah.skripsidisya.guru.ui.BacaGambarGuruFragment;
import com.riyanurdiansyah.skripsidisya.guru.ui.BacaHurufGuruFragment;
import com.riyanurdiansyah.skripsidisya.model.Gambar;
import com.riyanurdiansyah.skripsidisya.model.Kategori;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;
import com.riyanurdiansyah.skripsidisya.session.SessionManager;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class GambarActivity extends AppCompatActivity {

    GambarAdapter adapter;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    Window window;

    int kategori_id, selection;
    String title;

    ApiInterface api;
    TextView tv_title;
    ImageView  iv_back, iv_plus;
    Spinner spinner;
    Dialog dialog;

    String nama_gambar;
    String nama, postPath, mediaPath;
    LoadingDialog loadingDialog;

    ImageView iv_foto;


    SessionManager sessionManager;
    int role;

    public static final int IMG_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gambar);

        api = ApiClient.getClient().create(ApiInterface.class);
        dialog = new Dialog(this);
        loadingDialog = new LoadingDialog(this);

        sessionManager = new SessionManager(this);

        if (Build.VERSION.SDK_INT > 26) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        iv_back = findViewById(R.id.ivBackToolbarPlus);
        iv_plus = findViewById(R.id.ivPlus);
        tv_title = findViewById(R.id.tvTittlePlus);

        role = Integer.parseInt(sessionManager.getUserDetail().get("role_id"));

        kategori_id = getIntent().getIntExtra("kategori", 0);
        title = getIntent().getStringExtra("title");

        tv_title.setText(title);

        recyclerView = findViewById(R.id.rvGambar);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        loadData();

        if (role == 2){
            iv_plus.setVisibility(View.INVISIBLE);
        }

        iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialog.setContentView(R.layout.layout_tambah_gambar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(false);

                spinner = dialog.findViewById(R.id.spinnerTbhGambar);

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<List<Kategori>> kategori = api.getKategori();
                kategori.enqueue(new Callback<List<Kategori>>() {
                    @EverythingIsNonNull
                    @Override
                    public void onResponse(Call<List<Kategori>> call, Response<List<Kategori>> response) {
                        List<Kategori> data = response.body();
                        List<String> listSpinner = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            String nama = data.get(i).getNama();
                            listSpinner.add(nama);
                        }
                        ArrayAdapter<String> adapters = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.layout_kategori_gambar_spinner, listSpinner);
                        adapters.setDropDownViewResource(R.layout.layout_kategori_gambar_sp_dropdown);
                        spinner.setAdapter(adapters);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selection = position +1;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Kategori>> call, Throwable t) {
                        tampilToast("" + t.toString());
                    }
                });

                Button btn_tambah = (Button) dialog.findViewById(R.id.btnTbhGambar);
                Button btn_batal = (Button) dialog.findViewById(R.id.btnBatalTbhGambar);
                EditText et_nama = (EditText) dialog.findViewById(R.id.etTambahGambar);
                iv_foto = dialog.findViewById(R.id.ivTambahGambar);

                iv_foto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, IMG_REQUEST);
                    }
                });

                btn_batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_tambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingDialog.startLoadingDialog();
                        nama = et_nama.getText().toString();

                        if (nama.isEmpty()) {
                            loadingDialog.stopLoadingDialog();
                            tampilToast("Nama gambar tidak boleh kosong");
                        } else if (postPath == null) {
                            loadingDialog.stopLoadingDialog();
                            tampilToast("Foto tidak boleh kosong");
                        } else {
                            tambahData();
                        }
                    }
                });
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role == 2) {
                    startActivity(new Intent(getApplicationContext(), MenuGambarActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), GuruActivity.class));
                }
            }
        });
    }

    private void tambahData() {
        File foto = new File(mediaPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), foto);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto", foto.getName(), requestBody);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<AllResponse> tambah = api.tambahGambar(partImage,
                RequestBody.create(MediaType.parse("text/plain"), nama),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(selection)));
        tambah.enqueue(new Callback<AllResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                loadingDialog.stopLoadingDialog();
                tampilToast(response.body().getMessage());
                startActivity(new Intent(getApplicationContext(), GambarActivity.class)
                        .putExtra("kategori", kategori_id)
                        .putExtra("title", title)
                );
                dialog.dismiss();
                finish();
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<AllResponse> call, Throwable t) {
                tampilToast("" + t.toString());
            }
        });
    }

    private void loadData() {
        Call<List<Gambar>> gambar = api.getGambar(String.valueOf(kategori_id));
        gambar.enqueue(new Callback<List<Gambar>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Gambar>> call, Response<List<Gambar>> response) {
                List<Gambar> data = response.body();
                adapter = new GambarAdapter(data, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.setHasFixedSize(true);
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Gambar>> call, Throwable t) {
                tampilToast("" + t.toString());
            }
        });
    }

    private void tampilToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        }
    }
}