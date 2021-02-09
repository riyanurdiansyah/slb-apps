package com.riyanurdiansyah.skripsidisya.siswa.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.siswa.daftar_guru.DaftarGuruActivity;
import com.riyanurdiansyah.skripsidisya.siswa.gambar.MenuGambarActivity;
import com.riyanurdiansyah.skripsidisya.siswa.huruf.MenuHurufActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;
    CardView cv_tebak_huruf, cv_daftar_guru, cv_membaca, cv_membaca_gambar, cv_tebak_kata, cv_disabilitas;
    Window window;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        if (Build.VERSION.SDK_INT > 26) {
            window = requireActivity().getWindow();
            window.setStatusBarColor(Color.parseColor("#0399FB"));
        }

        cv_tebak_huruf = view.findViewById(R.id.cvTebakHuruf);
        cv_daftar_guru = view.findViewById(R.id.cvDaftarGuru);
        cv_tebak_kata = view.findViewById(R.id.cvTebakKataSiswa);
        cv_membaca = view.findViewById(R.id.cvMembacaSiswa);
        cv_membaca_gambar = view.findViewById(R.id.cvMembacaGambarSiswa);
        cv_disabilitas = view.findViewById(R.id.cvDisabilitas);

        cv_tebak_huruf.setOnClickListener(this);
        cv_daftar_guru.setOnClickListener(this);
        cv_membaca.setOnClickListener(this);
        cv_membaca_gambar.setOnClickListener(this);
        cv_disabilitas.setOnClickListener(this);
        cv_tebak_kata.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvMembacaSiswa:
                startActivity(new Intent(getActivity(), MenuHurufActivity.class)
                        .putExtra("title", "Pilih Membaca Huruf")
                        .putExtra("kode", "1")
                );
                requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_in_out);
                break;
            case R.id.cvMembacaGambarSiswa:
                startActivity(new Intent(getActivity(), MenuGambarActivity.class)
                        .putExtra("title", "Pilih Membaca Gambar")
                        .putExtra("kode", 3)
                );
                requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_in_out);
                break;
            case R.id.cvTebakHuruf:
                startActivity(new Intent(getActivity(), MenuHurufActivity.class)
                        .putExtra("title", "Pilih Tebak Huruf")
                        .putExtra("kode", "3")
                );
                requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_in_out);
                break;
            case R.id.cvDaftarGuru:
                startActivity(new Intent(getActivity(), DaftarGuruActivity.class)
                        .putExtra("title", "Daftar Guru")
                );
                requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_in_out);
                break;
            case R.id.cvTebakKataSiswa:
                startActivity(new Intent(getActivity(), MenuHurufActivity.class)
                        .putExtra("title", "Pilih Tebak Kata")
                        .putExtra("kode", "4")
                );
                break;
            case R.id.cvDisabilitas:
                startActivity(new Intent(getActivity(), DisabilitasActivity.class)
                        .putExtra("title", "Tentang Disabilitas")
                );
                break;
            default:
        }
    }

    private void tampilToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
}