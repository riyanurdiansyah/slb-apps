package com.riyanurdiansyah.skripsidisya.guru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.riyanurdiansyah.skripsidisya.LoadingDialog;
import com.riyanurdiansyah.skripsidisya.LoginActivity;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.session.SessionManager;

import java.util.Objects;

public class GuruActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Window window;
    TextView tv_nama, tv_nis;
    String foto, nama, nis;
    ImageView iv_foto;
    LinearLayout logout;
    Dialog dialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(this);
        dialog = new Dialog(this);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#0399FB"));
        }

        nis = sessionManager.getUserDetail().get("username");
        nama = sessionManager.getUserDetail().get("nama");
        foto = sessionManager.getUserDetail().get("foto");

        DrawerLayout drawer = findViewById(R.id.drawer_layout_guru);
        NavigationView navigationView = findViewById(R.id.nav_view_guru);
        View view = navigationView.getHeaderView(0);

        tv_nama = view.findViewById(R.id.tvNamaGuruMain);
        tv_nis = view.findViewById(R.id.tvNigGuruMain);
        iv_foto = view.findViewById(R.id.ivFotoGuruMain);
        logout = navigationView.findViewById(R.id.layOffProfilGuru);

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

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_baca_huruf_guru, R.id.nav_baca_gambar_guru, R.id.nav_tebak_huruf_guru,
                R.id.nav_tebak_kata_guru)
                .setDrawerLayout(drawer)
                .build();

        NavController navControllers = Navigation.findNavController(this, R.id.nav_host_fragment_guru);
        NavigationUI.setupActionBarWithNavController(this, navControllers, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navControllers);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_guru);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        dialog.show();
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

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
}