package com.koit.project_prm391_1.object;

public class Tinh {
    String matp;
    String name;
    String type;

    public Tinh(String matp, String name, String type) {
        this.matp = matp;
        this.name = name;
        this.type = type;
    }

    public String getMatp() {
        return matp;
    }

    public void setMatp(String matp) {
        this.matp = matp;
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

    @Override
    public String toString() {
        return name;
    }
}
