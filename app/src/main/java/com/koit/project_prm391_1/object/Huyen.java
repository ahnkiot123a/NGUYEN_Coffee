package com.koit.project_prm391_1.object;

public class Huyen {
    String maqh;
    String name;
    String type;
    String matp;

    public Huyen(String maqh, String name, String type, String matp) {
        this.maqh = maqh;
        this.name = name;
        this.type = type;
        this.matp = matp;
    }

    public String getMaqh() {
        return maqh;
    }

    public void setMaqh(String maqh) {
        this.maqh = maqh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatp() {
        return matp;
    }

    public void setMatp(String matp) {
        this.matp = matp;
    }

    @Override
    public String toString() {
        return name;
    }
}
