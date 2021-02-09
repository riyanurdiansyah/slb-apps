package com.riyanurdiansyah.skripsidisya.guru.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.riyanurdiansyah.skripsidisya.guru.tab.TabTebakHurufBisindoGuru;
import com.riyanurdiansyah.skripsidisya.guru.tab.TabTebakHurufSibiGuru;
import com.riyanurdiansyah.skripsidisya.guru.tab.TabTebakKataBisindoGuru;
import com.riyanurdiansyah.skripsidisya.guru.tab.TabTebakKataSibiGuru;

public class TabAdapterTebakKata extends FragmentStatePagerAdapter {

    int counttab;

    public TabAdapterTebakKata(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new TabTebakKataBisindoGuru();
            case 1:
                return new TabTebakKataSibiGuru();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }

}
