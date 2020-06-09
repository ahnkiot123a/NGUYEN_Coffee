package com.koit.project_prm391_1.dao;

import com.koit.project_prm391_1.object.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    ConnectDB connectDB = new ConnectDB();
    Connection con = connectDB.CONN();
    ResultSet rs;


    public void addNewUser(User user){

        try {
            PreparedStatement statement =
                    con.prepareStatement("insert into [User](UserID, [name], email, ImgURL) values (?,?,?,?)");
            statement.setInt(1,user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getImg_url());
            statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateUser(User user){

        try {
            PreparedStatement statement =
                    con.prepareStatement("update [User] set Name = N'" + user.getName() + "', Gender = " + user.getGender() + "" +
                            ", DOB = '"+ user.getDob() + "', PhoneNumber = " + user.getPhoneNumber() +
                            " where UserID = " + user.getId());
            statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public User getUserById(int id){
        User user = null;
        try {
            PreparedStatement statement =
                    con.prepareStatement("SELECT * FROM [User] WHERE UserID =  ?");
            statement.setInt(1,id);
            rs = statement.executeQuery();
            if(rs.next()){
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                int gender = rs.getInt("Gender");
                String dob =rs.getString("DOB");
                int phone = rs.getInt("PhoneNumber");
                String imgUrl =rs.getString("ImgUrl");
                user = new User(id,name,email,gender,dob,phone,imgUrl);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public int getMaxUserId(){
        try {
            PreparedStatement statement =
                    con.prepareStatement("SELECT max(UserID) as 'max' FROM [User]");
            rs = statement.executeQuery();
            if(rs.next()){
                int currentMax = rs.getInt("max");
                return currentMax;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public String getLastEmail(){
        try {
            PreparedStatement statement =
                    con.prepareStatement("select Email from [User] where UserID = " + getMaxUserId());
            rs = statement.executeQuery();
            if(rs.next()){
                String email = rs.getString("Email");
                return email;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
