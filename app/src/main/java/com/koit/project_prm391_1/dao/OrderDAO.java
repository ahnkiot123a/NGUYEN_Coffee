package com.koit.project_prm391_1.dao;

import com.koit.project_prm391_1.object.Order;
import com.koit.project_prm391_1.object.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class OrderDAO {
    ConnectDB connectDB = new ConnectDB();
    ResultSet rs;
    Connection con = connectDB.CONN();
    public ArrayList<OrderDetail> getOrderDetailByOrderId(int orderID){
        ArrayList<OrderDetail> list = new ArrayList<>();
        try {
            PreparedStatement statement =
                    con.prepareStatement("SELECT * FROM OrderDetail WHERE orderId= ?");
            statement.setInt(1,orderID);
            rs = statement.executeQuery();
            while (rs.next()) {
                int prodid = rs.getInt("ProductID");
                int quantity = rs.getInt("Quantity");
                OrderDetail orderDetail = new OrderDetail(prodid,quantity);
                list.add(orderDetail);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ArrayList<Order> getOrderByUserId(int userId){
        ArrayList<Order> list = new ArrayList<>();
        try {
            PreparedStatement statement =
                    con.prepareStatement("SELECT  [Order].* " +
                            "FROM            Diachi INNER JOIN" +
                            "                         [Order] ON Diachi.DiachiID = [Order].DiaChiID INNER JOIN" +
                            "                         [User] ON Diachi.UserID = [User].UserID " +
                            "WHERE [User].UserID = " + userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("OrderID");
                int employeeId = rs.getInt("EmployeeID");
                int diachiId = rs.getInt("DiaChiID");
                Date orderDate = rs.getDate("OrderDate");
                Order o = new Order(orderId, employeeId, diachiId, orderDate);
                list.add(o);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public void addNewOrder(int diachiID){
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        try {
            PreparedStatement statement =
                    con.prepareStatement("INSERT INTO [dbo].[Order]\n" +
                            "           ([EmployeeID]\n" +
                            "           ,[OrderDate]\n" +
                            "           ,[DiaChiID])\n" +
                            "     VALUES\n" +
                            "           (?,?,?)");
            statement.setInt(1,1);
            statement.setTimestamp(2, date);
            statement.setInt(3,diachiID);
            statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public int getMaxOid(){
        try {
            PreparedStatement statement =
                    con.prepareStatement("SELECT max(OrderID) as 'max' FROM dbo.[Order]");
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
    public void addNewOrderDetail(int orderid, OrderDetail orderDetail){
        try {
            PreparedStatement statement =
                    con.prepareStatement("INSERT INTO [dbo].[OrderDetail]\n" +
                            "           ([OrderID]\n" +
                            "           ,[ProductID]\n" +
                            "           ,[Quantity])\n" +
                            "     VALUES\n" +
                            "           (?,?,?)");
            statement.setInt(1,orderid);
            statement.setInt(2, orderDetail.getProductId());
            statement.setInt(3,orderDetail.getQuantity());
            statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
