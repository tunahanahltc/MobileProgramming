package com.example.secondapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Login extends Activity implements View.OnClickListener, Serializable {
    EditText e_mail,password;
    Button registerButton,loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        e_mail = findViewById(R.id.login_e_mail_editTxt);
        password = findViewById(R.id.login_password_EditText);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        registerButton = findViewById(R.id.register_Button);
        registerButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_button) {
            ArrayList<UserType> users = readFromFile("users.dat");
            if (e_mail.getText().toString().contains("@std.yildiz.edu.tr")) {


                for (UserType student : users) {

                    if (student.getE_mail().equals(e_mail.getText().toString())&& student.getPassword().equals(password.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Öğrenci bulundu.", Toast.LENGTH_SHORT).show();

                        users.clear();
                        Intent intent = new Intent(this,Account.class);
                        intent.putExtra("loginUser", student);
                        startActivity(intent);
                        break;
                    }
                }

            } else if (e_mail.getText().toString().contains("@yildiz.edu.tr")) {
                for (UserType instructor : users) {
                    if (instructor.getE_mail().equals(e_mail.getText().toString()) && instructor.getPassword().equals(password.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Akademisyen bulundu.", Toast.LENGTH_SHORT).show();
                        users.clear();
                        Intent intent = new Intent(this,Account.class);
                        intent.putExtra("loginUser", instructor);
                        startActivity(intent);

                        break;
                    }
                }
            } else if (e_mail.getText().toString().contains("admin") && password.getText().toString().equals("admin")) {

                Intent intent = new Intent(this,Account.class);
                startActivity(intent);

            } else{
                Toast.makeText(getApplicationContext(), "E posta gecersiz.", Toast.LENGTH_SHORT).show();

            }

        }
        else if (view.getId() == R.id.register_Button) {
            Intent intent = new Intent(this,Register.class);
            startActivity(intent);
        }
    }


    public ArrayList<UserType> readFromFile(String fileName) {
        ArrayList<UserType> objectList = new ArrayList<>();

        String file_path =  getApplicationContext().getFilesDir()+ File.separator + fileName;
        try (FileInputStream fileInputStream = new FileInputStream(file_path);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                objectList = (ArrayList<UserType>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objectList;
    }


}
