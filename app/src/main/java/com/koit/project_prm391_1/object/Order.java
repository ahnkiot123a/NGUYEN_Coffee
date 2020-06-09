package com.koit.project_prm391_1.object;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {

    private int OrderId;
    private int CustomerId;
    private int DiachiId;
    private Date OrderDate;
    private List<OrderDetail> OrderDetail;

    public Order() {
    }

    public Order(int orderId, int customerId, int diachiId, Date orderDate) {
        OrderId = orderId;
        CustomerId = customerId;
        DiachiId = diachiId;
        OrderDate = orderDate;
    }

    public Order(int orderId, int customerId, int diachiId, Date orderDate, List<com.koit.project_prm391_1.object.OrderDetail> orderDetail) {
        OrderId = orderId;
        CustomerId = customerId;
        DiachiId = diachiId;
        OrderDate = orderDate;
        OrderDetail = orderDetail;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getDiachiId() {
        return DiachiId;
    }

    public void setDiachiId(int diachiId) {
        DiachiId = diachiId;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public List<com.koit.project_prm391_1.object.OrderDetail> getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(List<com.koit.project_prm391_1.object.OrderDetail> orderDetail) {
        OrderDetail = orderDetail;
    }
}
