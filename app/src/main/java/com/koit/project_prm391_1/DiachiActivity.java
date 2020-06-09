package com.koit.project_prm391_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.koit.project_prm391_1.dao.AddressDAO;
import com.koit.project_prm391_1.object.Diachi;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DiachiActivity extends AppCompatActivity {
    ListView listView;
    List<Diachi> list;
    DiachiAdapter adapter = null;
    AddressDAO addressDAO;
    Diachi diachi;
    private FirebaseUser currentUser;
    ImageView imageViewEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diachi);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar = findViewById(R.id.toolbarGeneral);
        View tbView = findViewById(R.id.toolbarGeneral);
        TextView tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Địa chỉ giao hàng");
        toolbar.setTitle("");
        imageViewEmp = findViewById(R.id.imageViewEmpty);
        listView = findViewById(R.id.listview);
        list = new ArrayList<>();
//        list.add(new Diachi(1, "Luat", "Nha so 8", "Hanoi", "Nam Tu Liem",
//                "Xuan Dinh", "LuatNDSE05570@fpt.edu.vn", "0364413666"));
        addressDAO = new AddressDAO();
        list = addressDAO.getAllDiachiFromUser(Integer.parseInt(currentUser.getUid()));
        adapter = new DiachiAdapter();
        listView.setAdapter(adapter);
        if (list.size() == 0) {
            imageViewEmp.setVisibility(View.VISIBLE);
        } else {
            imageViewEmp.setVisibility(View.INVISIBLE);
        }

    }

    public void addNewAddress(View view) {
        Intent intent = new Intent(this, AddNewAddress.class);
        startActivity(intent);
    }

    public class DiachiAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView tvNguoiNhan, sonha, tinh, quan, phuong, phoneNumber, email;
            Button btnChinhSua;
            Button btnXoa;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.diachilayout, null);
                tvNguoiNhan = convertView.findViewById(R.id.tvNguoiNhan);
                sonha = convertView.findViewById(R.id.tvDiaChi);
                tinh = convertView.findViewById(R.id.tvTinhThanh);
                quan = convertView.findViewById(R.id.tvQuanHuyen);
                phuong = convertView.findViewById(R.id.tvPhuongXa);
                phoneNumber = convertView.findViewById(R.id.tvDienThoai);
                email = convertView.findViewById(R.id.tvEmail);
                btnXoa = convertView.findViewById(R.id.btnXoa);
                btnChinhSua = convertView.findViewById(R.id.btnChinhSua);
                convertView.setTag(R.id.tvNguoiNhan, tvNguoiNhan);
                convertView.setTag(R.id.tvDiaChi, sonha);
                convertView.setTag(R.id.tvTinhThanh, tinh);
                convertView.setTag(R.id.tvQuanHuyen, quan);
                convertView.setTag(R.id.tvPhuongXa, phuong);
                convertView.setTag(R.id.tvDienThoai, phoneNumber);
                convertView.setTag(R.id.tvEmail, email);
                convertView.setTag(R.id.btnXoa, btnXoa);
                convertView.setTag(R.id.btnChinhSua, btnChinhSua);
            } else {
                email = (TextView) convertView.getTag(R.id.tvEmail);
                sonha = (TextView) convertView.getTag(R.id.tvDiaChi);
                tinh = (TextView) convertView.getTag(R.id.tvTinhThanh);
                quan = (TextView) convertView.getTag(R.id.tvQuanHuyen);
                phuong = (TextView) convertView.getTag(R.id.tvPhuongXa);
                phoneNumber = (TextView) convertView.getTag(R.id.tvDienThoai);
                tvNguoiNhan = (TextView) convertView.getTag(R.id.tvNguoiNhan);
                btnXoa = (Button) convertView.getTag(R.id.btnXoa);
                btnChinhSua = (Button) convertView.getTag(R.id.btnChinhSua);
            }
            diachi = list.get(position);
            tvNguoiNhan.setText(diachi.getName());
            email.setText(diachi.getEmail());
            sonha.setText(diachi.getSoNha());
            tinh.setText(diachi.getTinh());
            quan.setText(diachi.getHuyen());
            phuong.setText(diachi.getXa());
            phoneNumber.setText(diachi.getPhoneNumber());
            final int diachiID = diachi.getDiachiID();
            //xoa dia chi
            btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(DiachiActivity.this);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete?");
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (addressDAO.deleteDiachi(diachiID)) {
                                Toast.makeText(getApplicationContext(), "Deleted Successfully ", Toast.LENGTH_SHORT).show();
                            }
                            list.remove(position);
                            listView.setAdapter(new DiachiAdapter());
                            if (list.size() == 0) {
                                imageViewEmp.setVisibility(View.VISIBLE);
                            } else {
                                imageViewEmp.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    adb.show();
                }
            });
            btnChinhSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), UpdateAddress.class);
                    intent.putExtra("diachiId", diachiID);
                    startActivity(intent);
//                    NotificationsFragment nextFrag = new NotificationsFragment();
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragmentHome, nextFrag)
//                            .addToBackStack(null)
//                            .commit();
//                    FragmentManager fm = getFragmentManager();
//                    Fragment fragment = fm.findFragmentById(R.id.fragmentHome);
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.setCustomAnimations(android.R.animator.fade_in,
//                            android.R.animator.fade_out);
//                    if (fragment.isHidden()) {
//                        ft.show(fragment);
//                    } else {
//                        ft.hide(fragment);
//                    }
//                    ft.commit();
                }
            });
            return convertView;
        }
    }

    public void back(View view) {
      Intent intent = new Intent(this, MainScreenActivity.class);
      startActivity(intent);
    }
}
