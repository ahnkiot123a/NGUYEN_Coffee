package com.koit.project_prm391_1.ui.address;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.koit.project_prm391_1.AddNewAddress;
import com.koit.project_prm391_1.LoginActivity;
import com.koit.project_prm391_1.OrderDetailActivity;
import com.koit.project_prm391_1.R;
import com.koit.project_prm391_1.dao.AddressDAO;
import com.koit.project_prm391_1.object.Diachi;
import com.koit.project_prm391_1.object.User;
import java.util.ArrayList;

public class FragmentChooseAddress extends Fragment {

    private boolean isCheckOut = false;
    private ArrayList<Diachi> diachis;
    private ListView listView;
    private FirebaseUser currentUser;
    private AddressDAO addressDAO;
    private Button btnAddAddress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_choose_address,container,false);
         listView = view.findViewById(R.id.listViewChooseAddress);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        diachis = new ArrayList<>();
        //Phải get Địa chỉ từ DB ra :)
         addressDAO = new AddressDAO();
        diachis = (ArrayList<Diachi>) addressDAO.getAllDiachiFromUser(Integer.parseInt(currentUser.getUid()));
        AddressChoosingAdapter addressChoosingAdapter = new AddressChoosingAdapter();
        listView.setAdapter(addressChoosingAdapter);
        btnAddAddress = view.findViewById(R.id.btnAddAddress);
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNewAddress.class);
                intent.putExtra("isFromCart", Boolean.TRUE);
                startActivity(intent);
            }
        });
        return view;
    }
    private class AddressChoosingAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return diachis.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.layout_address_in_ship,null);
            TextView tvAddressInfo = view1.findViewById(R.id.txtAddressInfo);
            Button btnShipNow = view1.findViewById(R.id.btnShipNow);
            Button btnDelAddress = view1.findViewById(R.id.btnDelAddress);
            final Diachi address = diachis.get(position);
            tvAddressInfo.setText(address.getAllInfo());
            btnShipNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isCheckOut = true;
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra("address", address);
                    intent.putExtra("isCheckOut", isCheckOut);
                    startActivity(intent);

                }
            });
            btnDelAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Chỗ này để remove trong db

                    int delDiachiId = diachis.get(position).getDiachiID();
                    addressDAO.deleteDiachi(delDiachiId);
                    diachis.remove(position);
                    AddressChoosingAdapter adapter = new AddressChoosingAdapter();
                    listView.setAdapter(adapter);
                    Toast.makeText(getActivity(),"Xóa địa chỉ giao hàng thành công",Toast.LENGTH_LONG).show();
                }
            });
            return view1;
        }
    }
}
