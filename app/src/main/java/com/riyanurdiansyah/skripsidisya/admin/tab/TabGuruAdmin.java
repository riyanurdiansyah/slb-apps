package com.riyanurdiansyah.skripsidisya.admin.tab;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.admin.adapter.GuruAdminAdapter;
import com.riyanurdiansyah.skripsidisya.admin.adapter.SiswaAdminAdapter;
import com.riyanurdiansyah.skripsidisya.api.ApiClient;
import com.riyanurdiansyah.skripsidisya.api.ApiInterface;
import com.riyanurdiansyah.skripsidisya.model.User;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TabGuruAdmin extends Fragment {
    View view;
    RecyclerView recyclerView;
    GuruAdminAdapter adapter;
    LinearLayoutManager layoutManager;
    String kode = "3";
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_guru_admin, container, false);

        progressBar = view.findViewById(R.id.pbGuruAdmin);
        recyclerView = view.findViewById(R.id.rvGuruAdmin);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SearchManager searchManager = (SearchManager) requireActivity()
                .getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchGuruAdmin);
        assert searchManager != null;
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName())
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


        return view;
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
                adapter = new GuruAdminAdapter(data, getActivity());
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


    private void tampilToast(String data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }
}