package com.riyanurdiansyah.skripsidisya.guru.ui;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.riyanurdiansyah.skripsidisya.LoadingDialog;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.model.Kategori;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;
import com.riyanurdiansyah.skripsidisya.guru.adapter.KategoriAdapter;

import java.io.File;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static android.app.Activity.RESULT_OK;

public class BacaGambarGuruFragment extends Fragment {

    View view;

    KategoriAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FloatingActionButton fab_tambah;
    Dialog dialog;
    Button btn_tambah, btn_batal;
    EditText et_nama;
    ImageView iv_foto;

    String nama, postPath, mediaPath;
    LoadingDialog loadingDialog;

    public static final int IMG_REQUEST = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_baca_gambar_guru, container, false);

        dialog = new Dialog(getActivity());
        loadingDialog = new LoadingDialog(getActivity());

        recyclerView = view.findViewById(R.id.rvKategoriGuru);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        fab_tambah = view.findViewById(R.id.fabTbhGambarGuru);

        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialog.setContentView(R.layout.layout_tambah_kategori_gambar);
                dialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                dialog.setCancelable(false);

                btn_tambah = dialog.findViewById(R.id.btnTambahKategoriGuru);
                btn_batal = dialog.findViewById(R.id.btnBatalTambahKategoriGuru);
                et_nama = dialog.findViewById(R.id.etTambahKategoriGuru);
                iv_foto = dialog.findViewById(R.id.ivTambahKategoriGuru);


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
                        nama = et_nama.getText().toString();
                        loadingDialog.startLoadingDialog();
                        if (nama.equals("")) {
                            loadingDialog.stopLoadingDialog();
                            Toast.makeText(getActivity(), "Nama kategori tidak boleh kosong",
                                    Toast.LENGTH_SHORT).show();
                        } else if (postPath == null) {
                            loadingDialog.stopLoadingDialog();
                            Toast.makeText(getActivity(), "Foto tidak boleh kosong",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            tambahData();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });
        loadData();
        return view;
    }

    private void tambahData() {
        File foto = new File(mediaPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), foto);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto", foto.getName(), requestBody);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<AllResponse> tambah = api.tambahKategori(partImage,
                RequestBody.create(MediaType.parse("text/plain"), nama));

        tambah.enqueue(new Callback<AllResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                loadingDialog.stopLoadingDialog();
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                loadFragment(new BacaGambarGuruFragment());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<AllResponse> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_guru, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    private void loadData() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Kategori>> kategori = api.getKategori();
        kategori.enqueue(new Callback<List<Kategori>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Kategori>> call, Response<List<Kategori>> response) {
                List<Kategori> data = response.body();
                adapter = new KategoriAdapter(data, getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Kategori>> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getActivity();
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            // Ambil Image Dari Galeri dan Foto
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(Objects.requireNonNull(selectedImage),
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