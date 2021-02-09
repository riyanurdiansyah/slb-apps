package com.riyanurdiansyah.skripsidisya;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.riyanurdiansyah.skripsidisya.guru.tab.TabTebakHurufBisindoGuru;
import com.riyanurdiansyah.skripsidisya.guru.tab.TabTebakHurufSibiGuru;
import com.riyanurdiansyah.skripsidisya.siswa.ui.TabTentang2Fragment;
import com.riyanurdiansyah.skripsidisya.siswa.ui.TabTentangFragment;

public class TabTentangAdapter extends FragmentStatePagerAdapter {

    int counttab;

    public TabTentangAdapter(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new TabTentangFragment();
            case 1:
                return new TabTentang2Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
