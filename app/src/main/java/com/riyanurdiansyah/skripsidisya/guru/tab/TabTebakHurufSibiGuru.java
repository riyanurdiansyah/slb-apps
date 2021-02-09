package com.riyanurdiansyah.skripsidisya.guru.tab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.adapter.BacaHurufGuruAdapter;
import com.riyanurdiansyah.skripsidisya.guru.adapter.TebakHurufAdapter;
import com.riyanurdiansyah.skripsidisya.model.Tebak;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabTebakHurufSibiGuru extends Fragment {
    View view;
    RecyclerView recyclerView;
    TebakHurufAdapter adapter;
    LinearLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_tebak_huruf_sibi_guru,
                container, false);

        recyclerView = view.findViewById(R.id.rvTbkSibiGuru);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        loadData();

        return view;
    }

    private void loadData() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Tebak>> tebakHuruf = api.getTebakHuruf("2");
        tebakHuruf.enqueue(new Callback<List<Tebak>>() {
            @Override
            public void onResponse(Call<List<Tebak>> call, Response<List<Tebak>> response) {
                List<Tebak> data = response.body();
                adapter = new TebakHurufAdapter(data, getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Tebak>> call, Throwable t) {

            }
        });
    }
}