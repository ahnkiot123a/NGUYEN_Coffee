package com.koit.project_prm391_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.koit.project_prm391_1.dao.AddressDAO;
import com.koit.project_prm391_1.object.Diachi;
import com.koit.project_prm391_1.object.Huyen;
import com.koit.project_prm391_1.object.Tinh;
import com.koit.project_prm391_1.object.User;
import com.koit.project_prm391_1.object.Xa;
import com.koit.project_prm391_1.ui.account.NotificationsViewModel;
import com.koit.project_prm391_1.ui.address.ShipAddressActivity;

import java.util.List;

public class AddNewAddress extends AppCompatActivity {
    private NotificationsViewModel notificationsViewModel;
    EditText etName, etSonha, etPhone, etEmail;
    Spinner spTinh, spHuyen, spXa;
    Button btnOk, btnHuy;
    ArrayAdapter<Tinh> adapterTinh;
    ArrayAdapter<Huyen> adapterHuyen;
    ArrayAdapter<Xa> adapterXa;
    public List<Tinh> listTinh;
    public List<Huyen> listHuyen;
    public List<Xa> listXa;
    private User currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);



        Toolbar toolbar = findViewById(R.id.toolbarGeneral);
        View tbView = findViewById(R.id.toolbarGeneral);
        TextView tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Thêm địa chỉ mới");
        toolbar.setTitle("");

        etName = findViewById(R.id.etANName);
        etSonha = findViewById(R.id.etANSonha);
        etPhone = findViewById(R.id.etANPhone);
        etEmail = findViewById(R.id.etANEmail);
        spTinh = findViewById(R.id.spinnerANTinh);
        spHuyen = findViewById(R.id.spinneRANQuan);
        spXa = findViewById(R.id.spinnerANPhuong);
//        btnOk = findViewById(R.id.btnANOk);
        btnHuy = findViewById(R.id.btnANHuy);
        final AddressDAO addressDAO = new AddressDAO();
        listTinh = addressDAO.getAllTinh();
        adapterTinh = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listTinh
        );
        //listHuyen
        spTinh.setAdapter(adapterTinh);
        spTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddressDAO addressDAO = new AddressDAO();
                listHuyen = addressDAO.getHuyenFromTinhName(spTinh.getSelectedItem().toString());
                adapterHuyen = new ArrayAdapter<>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        listHuyen
                );
                spHuyen.setAdapter(adapterHuyen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spHuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddressDAO addressDAO = new AddressDAO();
                listXa = addressDAO.getAllXaFromHuyenName(spHuyen.getSelectedItem().toString());
                adapterXa = new ArrayAdapter<>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        listXa
                );
                spXa.setAdapter(adapterXa);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tinhID = addressDAO.getTinhIDFromNameID(spTinh.getSelectedItem().toString());
                String huyenID = addressDAO.getHuyenIDFromNameID(spHuyen.getSelectedItem().toString());
                String xaID = addressDAO.getXaIDFromNameID(spXa.getSelectedItem().toString());
                String name = etName.getText().toString();
                String sonha = etSonha.getText().toString();
                String email = etEmail.getText().toString();
                String phoneNumber = etPhone.getText().toString();
                if (addressDAO.addDiaChi(currentUser.getId(), name, sonha, tinhID, huyenID, xaID, email, phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent currentIntent = getIntent();
                    Boolean fromCart = currentIntent.getBooleanExtra("isFromCart", Boolean.FALSE);
                    Intent intent;
                    if (fromCart) {
                        intent = new Intent(AddNewAddress.this, ShipAddressActivity.class);
                    } else {
                        intent = new Intent(AddNewAddress.this, DiachiActivity.class);
                    }
                    startActivity(intent);
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewAddress.this, DiachiActivity.class);
                startActivity(intent);
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }
}
