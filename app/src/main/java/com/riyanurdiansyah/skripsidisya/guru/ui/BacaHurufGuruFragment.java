package com.riyanurdiansyah.skripsidisya.guru.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.riyanurdiansyah.skripsidisya.LoadingDialog;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.admin.adapter.TabAdapterAdmin;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.GuruActivity;
import com.riyanurdiansyah.skripsidisya.guru.adapter.TabAdapterGuru;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import worker8.com.github.radiogroupplus.RadioGroupPlus;

import static android.app.Activity.RESULT_OK;

public class BacaHurufGuruFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapterGuru tabAdapter;
    View view;
    Dialog dialog;
    LoadingDialog loadingDialog;

    FloatingActionButton fab_tambah;
    Bitmap bitmap;

    ImageView iv_tambah;
    EditText et_tambah_baca;

    RadioGroupPlus radioGroupPlus;
    int metode;

    String mediaPath;
    String postPath;


    public static final int IMG_REQUEST = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_baca_huruf_guru, container, false);

        dialog = new Dialog(getActivity());
        loadingDialog = new LoadingDialog(getActivity());

        fab_tambah = view.findViewById(R.id.fabTambahBacaGuru);
        viewPager = view.findViewById(R.id.pagerGuru);
        tabLayout = view.findViewById(R.id.tab_layout_guru);

        tabLayout.addTab(tabLayout.newTab().setText("Bisindo"));
        tabLayout.addTab(tabLayout.newTab().setText("Sibi"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#06547E"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabAdapter = new TabAdapterGuru(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialog.setContentView(R.layout.layout_tambah_baca);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                dialog.setCancelable(false);

                Button btn_ya, btn_no;

                et_tambah_baca = dialog.findViewById(R.id.etPenjelasanTambahBaca);
                iv_tambah = dialog.findViewById(R.id.ivTambahBaca);
                radioGroupPlus = dialog.findViewById(R.id.rgTambahBaca);

                btn_ya = dialog.findViewById(R.id.btnTambahBaca);
                btn_no = dialog.findViewById(R.id.btnBatalTambahBaca);

                iv_tambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, IMG_REQUEST);
                    }
                });

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_ya.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public void onClick(View v) {
                        loadingDialog.startLoadingDialog();
                        if (mediaPath == null) {
                            loadingDialog.stopLoadingDialog();
                            tampilToast("Foto tidak boleh kosong");
                        } else if (et_tambah_baca.getText().toString().equals("")) {
                            loadingDialog.stopLoadingDialog();
                            tampilToast("Deskripsi tidak boleh kosong");
                        } else {
                            int id = 0;
                            if (radioGroupPlus != null) {
                                id = radioGroupPlus.getCheckedRadioButtonId();
                            }
                            switch (id) {
                                case R.id.rbBisindo:
                                    loadingDialog.stopLoadingDialog();
                                    metode = 1;
                                    uploadData();
                                    break;
                                case R.id.rbSibi:
                                    loadingDialog.stopLoadingDialog();
                                    metode = 2;
                                    uploadData();
                                    break;
                                default:
                                    loadingDialog.stopLoadingDialog();
                                    tampilToast("Silahkan pilih bahasa isyarat yang tersedia");
                            }
                        }
                    }
                });
            }
        });

        return view;
    }

    private void uploadData() {

        String penjelasan = et_tambah_baca.getText().toString();

        File foto = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), foto);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("materi", foto.getName(), requestBody);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<AllResponse> tambah = api.tambahBaca(partImage,
                RequestBody.create(MediaType.parse("text/plain"), penjelasan),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(metode)));

        tambah.enqueue(new Callback<AllResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                tampilToast(response.body().getMessage());
                startActivity(new Intent(getActivity(), GuruActivity.class));
                getActivity().finish();
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<AllResponse> call, Throwable t) {
                tampilToast("" + t.toString());
            }
        });
    }

    private void tampilToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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
            iv_tambah.setImageURI(data.getData());
            iv_tambah.setImageBitmap(BitmapFactory
                    .decodeFile(mediaPath));

            cursor.close();

            postPath = mediaPath;
        }
    }
}