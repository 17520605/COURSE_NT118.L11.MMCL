package com.example.course2kp.Model;

import java.io.Serializable;

public class UserAccount implements Serializable {
    public String hoten;
    public String ngaysinh;
    public String sdt;
    public String ava;
    public String mail;
    public String ID;
    public String token;
    public String gioitinh;
    public String mota;
    public String diachia;
    public String matkhau;

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getDiachia() {
        return diachia;
    }

    public void setDiachia(String diachia) {
        this.diachia = diachia;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public UserAccount(String hoten, String ngaysinh, String sdt, String ava, String mail, String ID, String token, String gioitinh, String mota, String diachia, String matkhau) {
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.ava = ava;
        this.mail = mail;
        this.ID = ID;
        this.token = token;
        this.gioitinh = gioitinh;
        this.mota = mota;
        this.diachia = diachia;
        this.matkhau = matkhau;
    }
}
