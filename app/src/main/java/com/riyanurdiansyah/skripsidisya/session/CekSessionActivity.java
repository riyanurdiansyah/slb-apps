package com.riyanurdiansyah.skripsidisya.session;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.riyanurdiansyah.skripsidisya.LoginActivity;
import com.riyanurdiansyah.skripsidisya.MainActivity;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.admin.activity.AdminActivity;
import com.riyanurdiansyah.skripsidisya.guru.GuruActivity;
import com.riyanurdiansyah.skripsidisya.siswa.SiswaActivity;

public class CekSessionActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String role_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_session);

        sessionManager = new SessionManager(CekSessionActivity.this);
        role_id = sessionManager.getUserDetail().get("role_id");

        if (!sessionManager.isLoggedIn()) {
            login();
        } else {
            if (role_id.equals("1")) {
                startActivity(new Intent(CekSessionActivity.this, AdminActivity.class));
            } else if (role_id.equals("2")){
                startActivity(new Intent(CekSessionActivity.this, SiswaActivity.class));
            } else {
                startActivity(new Intent(CekSessionActivity.this, GuruActivity.class));
            }
            finish();
        }
    }

    private void login() {
        Intent i = new Intent(CekSessionActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        finish();
    }
}