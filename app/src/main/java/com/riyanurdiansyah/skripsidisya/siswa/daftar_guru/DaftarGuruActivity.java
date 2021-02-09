package com.riyanurdiansyah.skripsidisya.siswa.daftar_guru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.admin.adapter.GuruAdminAdapter;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.model.User;
import com.riyanurdiansyah.skripsidisya.siswa.SiswaActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DaftarGuruActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GuruAdminAdapter adapter;
    LinearLayoutManager layoutManager;
    ProgressBar progressBar;
    String kode = "3";

    TextView tv_title;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_guru);

        iv_back = findViewById(R.id.ivBackToolbar);
        tv_title = findViewById(R.id.tvTittle);

        progressBar = findViewById(R.id.pbDaftarGuruSiswa);
        recyclerView = findViewById(R.id.rvDaftarGuru);
        layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        tv_title.setText(getIntent().getStringExtra("title"));

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchGuruSiswa);
        assert searchManager != null;
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(this.getComponentName())
        );

        loadGuru("");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadGuru(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadGuru(newText);
                return false;
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void loadGuru(String key) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<List<User>> siswa = api.getUser(key, kode);
        siswa.enqueue(new Callback<List<User>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                progressBar.setVisibility(View.GONE);
                List<User> data = response.body();
                adapter = new GuruAdminAdapter(data, getApplicationContext());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                tampilToast("" + t.toString());
            }
        });
    }

    private void tampilToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void back() {
        startActivity(new Intent(getApplicationContext(), SiswaActivity.class));
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }
}