package com.koit.project_prm391_1.object;
import java.io.Serializable;

public class Diachi implements Serializable {
    private  int diachiID;
    private int userID;
    private String name;
    private String soNha;
    private String tinh;
    private String huyen;
    private String xa;
    private String email;
    private String phoneNumber;
    private boolean status;

    public Diachi(int diachiID, int userID, String name, String soNha, String tinh, String huyen, String xa, String email, String phoneNumber, boolean status) {
        this.diachiID = diachiID;
        this.userID = userID;
        this.name = name;
        this.soNha = soNha;
        this.tinh = tinh;
        this.huyen = huyen;
        this.xa = xa;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Diachi(int userID, String name, String soNha, String tinh, String huyen, String xa, String email, String phoneNumber) {
        this.userID = userID;
        this.name = name;
        this.soNha = soNha;
        this.tinh = tinh;
        this.huyen = huyen;
        this.xa = xa;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Diachi(int diachiID, int userID, String name, String soNha, String tinh, String huyen, String xa, String email, String phoneNumber) {
        this.diachiID = diachiID;
        this.userID = userID;
        this.name = name;
        this.soNha = soNha;
        this.tinh = tinh;
        this.huyen = huyen;
        this.xa = xa;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Diachi(int userID, String name, String soNha, String tinh, String huyen, String xa, String email, String phoneNumber, boolean status) {
        this.userID = userID;
        this.name = name;
        this.soNha = soNha;
        this.tinh = tinh;
        this.huyen = huyen;
        this.xa = xa;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public int getDiachiID() {
        return diachiID;
    }

    public void setDiachiID(int diachiID) {
        this.diachiID = diachiID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getHuyen() {
        return huyen;
    }

    public void setHuyen(String huyen) {
        this.huyen = huyen;
    }

    public String getXa() {
        return xa;
    }

    public void setXa(String xa) {
        this.xa = xa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAllInfo() {
        String fullInfo = "Người nhận: " + name
                + "\nĐịa chỉ: " + soNha
                + "\nTỉnh\\Thành: " + tinh
                + "\nQuận\\Huyện: " + huyen
                + "\nPhường\\Xã: " + xa
                + "\nĐiện thoai: " + phoneNumber
                + "\nEmail: " + email;
        return fullInfo;
    }
}
