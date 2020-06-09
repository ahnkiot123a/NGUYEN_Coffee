package com.koit.project_prm391_1.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.koit.project_prm391_1.R;
import com.koit.project_prm391_1.dao.ProductDAO;
import com.koit.project_prm391_1.object.CurrencyProcessing;
import com.koit.project_prm391_1.object.OrderDetail;
import com.koit.project_prm391_1.object.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends BaseAdapter {
    private Activity activity;
    private int layout;
    private List<OrderDetail> list;

    public OrderDetailAdapter(Activity activity, int layout, List<OrderDetail> list) {
        this.activity = activity;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final TextView tvProductNameOrderDetail, tvQuantityOrderDetail, tvPriceOrderDetail;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(layout, null);
            tvProductNameOrderDetail = view.findViewById(R.id.tvProductNameOrderDetail);
            tvQuantityOrderDetail = view.findViewById(R.id.tvQuantityOrderDetail);
            tvPriceOrderDetail = view.findViewById(R.id.tvPriceOrderDetail);

            view.setTag(R.id.tvProductNameOrderDetail, tvProductNameOrderDetail);
            view.setTag(R.id.tvQuantityOrderDetail, tvQuantityOrderDetail);
            view.setTag(R.id.tvPriceOrderDetail, tvPriceOrderDetail);


        } else {
            tvProductNameOrderDetail = (TextView) view.getTag(R.id.tvProductName);
            tvQuantityOrderDetail = (TextView) view.getTag(R.id.tvQuantityOrderDetail);
            tvPriceOrderDetail = (TextView) view.getTag(R.id.tvPriceOrderDetail);
        }

        final OrderDetail od = list.get(i);
        ProductDAO productDAO = new ProductDAO();
        Product p = productDAO.getProductById(od.getProductId());
//        tvProductNameOrderDetail.setText(p.getProductName()  + " (" + p.getSize().trim() + ") ");
        tvQuantityOrderDetail.setText(od.getQuantity() +"");
        float totalPrice = p.getPrice()*od.getQuantity();
        tvPriceOrderDetail.setText(CurrencyProcessing.covertString((int)totalPrice));



        return view;
    }

}
