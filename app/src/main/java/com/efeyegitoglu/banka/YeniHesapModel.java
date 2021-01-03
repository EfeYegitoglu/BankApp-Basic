package com.efeyegitoglu.banka;

public class YeniHesapModel {

    String isim, kartNo,para;

    public YeniHesapModel(String isim, String kartNo,String para) {
        this.isim = isim;
        this.kartNo = kartNo;
        this.para=para;
    }

    public YeniHesapModel() {}

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getKartNo() {
        return kartNo;
    }

    public void setKartNo(String kartNo) {
        this.kartNo = kartNo;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }
}


