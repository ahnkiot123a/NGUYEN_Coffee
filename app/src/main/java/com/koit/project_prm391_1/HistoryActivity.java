package com.koit.project_prm391_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.koit.project_prm391_1.adapter.HistoryOrderAdapter;
import com.koit.project_prm391_1.dao.OrderDAO;
import com.koit.project_prm391_1.object.Order;
import com.koit.project_prm391_1.object.User;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lvOrder;
    private FirebaseUser currentUser;
    private ArrayList<Order> listOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        toolbar = findViewById(R.id.toolbarGeneral);
        lvOrder = findViewById(R.id.lvDisplayHistory);

        View tbView = findViewById(R.id.toolbarGeneral);
        TextView tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Lịch sử giao dịch");
        toolbar.setTitle("");

        OrderDAO orderDAO = new OrderDAO();
        listOrder = orderDAO.getOrderByUserId(Integer.parseInt(currentUser.getUid()));
        HistoryOrderAdapter adapter = new HistoryOrderAdapter(this, R.layout.listview_history_layout, listOrder);
        lvOrder.setAdapter(adapter);

    }



    public void back(View view){
        onBackPressed();
    }

}
