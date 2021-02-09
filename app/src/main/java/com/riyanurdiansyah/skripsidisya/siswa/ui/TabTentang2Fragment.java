package com.riyanurdiansyah.skripsidisya.siswa.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riyanurdiansyah.skripsidisya.R;
public class TabTentang2Fragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_tentang2, container, false);

        return view;
    }
}