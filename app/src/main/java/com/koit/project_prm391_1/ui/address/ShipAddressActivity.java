package com.koit.project_prm391_1.ui.address;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.koit.project_prm391_1.R;


public class ShipAddressActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_address);

        Toolbar toolbar = findViewById(R.id.toolbarGeneral);
        View tbView = findViewById(R.id.toolbarGeneral);
        TextView tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Địa chỉ giao hàng");
        toolbar.setTitle("");
        //add fragment to frame
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentChooseAddress fragmentChooseAddress = new FragmentChooseAddress();
        fragmentTransaction.add(R.id.containerFrame, fragmentChooseAddress);
        fragmentTransaction.commit();
    }

    public void back(View view){
        onBackPressed();
    }

}
