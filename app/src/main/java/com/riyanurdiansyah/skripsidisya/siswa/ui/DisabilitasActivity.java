package com.riyanurdiansyah.skripsidisya.siswa.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.siswa.SiswaActivity;

import java.util.Objects;

public class DisabilitasActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView down1, down2, down3, down4, back;
    LinearLayout lay1, lay2, lay3, lay4;

    int satu = 0;
    int dua = 0;
    int tiga = 0;
    int empat = 0;

    String title;

    TextView tv_title;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disabilitas);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        title = getIntent().getStringExtra("title");

        down1 = findViewById(R.id.down1);
        down2 = findViewById(R.id.down2);
        down3 = findViewById(R.id.down3);
        down4 = findViewById(R.id.down4);

        lay1 = findViewById(R.id.lay1);
        lay2 = findViewById(R.id.lay2);
        lay3 = findViewById(R.id.lay3);
        lay4 = findViewById(R.id.lay4);
        tv_title = findViewById(R.id.tvTittle);
        back = findViewById(R.id.ivBackToolbar);

        tv_title.setText(title);

        down1.setOnClickListener(this);
        down2.setOnClickListener(this);
        down3.setOnClickListener(this);
        down4.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackToolbar:
                back();
                break;
            case R.id.down1:
                if (satu == 0) {
                    lay1.setVisibility(View.VISIBLE);
                    down1.setImageResource(R.drawable.ic_up);
                    satu = 1;
                } else {
                    lay1.setVisibility(View.GONE);
                    down1.setImageResource(R.drawable.ic_down);
                    satu = 0;
                }
                break;
            case R.id.down2:
                if (dua == 0) {
                    lay2.setVisibility(View.VISIBLE);
                    down2.setImageResource(R.drawable.ic_up);
                    dua = 1;
                } else {
                    lay2.setVisibility(View.GONE);
                    down2.setImageResource(R.drawable.ic_down);
                    dua = 0;
                }
                break;
            case R.id.down3:
                if (tiga == 0) {
                    lay3.setVisibility(View.VISIBLE);
                    down3.setImageResource(R.drawable.ic_up);
                    tiga = 1;
                } else {
                    lay3.setVisibility(View.GONE);
                    down3.setImageResource(R.drawable.ic_down);
                    tiga = 0;
                }
                break;
            case R.id.down4:
                if (empat == 0) {
                    lay4.setVisibility(View.VISIBLE);
                    down4.setImageResource(R.drawable.ic_up);
                    empat = 1;
                } else {
                    lay4.setVisibility(View.GONE);
                    down4.setImageResource(R.drawable.ic_down);
                    empat = 0;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        startActivity(new Intent(getApplicationContext(), SiswaActivity.class));
    }
}