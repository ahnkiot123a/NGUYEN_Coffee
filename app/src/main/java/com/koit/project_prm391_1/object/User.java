package com.koit.project_prm391_1.object;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private int id;
    private String name;
    private String email;
    private int gender;
    private String dob;
    private int phoneNumber;
    private String img_url;

    public User() {
    }

    public User(int id, String name, String email, int gender, String dob, int phoneNumber, String img_url) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.img_url = img_url;
    }

    public User(int id, String name, String email, String img_url) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = -1;
        this.img_url = img_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
