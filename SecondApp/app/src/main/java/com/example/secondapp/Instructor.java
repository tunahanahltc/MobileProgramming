package com.example.secondapp;

import java.io.Serializable;

public class Instructor extends UserType implements Serializable {


    public Instructor(String name, String surname, String e_mail, String password) {
        super(name,surname,e_mail,password,"Akademisyen");

    }

    @Override
    public String toString() {
        return "Akademisyen :  \n" +"İsim: "+ getName() +"\n" +"E-mail: "+ getE_mail();
    }
}
