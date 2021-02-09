package com.riyanurdiansyah.skripsidisya.siswa.huruf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riyanurdiansyah.skripsidisya.Question;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.model.Tebak;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TebakKataActivity extends AppCompatActivity {

    ImageView iv_soal, iv_back;
    EditText et_jawaban;
    Button btn_jawab;
    TextView tv_title;
    Window window;

    int currentQuestionIndex = 0;
    private int maxQuestionIndex;

    String jawaban;
    String metode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tebak_kata);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        iv_soal = findViewById(R.id.ivSoalKata  );
        iv_back = findViewById(R.id.ivBackToolbar);
        et_jawaban = findViewById(R.id.etJawabanKata);
        btn_jawab = findViewById(R.id.btnJawabKata);


        tv_title = findViewById(R.id.tvTittle);
        tv_title.setText(getIntent().getStringExtra("title"));
        metode = getIntent().getStringExtra("metode");

        loadData();

        btn_jawab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_jawaban.getText().toString().equals(jawaban)) {
                    tampilToast("Jawaban Anda Tepat");
                    et_jawaban.setText("");
                    currentQuestionIndex++;
                    if ((currentQuestionIndex + 1) > maxQuestionIndex) {
                        tampilToast("YEAA KAMU SUDAH BERHASIL MENEBAK SEMUA SOAL");
                        back();
                    } else {
                        loadData();
                    }
                } else {
                    tampilToast("Jawaban Anda Salah");
                }
            }
        });
    }

    private void loadData() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Tebak>> question = api.getTebakKata(metode);
        question.enqueue(new Callback<List<Tebak>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Tebak>> call, Response<List<Tebak>> response) {
                List<Tebak> data = response.body();
                maxQuestionIndex = data.size();
                if (data.size() == 0) {
                    tampilToast("Soal akan segera diupdate");
                } else {
                    jawaban = data.get(currentQuestionIndex).getJawaban();

                    Glide.with(TebakKataActivity.this)
                            .load(ApiClient.MATERI_TEBAK_KATA
                                    + data.get(currentQuestionIndex).getSoal())
                            .into(iv_soal);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Tebak>> call, Throwable t) {
                tampilToast("" + t.toString());
            }
        });
    }

    private void tampilToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void back() {
        startActivity(new Intent(getApplicationContext(), MenuHurufActivity.class)
                .putExtra("title", "Pilih Tebak Huruf")
                .putExtra("kode", "4"));
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
    }

    @Override
    public void onBackPressed() {
        back();
    }
}