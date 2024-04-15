package com.example.secondapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Register extends Activity implements View.OnClickListener, java.io.Serializable  {

    Button signUp_button;
    ImageButton previousPage;
    EditText name,surname,e_mail,student_id;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name_EditText);
        surname = findViewById(R.id.surname_EditText);
        e_mail = findViewById(R.id.e_mail_editTxt);
        signUp_button = findViewById(R.id.signUp_Button);
        previousPage = findViewById(R.id.previousPageButton);
        previousPage.setOnClickListener(this);
        password = findViewById(R.id.password_EditText);
        student_id = findViewById(R.id.student_id);
        signUp_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        ArrayList<UserType> tmp_users = readFromFile("users.dat");

        if (view.getId() == R.id.signUp_Button) {
            if (!name.getText().toString().isEmpty() && !surname.getText().toString().isEmpty()
                    && !password.getText().toString().isEmpty()&& searchUser(tmp_users,e_mail.getText().toString()) == 0) {

                if (e_mail.getText().toString().contains("@yildiz.edu.tr")) {

                    UserType instructor = new Instructor(name.getText().toString(),
                            surname.getText().toString(), e_mail.getText().toString()
                            , password.getText().toString());

                        tmp_users.add(instructor);
                        writeToEndOfFile(tmp_users, "users.dat");
                        Toast.makeText(getApplicationContext(), "Akademisyen kaydedildi", Toast.LENGTH_SHORT).show();
                } else if (!student_id.getText().toString().isEmpty() && e_mail.getText().toString().contains("@std.yildiz.edu.tr")) {

                    UserType student = new Student(name.getText().toString(),
                            surname.getText().toString(), e_mail.getText().toString()
                            , student_id.getText().toString(), password.getText().toString());
                        tmp_users.add(student);
                        writeToEndOfFile(tmp_users, "users.dat");
                        Toast.makeText(getApplicationContext(), "Öğrenci kaydedildi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bilgileriniz hatalı. Geçerli bilgiler giriniz", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Bilgilerinizde hata olabilir veya e mail kullanılıyor olabilir", Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId() == R.id.previousPageButton){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



     public void writeToEndOfFile(ArrayList<UserType> users, String filename) {
           String  filePath = getApplicationContext().getFilesDir() + File.separator + filename;
         try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
              ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

             objectOutputStream.writeObject(users);
             objectOutputStream.close();
             objectOutputStream.flush();
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         } catch (IOException e) {
             throw new RuntimeException(e);
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


    public int searchUser(ArrayList<UserType> users,String e_mail){

        for(UserType tmp : users){
            if(tmp.getE_mail().equals(e_mail)){
                return 1;
            }
        }
        return 0;

    }



}

