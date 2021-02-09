package com.riyanurdiansyah.skripsidisya.guru.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
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
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.adapter.TabAdapterGuru;
import com.riyanurdiansyah.skripsidisya.guru.adapter.TabAdapterTebakHuruf;
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
import worker8.com.github.radiogroupplus.RadioGroupPlus;

import static android.app.Activity.RESULT_OK;

public class TebakHurufGuruFragment extends Fragment {

    View view;
    FloatingActionButton fab_tambah;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapterTebakHuruf tabAdapter;
    Dialog dialog;
    LoadingDialog loadingDialog;

    String mediaPath;
    String postPath;

    Button btn_ya, btn_no;
    EditText et_jawaban;
    ImageView iv_soal;
    RadioGroupPlus radioGroupPlus;

    int metode;

    public static final int IMG_REQUEST = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tebak_huruf_guru, container, false);

        dialog = new Dialog(getActivity());
        loadingDialog = new LoadingDialog(getActivity());

        fab_tambah = view.findViewById(R.id.fabTbkHurufGuru);
        viewPager = view.findViewById(R.id.pagerTebakGuru);
        tabLayout = view.findViewById(R.id.tab_layout_tbk);

        tabLayout.addTab(tabLayout.newTab().setText("Bisindo"));
        tabLayout.addTab(tabLayout.newTab().setText("Sibi"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#06547E"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabAdapter = new TabAdapterTebakHuruf(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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
                dialog.setContentView(R.layout.layout_tambah_tebak_huruf);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(false);

                et_jawaban = dialog.findViewById(R.id.etTbhTbkHuruf);
                iv_soal = dialog.findViewById(R.id.ivTbhTbkHuruf);
                radioGroupPlus = dialog.findViewById(R.id.rgTambahTebakHuruf);

                btn_ya = dialog.findViewById(R.id.btnTbkHuruf);
                btn_no = dialog.findViewById(R.id.btnBatalTbkHuruf);

                iv_soal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, IMG_REQUEST);
                    }
                });

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NonConstantResourceId")
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
                            tampilToast("Foto tidak boleh kosong");
                        } else if (et_jawaban.getText().toString().isEmpty()) {
                            tampilToast("Deskripsi tidak boleh kosong");
                        } else {
                            int id = 0;
                            if (radioGroupPlus != null) {
                                id = radioGroupPlus.getCheckedRadioButtonId();
                            }
                            switch (id) {
                                case R.id.rbBisindoTbk:
                                    metode = 1;
                                    uploadData();
                                    break;
                                case R.id.rbSibiTbk:
                                    metode = 2;
                                    uploadData();
                                    break;
                                default:
                                    tampilToast("Silahkan pilih bahasa isyarat yang tersedia");
                            }
                        }
                        dialog.dismiss();
                        loadingDialog.stopLoadingDialog();
                    }
                });
            }
        });

        return view;
    }

    private void uploadData() {
        String jawaban = et_jawaban.getText().toString().toLowerCase();

        File foto = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), foto);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("soal", foto.getName(), requestBody);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<AllResponse> tambah = api.tambahTbkHuruf(
                partImage,
                RequestBody.create(MediaType.parse("text/plain"), jawaban),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(metode)));

        tambah.enqueue(new Callback<AllResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<AllResponse> call, Response<AllResponse> response) {
                loadingDialog.stopLoadingDialog();
                tampilToast(response.body().getMessage());
                loadFragment(new BacaHurufGuruFragment());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<AllResponse> call, Throwable t) {
                tampilToast(""+t.toString());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_guru, fragment, fragment.getClass().getSimpleName())
                .commit();
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
            iv_soal.setImageURI(data.getData());
            iv_soal.setImageBitmap(BitmapFactory
                    .decodeFile(mediaPath));

            cursor.close();

            postPath = mediaPath;
        }
    }
}