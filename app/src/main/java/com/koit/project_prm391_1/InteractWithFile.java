package com.koit.project_prm391_1;

import android.app.Activity;
import android.content.Context;

import com.koit.project_prm391_1.object.CartProduct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InteractWithFile {
    private Activity activity;
    private final String fileName = "cart.txt";
    public InteractWithFile(Activity activity) {
        this.activity = activity;
    }

    public void writeList(ArrayList<CartProduct> list){
        try {
            FileOutputStream fos = activity.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(list);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<CartProduct> readList(){
        ArrayList<CartProduct> arrayList=null;
        try {
            FileInputStream fis = activity.getApplicationContext().openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            arrayList = (ArrayList<CartProduct>) is.readObject();
            is.close();
            fis.close();
        }
        catch (FileNotFoundException f){
            arrayList = new ArrayList<>();
        }
        catch (Exception e) {
            e.printStackTrace();
            arrayList = new ArrayList<>();
        }finally {

        }
        return arrayList;
    }
    public void destroyData(){
        try {
            FileOutputStream fos = activity.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(new ArrayList<CartProduct>());
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  int getCartNumber(){
        return readList().size();
    }
}
