package com.riyanurdiansyah.skripsidisya.admin.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.riyanurdiansyah.skripsidisya.admin.tab.TabGuruAdmin;
import com.riyanurdiansyah.skripsidisya.admin.tab.TabSiswaAdmin;

public class TabAdapterAdmin extends FragmentStatePagerAdapter {

    int counttab;

    public TabAdapterAdmin(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new TabSiswaAdmin();
            case 1:
                return new TabGuruAdmin();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
