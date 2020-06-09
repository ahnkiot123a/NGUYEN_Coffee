package com.koit.project_prm391_1.object;

import java.io.Serializable;

public class CartProduct implements Serializable {
    Product product;
    int quantity;

    public CartProduct() {
    }

    public CartProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
