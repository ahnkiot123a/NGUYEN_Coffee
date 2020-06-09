package com.koit.project_prm391_1.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koit.project_prm391_1.ProductDetailActivity;
import com.koit.project_prm391_1.R;
import com.koit.project_prm391_1.object.Product;

import java.util.List;


public class TabInDrink extends Fragment {

    List<Product> list;
    public TabInDrink(List<Product> listPro){
        this.list = listPro;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.gridview_product,container,false);
        GridView gridView = view.findViewById(R.id.gridViewDrink);
        CustomAdapter1 customAdapter = new CustomAdapter1();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product",list.get(position));
                startActivity(intent);
            }
        });
        return view;
    }
    private class CustomAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.layout_product_in_menu,null);
            TextView tvName = view1.findViewById(R.id.tvProductName);
            TextView tvPrice = view1.findViewById(R.id.tvPrice);
            ImageView imageView= view1.findViewById(R.id.imgViewProduct);
            Product pro = list.get(position);
//            tvName.setText(pro.getProductName()+" ("+pro.getSize().trim()+")");
//            tvPrice.setText(pro.getStringPrice());
            Context ctx = getActivity().getApplicationContext();
            Uri uri = Uri.parse(pro.getPicture());
            imageView.setImageURI(uri);
            return view1;
        }
    }
}
