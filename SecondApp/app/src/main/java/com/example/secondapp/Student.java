package com.example.secondapp;


import java.io.Serializable;

public class Student extends UserType implements Serializable {
    private String student_id;


    public Student(String name, String surname, String e_mail, String student_id, String password) {
        super(name, surname, e_mail, password,"Öğrenci");
        this.student_id = student_id;


    }
    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    @Override
    public String toString() {
        return "Öğrenci :  \n" +"İsim: "+ getName() +"\n" +"E-mail: "+ getE_mail()+"\n" +"Öğrenci No: "+ getStudent_id();
    }
}
