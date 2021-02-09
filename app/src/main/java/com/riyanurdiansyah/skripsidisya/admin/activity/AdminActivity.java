package com.riyanurdiansyah.skripsidisya.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.tabs.TabLayout;
import com.riyanurdiansyah.skripsidisya.LoginActivity;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.admin.adapter.TabAdapterAdmin;
import com.riyanurdiansyah.skripsidisya.session.SessionManager;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

     TabLayout tabLayout;
     ViewPager viewPager;
     TabAdapterAdmin tabAdapter;
     Window window;
     Dialog dialog;
     FloatingActionButton btn_tambah_user, btn_keluar;
     FloatingActionsMenu floatingActionsMenu;
     SessionManager sessionManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sessionManager = new SessionManager(this);
        dialog = new Dialog(this);

        if (Build.VERSION.SDK_INT > 26) {
            window = Objects.requireNonNull(this.getWindow());
            window.setStatusBarColor(Color.parseColor("#0399FB"));
        }
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);
        btn_tambah_user = findViewById(R.id.btnTambahAdmin);
        btn_keluar = findViewById(R.id.btnKeluarAdmin);
        floatingActionsMenu = findViewById(R.id.floatAction);

        tabLayout.addTab(tabLayout.newTab().setText("Siswa"));
        tabLayout.addTab(tabLayout.newTab().setText("Guru"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#06547E"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabAdapter = new TabAdapterAdmin(this.getSupportFragmentManager(), tabLayout.getTabCount());
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

        btn_tambah_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                startActivity(new Intent(getApplicationContext(), TambahUserActivity.class));
                overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
            }
        });

        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutSession();
                logout();
            }
        });
    }


    @Override
    public void onBackPressed() {
        logout();
    }

    private void logout() {
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
}