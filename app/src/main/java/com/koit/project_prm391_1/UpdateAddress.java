package com.koit.project_prm391_1;

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
import com.koit.project_prm391_1.object.Xa;
import com.koit.project_prm391_1.ui.account.NotificationsViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UpdateAddress extends AppCompatActivity {
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        Toolbar toolbar = findViewById(R.id.toolbarGeneral);
        View tbView = findViewById(R.id.toolbarGeneral);
        TextView tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Thêm địa chỉ mới");
        toolbar.setTitle("");

        etName = findViewById(R.id.etUDName);
        etSonha = findViewById(R.id.etUDSonha);
        etPhone = findViewById(R.id.etUDPhone);
        etEmail = findViewById(R.id.etUDEmail);
        spTinh = findViewById(R.id.spinnerUDTinh);
        spHuyen = findViewById(R.id.spinnerUDQuan);
        spXa = findViewById(R.id.spinnerUDPhuong);
        btnOk = findViewById(R.id.btnUDOk);
        btnHuy = findViewById(R.id.btnUDHuy);
        final AddressDAO addressDAO = new AddressDAO();
        Intent intent = getIntent();
        final int diachiId = (Integer) intent.getIntExtra("diachiId", 0);
        final Diachi diachi = addressDAO.getDiachiFromDiachiID(diachiId);
        etName.setText(diachi.getName());
        etSonha.setText(diachi.getSoNha());
        etPhone.setText(diachi.getPhoneNumber());
        etEmail.setText(diachi.getEmail());
        listTinh = addressDAO.getAllTinh();
        adapterTinh = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listTinh
        );
        //listHuyen
        spTinh.setAdapter(adapterTinh);
        int posTinh = -1;
        Tinh tinh = addressDAO.getTinhFromTinhID(diachi.getTinh());
        for (int i = 0; i < listTinh.size(); i++) {
            Tinh t = listTinh.get(i);
            if (tinh.getMatp().equals(t.getMatp())) {
                posTinh = i;
                break;
            }
        }
        spTinh.setSelection(posTinh);
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
                int posHuyen = -1;
                Huyen huyen = addressDAO.getHuyenFromHuyen(diachi.getHuyen());
                for (int i = 0; i < listHuyen.size(); i++) {
                    Huyen t = listHuyen.get(i);
                    if (huyen.getMaqh().equals(t.getMaqh())) {
                        posHuyen = i;
                        break;
                    }
                }
                spHuyen.setSelection(posHuyen);
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
                int posXa = -1;
                Xa xa = addressDAO.getXaFromXaID(diachi.getXa());
                for (int i = 0; i < listXa.size(); i++) {
                    Xa t = listXa.get(i);
                    if (xa.getXaId().equals(t.getXaId())) {
                        posXa = i;
                        break;
                    }
                }
                spXa.setSelection(posXa);
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
                if (addressDAO.updateDiachi(diachiId, name, sonha, tinhID, huyenID, xaID, email, phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateAddress.this, DiachiActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateAddress.this, DiachiActivity.class);
                startActivity(intent);
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }
}
