package com.koit.project_prm391_1.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.koit.project_prm391_1.OrderDetailActivity;
import com.koit.project_prm391_1.R;
import com.koit.project_prm391_1.dao.OrderDAO;
import com.koit.project_prm391_1.dao.ProductDAO;
import com.koit.project_prm391_1.object.CurrencyProcessing;
import com.koit.project_prm391_1.object.Order;
import com.koit.project_prm391_1.object.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderAdapter extends BaseAdapter {

    private final int shipFee = 10000;


    private Activity activity;
    private int layout;
    private List<Order> list;

    public HistoryOrderAdapter(Activity activity, int layout, List<Order> list) {
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
        final TextView tvOrderId, tvOrderDate, tvOrderPrice;
        final TextView tvOrderType;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(layout, null);
            tvOrderId = view.findViewById(R.id.tvOrderId);
            tvOrderDate = view.findViewById(R.id.tvOrderDate);
            tvOrderPrice = view.findViewById(R.id.tvOrderPrice);
            tvOrderType = view.findViewById(R.id.tvOrderType);

            view.setTag(R.id.tvOrderId, tvOrderId);
            view.setTag(R.id.tvOrderDate, tvOrderDate);
            view.setTag(R.id.tvOrderPrice, tvOrderPrice);
            view.setTag(R.id.tvOrderType, tvOrderType);


        } else {
            tvOrderId = (TextView) view.getTag(R.id.tvOrderId);
            tvOrderDate = (TextView) view.getTag(R.id.tvOrderDate);
            tvOrderPrice = (TextView) view.getTag(R.id.tvOrderPrice);
            tvOrderType = (TextView) view.getTag(R.id.tvOrderType);
        }

        final Order o = list.get(i);
        tvOrderId.setText(o.getOrderId() + "");
        tvOrderDate.setText(o.getOrderDate().toString());

        OrderDAO orderDAO = new OrderDAO();
        ArrayList<OrderDetail> list = orderDAO.getOrderDetailByOrderId(o.getOrderId());;
        float totalFoodCost = 0;
        float totalCash = 0;
        ProductDAO productDAO = new ProductDAO();
        for (OrderDetail od : list) {
            totalFoodCost += productDAO.getProductById(od.getProductId()).getPrice() * od.getQuantity();
        }
        totalCash = totalFoodCost + shipFee;
        tvOrderPrice.setText(CurrencyProcessing.covertString((int) totalCash));
        tvOrderType.setText("Giao đến tận nơi");

        final float finalTotalCash = totalCash;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OrderDetailActivity.class);
                intent.putExtra("Order", o);
                intent.putExtra("totalPrice", CurrencyProcessing.covertString((int) finalTotalCash));
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
