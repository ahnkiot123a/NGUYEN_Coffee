package com.koit.project_prm391_1.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.koit.project_prm391_1.R;
import com.koit.project_prm391_1.adapter.DrinkPageAdapter;
import com.koit.project_prm391_1.dao.ProductDAO;
import com.koit.project_prm391_1.object.Category;
import com.koit.project_prm391_1.object.Product;

import java.util.ArrayList;
import java.util.List;

public class TabDrink extends Fragment {
    List<Product> products = new ArrayList<>();
    List<String> cateDrinks = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ProductDAO productDAO= new ProductDAO();
        products = productDAO.getProductByCateId(1);
        cateDrinks = productDAO.getDrinkCateName();
        View root =  inflater.inflate(R.layout.fragment_tab_drink,container,false);
        final TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabLayoutDrink);
        final ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewPagerDink);

        for(String s: cateDrinks){
            tabLayout.addTab(tabLayout.newTab().setText(s));
        }

        final DrinkPageAdapter drinkPageAdapter = new DrinkPageAdapter(getFragmentManager(),tabLayout.getTabCount(), products);
        viewPager.setAdapter(drinkPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                products = productDAO.getCoffeeProductByCategory(tab.getPosition()+1);
                DrinkPageAdapter drinkPageAdapter = new DrinkPageAdapter(getFragmentManager(),tabLayout.getTabCount(), products);
                viewPager.setAdapter(drinkPageAdapter);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;
    }

}
