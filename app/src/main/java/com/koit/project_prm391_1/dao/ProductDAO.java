package com.koit.project_prm391_1.dao;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koit.project_prm391_1.object.Category;
import com.koit.project_prm391_1.object.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductDAO {
    ConnectDB connectDB = new ConnectDB();
    ResultSet rs;
    Connection con = connectDB.CONN();

    private DatabaseReference productReference = FirebaseDatabase.getInstance().getReference().child("Products");

    public ArrayList<String> getDrinkCateName() {
        ArrayList<String> list = new ArrayList<>();
        list.add("COFFEE");
        list.add("FREEZE");
        list.add("TEA");
        return list;
    }

    public ArrayList<Product> getCoffeeProductByCategory(int id) {
        final ArrayList<Product> listCoffee = new ArrayList<>();
        final ArrayList<Product> listFreeze = new ArrayList<>();
        final ArrayList<Product> listTea = new ArrayList<>();
        final ArrayList<Product> listBanhMi = new ArrayList<>();

        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Products");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot ds : dataSnapshots) {
                    Product product =ds.getValue(Product.class);
                    switch (product.getCategory()) {
                        case "COFFEE":
                            listCoffee.add(product);
                            break;
                        case "FREEZE":
                            listFreeze.add(product);
                            break;
                        case "TEA":
                            listTea.add(product);
                            break;
                        case "BANH MI":
                            listBanhMi.add(product);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("datacheck", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]

            }
        });
        Log.d("listCoffee", listCoffee.toString());
        Log.d("listFreeze", listFreeze.toString());
        Log.d("listTea", listTea.toString());
        Log.d("listBanhMi", listBanhMi.toString());
        return id == 0 ? listBanhMi : id == 1 ? listCoffee : id == 2 ? listFreeze : listTea;
    }

    public ArrayList<Product> getProductByCateId(int cateID) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            PreparedStatement statement =
                    con.prepareStatement("SELECT * FROM Product WHERE categoryId= ?");
            statement.setInt(1, cateID);
            rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                float price = rs.getFloat("Price");
                String size = rs.getString("Size");
                String description = rs.getString("Description");
                String picture = rs.getString("Picture");
                picture = "@mipmap/" + picture;
//                Product product = new Product(id,name,cateID,price,size,description,picture);

//                list.add(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ArrayList<Category> getDrinkCate() {
        ArrayList<Category> list = new ArrayList<>();

        try {
            PreparedStatement statement =
                    con.prepareStatement("SELECT * FROM Category WHERE categoryName != ?");
            statement.setString(1, "BANH MI");
            rs = statement.executeQuery();
            while (rs.next()) {
                int cateID = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                list.add(new Category(cateID, categoryName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Product getProductById(int proId) {
        Product product = null;
        try {
            PreparedStatement statement =
                    con.prepareStatement("SELECT * FROM Product WHERE ProductID = ?");
            statement.setInt(1, proId);
            rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                float price = rs.getFloat("Price");
                String size = rs.getString("Size");
                String description = rs.getString("Description");
                String picture = rs.getString("Picture");
                picture = "@mipmap/" + picture;
//                product = new Product(id,name,proId,price,size,description,picture);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }

    public ArrayList<Product> getProductByName(String pName) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            PreparedStatement statement =
                    con.prepareStatement("SELECT * FROM Product WHERE productName= ?");
            statement.setString(1, pName);
            rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                int cateID = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                float price = rs.getFloat("Price");
                String size = rs.getString("Size");
                String description = rs.getString("Description");
                String picture = rs.getString("Picture");
                picture = "@mipmap/" + picture;
//                Product product = new Product(id,name,cateID,price,size,description,picture);
//                list.add(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }


}
