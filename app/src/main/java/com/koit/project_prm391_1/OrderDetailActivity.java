package com.koit.project_prm391_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.koit.project_prm391_1.adapter.OrderDetailAdapter;
import com.koit.project_prm391_1.adapter.OrderDetailAdapterPlus;
import com.koit.project_prm391_1.dao.AddressDAO;
import com.koit.project_prm391_1.dao.OrderDAO;
import com.koit.project_prm391_1.object.CartProduct;
import com.koit.project_prm391_1.object.CurrencyProcessing;
import com.koit.project_prm391_1.object.Diachi;
import com.koit.project_prm391_1.object.Order;
import com.koit.project_prm391_1.object.OrderDetail;
import com.koit.project_prm391_1.object.User;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    private Button btnCheckOut, btnCancelCheckOut;
    private Intent intent;
    private TextView tvUserName, tvAddress, tvCity, tvDistrict, tvTown, tvPhoneNumber, tvTotalPrice, tvOrderType;
    private Diachi diachi;
    private ListView listOrder;
    private Order order;
    private FirebaseUser currentUser;
    private ArrayList<CartProduct> listOderDetailInCart;
    InteractWithFile interactWithFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        tvUserName = findViewById(R.id.tvUserName);
        tvAddress = findViewById(R.id.tvAddress);
        tvCity = findViewById(R.id.tvCity);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvTown = findViewById(R.id.tvTown);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvOrderType = findViewById(R.id.tvOrderType);
        listOrder = findViewById(R.id.lvItemOrderDetail);

        Toolbar toolbar = findViewById(R.id.toolbarGeneral);
        View tbView = findViewById(R.id.toolbarGeneral);
        TextView tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Chi tiết đơn hàng");
        toolbar.setTitle("");

        btnCheckOut = findViewById(R.id.btnCheckoutDetail);
        btnCancelCheckOut = findViewById(R.id.btnCancelCheckout);

        interactWithFile = new InteractWithFile(this);
        intent = getIntent();
        boolean isCheckout = intent.getBooleanExtra("isCheckOut", false);

        if (!isCheckout) {
            btnCheckOut.setVisibility(View.INVISIBLE);
            btnCancelCheckOut.setVisibility(View.INVISIBLE);
            order = (Order) intent.getSerializableExtra("Order");
            OrderDAO orderDAO = new OrderDAO();
            ArrayList<OrderDetail> listOrderDetail = orderDAO.getOrderDetailByOrderId(order.getOrderId());
            OrderDetailAdapter adapter = new OrderDetailAdapter(this, R.layout.layout_listview_orderdetail, listOrderDetail);
            listOrder.setAdapter(adapter);
            String totalPrice = intent.getStringExtra("totalPrice");
            tvTotalPrice.setText(totalPrice);


            AddressDAO addressDAO =new AddressDAO();
            diachi = addressDAO.getDiachiFromDiachiID(order.getDiachiId());
            String tinhID = diachi.getTinh();
            String huyenID = diachi.getHuyen();
            String xaID = diachi.getXa();
            diachi.setTinh(addressDAO.getTinhFromTinhID(tinhID).getName());
            diachi.setHuyen(addressDAO.getHuyenFromHuyen(huyenID).getName());
            diachi.setXa(addressDAO.getXaFromXaID(xaID).getName());
        }else{
            diachi = (Diachi) intent.getSerializableExtra("address");
            ArrayList<CartProduct> listOderDetailInCart = interactWithFile.readList();
            OrderDetailAdapterPlus orderDetailAdapterPlus = new OrderDetailAdapterPlus(this, R.layout.layout_listview_orderdetail, listOderDetailInCart);
            listOrder.setAdapter(orderDetailAdapterPlus);
            float totalCash = 0;
            for (CartProduct c: listOderDetailInCart){
                totalCash += c.getProduct().getPrice()*c.getQuantity();
            }
            tvTotalPrice.setText(CurrencyProcessing.covertString((int) totalCash));
        }
        loadDataInOrderDetail();
    }



    public void loadDataInOrderDetail() {
        tvUserName.setText(currentUser.getDisplayName());
        tvAddress.setText(diachi.getSoNha());
        tvCity.setText(diachi.getTinh());
        tvDistrict.setText(diachi.getHuyen());
        tvTown.setText(diachi.getXa());
        tvPhoneNumber.setText(diachi.getPhoneNumber());
        tvOrderType.setText("Giao hàng tận nơi");


    }

    public void back(View view) {
        onBackPressed();
    }

    public ArrayList<CartProduct> getListInCart() {
        return interactWithFile.readList();
    }

    public void createNewOrder() {
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.addNewOrder(diachi.getDiachiID());
        int newOrderId = orderDAO.getMaxOid();
        ArrayList<CartProduct> cartProducts = getListInCart();
        for (CartProduct c : cartProducts) {
//            orderDAO.addNewOrderDetail(newOrderId, new OrderDetail(c.getProduct().getProductID(), c.getQuantity()));
        }
    }

    public void checkout(View view) {
        createNewOrder();
        interactWithFile.destroyData();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn đã thanh toán thành công.Cảm ơn bạn đã đặt hàng tại NGUYEN COFFEE!");
        builder.setCancelable(false);
        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(OrderDetailActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setBtnCancelCheckOut(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn hủy bỏ đơn hàng!");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", null);
        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                interactWithFile.destroyData();
                Toast.makeText(OrderDetailActivity.this, "Đã hủy bỏ đơn hàng", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(OrderDetailActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
