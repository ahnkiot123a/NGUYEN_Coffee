package com.koit.project_prm391_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koit.project_prm391_1.dao.UserDAO;
import com.koit.project_prm391_1.object.Product;

import java.util.Calendar;

public class UserDetailActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPhone;
    private TextView tvDOB;
    private RadioGroup radioGroup;
    private RadioButton rbMale, rbFamale;
    private FirebaseUser currentUser;
    private String updateDate = "";
    private UserDAO userDAO;
    private Toolbar toolbar;
    private TextView tvToolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        findViewByID();
        setToolbarTitle();
        loadUserInfo();

    }

    private void findViewByID() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        tvDOB = findViewById(R.id.tvDoB);
        etPhone = findViewById(R.id.etPhone);
        etName = findViewById(R.id.etName);
        radioGroup = findViewById(R.id.radioGender);
        rbMale = findViewById(R.id.rbMale);
        rbFamale = findViewById(R.id.rbFemale);
        toolbar = findViewById(R.id.toolbarGeneral);
        View tbView = findViewById(R.id.toolbarGeneral);
        tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
    }

    private void setToolbarTitle() {
        tvToolbarTitle.setText("Thông tin tài khoản");
        toolbar.setTitle("");
    }

    public void loadUserInfo() {
        etEmail.setText(currentUser.getEmail());
        etEmail.setEnabled(false);
        etName.setText(currentUser.getDisplayName());
        //updateDate = currentUser.getDob();
//        if (currentUser.getGender() == 1) {
//            radioGroup.check(rbMale.getId());
//        } else if (currentUser.getGender() == 0) {
//            radioGroup.check(rbFamale.getId());
//        }
//        if (currentUser.getPhoneNumber() != 0) {
//            etPhone.setText(currentUser.getPhoneNumber() + "");
//        }
//        tvDOB.setText(currentUser.getDob());

    }

    public void updateUser(View view) {
        if (rbMale.isChecked()) {
            radioGroup.check(rbMale.getId());
        } else {
            radioGroup.check(rbFamale.getId());
        }
        try {
//            currentUser.setName(etName.getText().toString());
//            currentUser.setGender(gender);
//            currentUser.setDob(updateDate);
//            currentUser.setPhoneNumber(Integer.parseInt(etPhone.getText().toString()));
//            userDAO.updateUser(currentUser);
            Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Cập nhật thông tin thất bại!", Toast.LENGTH_LONG).show();
        }


    }

    public void getNewDate(View view) {
        DatePickerDialog.OnDateSetListener mListener = mListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                String date = i1 < 10 ? i + "-0" + i1 + "-" + i2 : i + "-" + i1 + "-" + i2;
                updateDate = i1 + "/" + i2 + "/" + i;
                tvDOB.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, mListener, year, month, day);
        dialog.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Add value event listener to the post
        // [START post_value_event_listener]

    }
    public void back(View view) {
        onBackPressed();
    }
}
