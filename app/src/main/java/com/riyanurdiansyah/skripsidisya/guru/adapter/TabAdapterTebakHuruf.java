package com.riyanurdiansyah.skripsidisya.guru.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.riyanurdiansyah.skripsidisya.guru.tab.TabBisindoBacaGuru;
import com.riyanurdiansyah.skripsidisya.guru.tab.TabSibiBacaGuru;
import com.riyanurdiansyah.skripsidisya.guru.tab.TabTebakHurufBisindoGuru;
import com.riyanurdiansyah.skripsidisya.guru.tab.TabTebakHurufSibiGuru;

public class TabAdapterTebakHuruf extends FragmentStatePagerAdapter {
    int counttab;

    public TabAdapterTebakHuruf(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new TabTebakHurufBisindoGuru();
            case 1:
                return new TabTebakHurufSibiGuru();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }

}
