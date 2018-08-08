package com.example.administrator.myapplication;

import java.io.Serializable;

public class PersonInfo implements Serializable {

    //地区
    private String address;
    //出生年月日
    private String birthday;
    //性别
    private String sex;

    public PersonInfo() {
    }

    public PersonInfo(String address, String birthday, String sex) {
        this.address = address;
        this.birthday = birthday;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "address='" + address + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
