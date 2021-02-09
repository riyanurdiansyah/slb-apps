package com.riyanurdiansyah.skripsidisya.guru.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.riyanurdiansyah.skripsidisya.admin.tab.TabGuruAdmin;
import com.riyanurdiansyah.skripsidisya.admin.tab.TabSiswaAdmin;
import com.riyanurdiansyah.skripsidisya.guru.tab.TabBisindoBacaGuru;
import com.riyanurdiansyah.skripsidisya.guru.tab.TabSibiBacaGuru;

public class TabAdapterGuru extends FragmentStatePagerAdapter {
    int counttab;

    public TabAdapterGuru(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new TabBisindoBacaGuru();
            case 1:
                return new TabSibiBacaGuru();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
