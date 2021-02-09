package com.riyanurdiansyah.skripsidisya.siswa;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.riyanurdiansyah.skripsidisya.LoginActivity;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.session.SessionManager;
import com.riyanurdiansyah.skripsidisya.siswa.huruf.MenuHurufActivity;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class SiswaActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    SessionManager sessionManager;
    TextView tv_nama, tv_nis;
    String foto, nama, nis;
    ImageView iv_foto;

    LinearLayout logout;
    Dialog dialog;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(SiswaActivity.this);

        dialog = new Dialog(this);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#0399FB"));
        }

        nis = sessionManager.getUserDetail().get("username");
        nama = sessionManager.getUserDetail().get("nama");
        foto = sessionManager.getUserDetail().get("foto");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        drawer.setBackgroundColor(Color.BLACK);

        View view = navigationView.getHeaderView(0);

        tv_nama = view.findViewById(R.id.tvNamaSiswaMain);
        tv_nis = view.findViewById(R.id.tvNisSiswaMain);
        iv_foto = view.findViewById(R.id.ivFotoSiswaMain);
        logout = navigationView.findViewById(R.id.layOffProfil);

        tv_nis.setText(nis);
        tv_nama.setText(nama);
        Glide.with(this)
                .load(ApiClient.IMAGE_PROFIL + foto)
                .into(iv_foto);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialog.setContentView(R.layout.dialog_logout);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                dialog.setCancelable(false);

                Button btn_ya = (Button) dialog.findViewById(R.id.btnKeluarDialog);
                Button btn_no = (Button) dialog.findViewById(R.id.btnBatalDialog);

                btn_ya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sessionManager.logoutSession();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
                        finish();
                    }
                });

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profil, R.id.nav_dev, R.id.nav_sekolah)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.siswa, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}