package com.koit.project_prm391_1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.koit.project_prm391_1.object.Product;
import com.koit.project_prm391_1.ui.menu.TabInDrink;

import java.util.List;


public class DrinkPageAdapter extends FragmentStatePagerAdapter {
    int counttab;
    List<Product> list;
    public DrinkPageAdapter(FragmentManager fm, int behavior, List<Product> list) {
        super(fm, behavior);
        this.counttab=behavior;
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TabInDrink tab = new TabInDrink(list);
        return tab;
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
