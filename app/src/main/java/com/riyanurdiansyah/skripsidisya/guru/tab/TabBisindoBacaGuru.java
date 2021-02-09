package com.riyanurdiansyah.skripsidisya.guru.tab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.admin.adapter.SiswaAdminAdapter;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.guru.adapter.BacaHurufGuruAdapter;
import com.riyanurdiansyah.skripsidisya.model.Baca;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabBisindoBacaGuru extends Fragment {
    View view;
    RecyclerView recyclerView;
    BacaHurufGuruAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_bisindo_baca_guru,
                container, false);

        recyclerView = view.findViewById(R.id.rvBacaHurufGuru);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        loadData();

        return view;
    }

    private void loadData() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Baca>> baca = api.getBaca("1");
        baca.enqueue(new Callback<List<Baca>>() {
            @Override
            public void onResponse(Call<List<Baca>> call, Response<List<Baca>> response) {
                List<Baca> data = response.body();
                adapter = new BacaHurufGuruAdapter(data, getActivity());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<Baca>> call, Throwable t) {

            }
        });
    }
}