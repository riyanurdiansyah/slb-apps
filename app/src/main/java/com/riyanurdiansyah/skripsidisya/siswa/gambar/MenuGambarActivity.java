package com.riyanurdiansyah.skripsidisya.siswa.gambar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.GuruActivity;
import com.riyanurdiansyah.skripsidisya.guru.adapter.KategoriAdapter;
import com.riyanurdiansyah.skripsidisya.model.Kategori;
import com.riyanurdiansyah.skripsidisya.siswa.SiswaActivity;
import com.riyanurdiansyah.skripsidisya.siswa.ui.HomeFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuGambarActivity extends AppCompatActivity {

    KategoriAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Window window;

    String title = "Kategori Gambar";

    ImageView iv_back;
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_gambar);

        if (Build.VERSION.SDK_INT > 26) {
            window = getWindow();
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        tv_title = findViewById(R.id.tvTittle);
        iv_back = findViewById(R.id.ivBackToolbar);

        recyclerView = findViewById(R.id.rvKategori);
        layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        loadData();

        tv_title.setText(title);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SiswaActivity.class));
            }
        });
    }

    private void loadData() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Kategori>> kategori = api.getKategori();
        kategori.enqueue(new Callback<List<Kategori>>() {
            @Override
            public void onResponse(Call<List<Kategori>> call, Response<List<Kategori>> response) {
                List<Kategori> data = response.body();
                adapter = new KategoriAdapter(data, getApplicationContext());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Kategori>> call, Throwable t) {
                Toast.makeText(MenuGambarActivity.this, "" + t.toString()
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}