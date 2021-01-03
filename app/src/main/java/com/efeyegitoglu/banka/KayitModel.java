package com.efeyegitoglu.banka;

public class KayitModel {

    String id, isim, KartNo, mail, para;


    public KayitModel(String id, String isim, String kartNo, String mail, String para) {
        this.id = id;
        this.isim = isim;
        this.KartNo = kartNo;
        this.mail = mail;
        this.para = para;
    }

    public KayitModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getKartNo() {
        return KartNo;
    }

    public void setKartNo(String kartNo) {
        KartNo = kartNo;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }


}
