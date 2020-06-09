package com.koit.project_prm391_1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.koit.project_prm391_1.ui.menu.TabBread;
import com.koit.project_prm391_1.ui.menu.TabDrink;

public class MenuPageAdapter extends FragmentStatePagerAdapter {
    int counttab;
    public MenuPageAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.counttab=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabDrink tabDrink = new TabDrink();
                return tabDrink;
            case 1:
                TabBread tabBread = new TabBread();
                return tabBread;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
