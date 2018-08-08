package com.example.administrator.myapplication;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class AddressInfo extends DataSupport implements Serializable {

    //地区号
    private String number;
    //地区名字
    private String name;

    public AddressInfo() {
    }

    public AddressInfo(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
