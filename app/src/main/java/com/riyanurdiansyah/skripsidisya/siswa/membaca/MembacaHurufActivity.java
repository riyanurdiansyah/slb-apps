package com.riyanurdiansyah.skripsidisya.siswa.membaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.model.Baca;
import com.riyanurdiansyah.skripsidisya.siswa.SiswaActivity;
import com.riyanurdiansyah.skripsidisya.siswa.huruf.MenuHurufActivity;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MembacaHurufActivity extends AppCompatActivity {

    String metode;
    Window window;
    ImageView iv_foto, iv_back;
    TextView tv_penjelasan, tv_title;
    LinearLayout tv_next, tv_previous;

    int index = 0;
    int jumlah = 0;
    int batas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membaca_huruf);

        if (Build.VERSION.SDK_INT > 26) {

            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        iv_foto = findViewById(R.id.ivBaca);
        iv_back = findViewById(R.id.ivBackToolbar);
        tv_penjelasan = findViewById(R.id.tvPenjelasanBaca);
        tv_title = findViewById(R.id.tvTittle);
        tv_next = findViewById(R.id.tvNextBaca);
        tv_previous = findViewById(R.id.tvBackBaca);

        metode = getIntent().getStringExtra("metode");
        tv_title.setText(getIntent().getStringExtra("title"));

        if(index > 0) {
            tv_penjelasan.setVisibility(View.INVISIBLE);
        }

        tv_previous.setVisibility(View.INVISIBLE);

        loadData();

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index != 0) {
                    tv_previous.setVisibility(View.VISIBLE);
                }
                loadData();
            }
        });

        tv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                loadData();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void back() {
        startActivity(new Intent(getApplicationContext(), MenuHurufActivity.class)
                .putExtra("title", "Pilih Membaca Huruf")
                .putExtra("kode", "1"));
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
    }

    private void tampilToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Baca>> baca = api.getBaca(metode);
        baca.enqueue(new Callback<List<Baca>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Baca>> call, Response<List<Baca>> response) {
                List<Baca> data = response.body();
                if (data.size() == 0) {
                    tampilToast("Materi akan segera diupdate");
                    tv_next.setVisibility(View.INVISIBLE);
                    tv_previous.setVisibility(View.INVISIBLE);
                } else {
                    assert data != null;
                    jumlah = data.size();
                    tv_penjelasan.setText(data.get(index).getPenjelasan());
                    Glide.with(MembacaHurufActivity.this)
                            .load(ApiClient.MATERI_BACA + data.get(index).getMateri())
                            .into(iv_foto);
                    batas = index + 1;
                    if (batas == jumlah) {
                        tv_next.setVisibility(View.INVISIBLE);
                    } else if (batas == 1){
                        tv_next.setVisibility(View.VISIBLE);
                        tv_previous.setVisibility(View.INVISIBLE);
                    } else {
                        tv_next.setVisibility(View.VISIBLE);
                        tv_previous.setVisibility(View.VISIBLE);
                    }
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Baca>> call, Throwable t) {
                tampilToast(""+t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }
}