package com.app.app;

public class Viewdatalist {
    String txt;
    boolean isSelected;
    int txtid;

    public Viewdatalist() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public long getTxtid() {
        return txtid;
    }

    public void setTxtid(long txtid) {
        this.txtid = (int) txtid;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
