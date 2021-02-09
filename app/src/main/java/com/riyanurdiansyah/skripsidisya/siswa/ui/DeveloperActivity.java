package com.riyanurdiansyah.skripsidisya.siswa.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.siswa.SiswaActivity;

public class DeveloperActivity extends AppCompatActivity {

    Window window;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        if (Build.VERSION.SDK_INT > 26) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        back = findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SiswaActivity.class));
            }
        });
    }
}