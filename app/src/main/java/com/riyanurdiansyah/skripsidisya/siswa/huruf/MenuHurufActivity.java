package com.riyanurdiansyah.skripsidisya.siswa.huruf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.siswa.SiswaActivity;
import com.riyanurdiansyah.skripsidisya.siswa.membaca.MembacaHurufActivity;

import java.util.Objects;

public class MenuHurufActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_title;
    ImageView iv_back;
    CardView cv_bisindo, cv_sibi;
    Window window;
    String kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_huruf);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        iv_back = findViewById(R.id.ivBackToolbar);
        cv_bisindo = findViewById(R.id.cvBisindo);
        cv_sibi = findViewById(R.id.cvSibi);

        tv_title = findViewById(R.id.tvTittle);
        tv_title.setText(getIntent().getStringExtra("title"));
        kode = getIntent().getStringExtra("kode");

        iv_back.setOnClickListener(this);
        cv_bisindo.setOnClickListener(this);
        cv_sibi.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackToolbar:
                startActivity(new Intent(getApplicationContext(), SiswaActivity.class));
                overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
                break;
            case R.id.cvBisindo:
                if (kode.equals("1")) {
                    startActivity(new Intent(getApplicationContext(), MembacaHurufActivity.class)
                            .putExtra("title", "Membaca Bisindo")
                            .putExtra("metode", "1")
                    );
                } else if (kode.equals("3")) {
                    startActivity(new Intent(getApplicationContext(), TebakHurufActivity.class)
                            .putExtra("title", "Tebak Huruf Bisindo")
                            .putExtra("metode", "1")
                    );
                } else {
                    startActivity(new Intent(getApplicationContext(), TebakKataActivity.class)
                            .putExtra("title", "Tebak Kata Bisindo")
                            .putExtra("metode", "1")
                    );
                }
                overridePendingTransition(R.anim.slide_in, R.anim.slide_in_out);
                break;
            case R.id.cvSibi:
                if (kode.equals("1")) {
                    startActivity(new Intent(getApplicationContext(), MembacaHurufActivity.class)
                            .putExtra("title", "Membaca Sibi")
                            .putExtra("metode", "2")
                    );
                } else if (kode.equals("3")) {
                    startActivity(new Intent(getApplicationContext(), TebakHurufActivity.class)
                            .putExtra("title", "Tebak Huruf Sibi")
                            .putExtra("metode", "2")
                    );
                } else  {
                    startActivity(new Intent(getApplicationContext(), TebakKataActivity.class)
                            .putExtra("title", "Tebak Kata Sibi")
                            .putExtra("metode", "2")
                    );
                }
                overridePendingTransition(R.anim.slide_in, R.anim.slide_in_out);
                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), SiswaActivity.class));
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
    }
}