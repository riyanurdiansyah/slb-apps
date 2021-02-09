package com.riyanurdiansyah.skripsidisya.siswa.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.riyanurdiansyah.skripsidisya.R;

public class DisabilitasFragment extends Fragment implements View.OnClickListener {
    View view;

    ImageView down1, down2, down3, down4;
    LinearLayout lay1, lay2, lay3, lay4;

    int satu = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disabilitas, container, false);

        down1 = view.findViewById(R.id.down1);
        lay1 = view.findViewById(R.id.lay1);

        down1.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.down1:
                if (satu == 0) {
                    lay1.setVisibility(View.VISIBLE);
                    down1.setImageResource(R.drawable.ic_up);
                    satu = 1;
                } else {
                    lay1.setVisibility(View.INVISIBLE);
                    down1.setImageResource(R.drawable.ic_down);
                    satu = 0;
                }
        }
    }
}