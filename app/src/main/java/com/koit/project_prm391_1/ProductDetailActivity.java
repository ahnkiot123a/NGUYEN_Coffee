package com.koit.project_prm391_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.koit.project_prm391_1.dao.ProductDAO;
import com.koit.project_prm391_1.object.CartProduct;
import com.koit.project_prm391_1.object.CurrencyProcessing;
import com.koit.project_prm391_1.object.Product;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView tvDes, tvName, tvTotalMoney, tvSize;
    private Spinner spinner,spinnerSize;
    private ImageView imageView;
    private Button btnAddToCart;
    private Product product;
    private int quantity;
    private final String fileName ="cart.txt";
    final private String[] quantityRange = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        spinner = findViewById(R.id.spinnerQuantityChoose);
        spinnerSize = findViewById(R.id.spinnerSize);
        imageView = findViewById(R.id.imageView);
        tvDes = findViewById(R.id.tvDescription);
        tvName = findViewById(R.id.tvProductName);
        tvTotalMoney = findViewById(R.id.tvDetailTotalMoney);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        Toolbar toolbar = findViewById(R.id.toolbarGeneral);
        View tbView = findViewById(R.id.toolbarGeneral);
        TextView tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Thông tin sản phẩm");
        toolbar.setTitle("");

        product = (Product) getIntent().getSerializableExtra("product");
        setAllInfo();

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, quantityRange);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int number = Integer.parseInt(quantityRange[position]);
                quantity =number;
                setMoney();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ProductDAO productDAO = new ProductDAO();
        final ArrayList<Product> products = productDAO.getProductByName(product.getProductName());
        ArrayList<String> listSize = new ArrayList<>();
        for(Product p:products){
//            listSize.add(p.getSize());
        }
        ArrayAdapter<String> adapterSize = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, listSize);
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapterSize);
        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product = products.get(position);
                setAllInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this,CartActivity.class);
                CartProduct cartProduct = new CartProduct(product,quantity);
                InteractWithFile interactWithFile = new InteractWithFile(ProductDetailActivity.this);
                ArrayList<CartProduct> arrayList = interactWithFile.readList();
                Boolean isExist = false;
                for(CartProduct c: arrayList){
                    if(cartProduct.getProduct().getId()==c.getProduct().getId()){
                        c.setQuantity(c.getQuantity()+cartProduct.getQuantity());
                        isExist = true;
                    }
                }
                if(isExist==false){
                    arrayList.add(cartProduct);
                }
                interactWithFile.writeList(arrayList);
                //Chỗ này sẽ bị thay đổi nếu có biểu tượng giỏ hàng, đây chỉ để test thử xem có add
                // được không thôi :)
                startActivity(intent);
            }
        });
    }

    public void back(View view){
        onBackPressed();
    }
    public void setAllInfo(){
        Context ctx = getApplicationContext();
        int imageID = ctx.getResources().getIdentifier(product.getPicture(), "drawable", ctx.getPackageName());
        imageView.setImageResource(imageID);
        tvDes.setText(product.getDescription());
        tvName.setText(product.getProductName());
        setMoney();
    }
    public void setMoney(){
        float floatTotal = product.getPrice()*quantity;
        String totalNumber = CurrencyProcessing.covertString((int)floatTotal);
        tvTotalMoney.setText(totalNumber);
    }

}
