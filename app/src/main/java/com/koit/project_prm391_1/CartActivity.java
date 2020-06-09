package com.koit.project_prm391_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.koit.project_prm391_1.object.CartProduct;
import com.koit.project_prm391_1.object.CurrencyProcessing;
import com.koit.project_prm391_1.object.Product;
import com.koit.project_prm391_1.ui.address.ShipAddressActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvTotalFoodCost,tvTotalCash, tvShipFee;
    private Button btnAddNewItem,btnCheckOut;
    private ArrayList<CartProduct> list;
    private final int shipFee = 10000;
    private InteractWithFile interactWithFile;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancel(102);

        listView = findViewById(R.id.lvPro_Cart);
        tvTotalFoodCost = findViewById(R.id.txtTotalFoodCost);
        tvTotalCash  = findViewById(R.id.txtTotalCash);
        tvShipFee = findViewById(R.id.txtShipFee);
        btnAddNewItem = findViewById(R.id.btnAddMoreItem);
        btnCheckOut = findViewById(R.id.btnCheckOut);

        toolbar = findViewById(R.id.toolbarGeneral);
        View tbView = findViewById(R.id.toolbarGeneral);
        TextView tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Giỏ hàng");
        toolbar.setTitle("");


        interactWithFile = new InteractWithFile(this);
        list = interactWithFile.readList();

        tvShipFee.setText(CurrencyProcessing.covertString(shipFee));
        setMoney();

        listView.setAdapter(new CartProductAdapter());
        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size()==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setMessage("Hiện tại bạn chưa có sản phẩm nào trong giỏ hàng. Nhấn mua thêm sản phẩm để tiếp tục!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Đồng ý", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    Intent intent = new Intent(CartActivity.this, ShipAddressActivity.class);
                    startActivity(intent);
                }

            }
        });



    }
    private void setMoney(){
        float totalFoodCost =0;
        float totalCash = 0;
        for (CartProduct c: list){
            totalFoodCost += c.getProduct().getPrice()*c.getQuantity();
        }
        totalCash = totalFoodCost+shipFee;
        tvTotalFoodCost.setText(CurrencyProcessing.covertString((int)totalFoodCost));
        tvTotalCash.setText(CurrencyProcessing.covertString((int)totalCash));

    }
    private class CartProductAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
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
            View view1 = getLayoutInflater().inflate(R.layout.layout_product_in_cart,null);
            TextView tvName = view1.findViewById(R.id.txtProductName);
            TextView tvPrice = view1.findViewById(R.id.tvPrice);
            final TextView tvQuantity = view1.findViewById(R.id.tvQuantity);
            ImageView imageView= view1.findViewById(R.id.imgProductPic);
            ImageButton imgBtnDel = view1.findViewById(R.id.imgBtnDelete);
            ImageButton imgBtnUp = view1.findViewById(R.id.imgBtnUp);
            ImageButton imgBtnDown = view1.findViewById(R.id.imgBtnDown);

            CartProduct cartProduct = list.get(position);
            Product pro = cartProduct.getProduct();
            tvQuantity.setText(cartProduct.getQuantity()+"");
//            tvName.setText(pro.getProductName()+" ("+pro.getSize().trim()+")");
//            tvPrice.setText(pro.getStringPrice());
            Context ctx = getApplicationContext();
            int imageID = ctx.getResources().getIdentifier(pro.getPicture(),"drawable",ctx.getPackageName());
            imageView.setImageResource(imageID);

            imgBtnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    setMoney();
                    interactWithFile.writeList(list);
                    listView.setAdapter(new CartProductAdapter());
                }
            });

            imgBtnUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartProduct cartProduct = list.get(position);
                    int newQuant = cartProduct.getQuantity()+1;
                    cartProduct.setQuantity(newQuant);
                    tvQuantity.setText(newQuant+"");
                    setMoney();
                    interactWithFile.writeList(list);
                }
            });

            imgBtnDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartProduct cartProduct = list.get(position);
                    if(cartProduct.getQuantity()>1){
                        int newQuant = cartProduct.getQuantity()-1;
                        cartProduct.setQuantity(newQuant);
                        tvQuantity.setText(newQuant+"");
                        setMoney();
                        interactWithFile.writeList(list);
                    }
                }
            });



            return view1;
        }
    }
    public void back(View view){
        onBackPressed();
    }

}
