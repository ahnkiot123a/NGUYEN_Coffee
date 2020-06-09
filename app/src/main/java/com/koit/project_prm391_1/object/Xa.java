package com.koit.project_prm391_1.object;

public class Xa {
    String xaId;
    String name;
    String type;
    String maqh;

    public Xa(String xaId, String name, String type, String maqh) {
        this.xaId = xaId;
        this.name = name;
        this.type = type;
        this.maqh = maqh;
    }

    public String getXaId() {
        return xaId;
    }

    public void setXaId(String xaId) {
        this.xaId = xaId;
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

    public String getMaqh() {
        return maqh;
    }

    public void setMaqh(String maqh) {
        this.maqh = maqh;
    }

    @Override
    public String toString() {
        return name;
    }
}
