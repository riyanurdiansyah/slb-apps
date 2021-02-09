package com.riyanurdiansyah.skripsidisya.siswa.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.riyanurdiansyah.skripsidisya.R;
import com.riyanurdiansyah.skripsidisya.SliderItem;
import com.riyanurdiansyah.skripsidisya.SlidersAdapter;
import com.riyanurdiansyah.skripsidisya.TabTentangAdapter;
import com.riyanurdiansyah.skripsidisya.guru.adapter.TabAdapterGuru;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class TentangSekolahFragment extends Fragment {

    View view;
    ViewPager2 viewPager2;
    SliderView sliderView;
    SlidersAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabTentangAdapter tabAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tentang_sekolah, container, false);

        sliderView = view.findViewById(R.id.sliderIvDetail);

        viewPager = view.findViewById(R.id.pagerTentang);
        tabLayout = view.findViewById(R.id.tab_layout_tentang);
        tabLayout.addTab(tabLayout.newTab().setText("Tentang"));
        tabLayout.addTab(tabLayout.newTab().setText("Visi Misi"));
        tabLayout.setTabTextColors(Color.parseColor("#06547E"), Color.parseColor("#06547E"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#06547E"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabAdapter = new TabTentangAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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

        loadFoto();

        return view;
    }

    private void loadFoto() {
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.sekolah));
        sliderItems.add(new SliderItem(R.drawable.sekolah1));
        sliderItems.add(new SliderItem(R.drawable.sekolah2));

        adapter = new SlidersAdapter(sliderItems, getActivity());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }
}