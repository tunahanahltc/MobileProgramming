package com.example.secondapp;

import java.io.Serializable;

public class UserType implements Serializable {
    private String name,surname,e_mail,phone_number,educateInform;
    private String password,statu;

    public UserType(String name, String surname, String e_mail, String password,String statu) {
        this.name = name;
        this.surname = surname;
        this.e_mail = e_mail;
        this.password = password;
        this.educateInform = "";
        this.phone_number = "";
        this.statu = statu;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getStatu() {
        return statu;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEducateInform() {
        return educateInform;
    }


    public String getPassword() {
        return password;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setEducateInform(String educateInform) {
        this.educateInform = educateInform;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
