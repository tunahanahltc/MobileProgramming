package com.example.secondapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Profile extends Activity implements View.OnClickListener{

    EditText name,telNo,educate,mail,studentId;
    TextView statu;
    Button saveButton,editButton;
    UserType loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_inform);
        saveButton = findViewById(R.id.saveButton);
        editButton = findViewById(R.id.editButton);

        statu = findViewById(R.id.statu);
        name = findViewById(R.id.profile_name);
        telNo = findViewById(R.id.profile_phone);
        educate = findViewById(R.id.profile_educate);
        mail = findViewById(R.id.profile_mail);
        studentId = findViewById(R.id.profile_studentId);
        telNo.setEnabled(false);
        educate.setEnabled(false);

        Intent intent = getIntent();
        if (intent != null) {
            UserType userType = (UserType) intent.getSerializableExtra("userType");
             loginUser = (UserType) intent.getSerializableExtra("loginUser");
            // Burada userType nesnesinin niteliklerine erişebilirsiniz

            name.setText(userType.getName());
            mail.setText(userType.getE_mail());
            statu.setText(userType.getStatu());
            educate.setText(userType.getEducateInform());
            telNo.setText(userType.getPhone_number());
            if(userType.getE_mail().contains("@std.yildiz.edu.tr")){
                Student student = (Student) userType;
                studentId.setText(student.getStudent_id());
            }
            else {
                studentId.setVisibility(View.GONE);
                findViewById(R.id.textView9).setVisibility(View.GONE);

            }
        }

        saveButton.setOnClickListener(this);
        editButton.setOnClickListener(this);


    }
    @Override
    protected void onStop() {
        super.onStop();
        // Gereksiz kaynakları serbest bırak
        // Örnek: Çalışan işlemleri durdur
    }

    @Override
    protected void onDestroy() {
        // Ek olarak, onDestroy() metodunu da kontrol edebilirsiniz.
        // Gereksiz kaynakları serbest bırakmak için kullanabilirsiniz.
        super.onDestroy();
    }



    @Override
    public void onClick(View v) {
        if(loginUser == null || mail.getText().toString().equals(loginUser.getE_mail())) {
            if (v.getId() == R.id.editButton) {

                editButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                telNo.setEnabled(true);
                educate.setEnabled(true);
                if(loginUser == null){
                    name.setEnabled(true);



                }

            } else {
                saveButton.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.VISIBLE);
                telNo.setEnabled(false);
                educate.setEnabled(false);


                ArrayList<UserType> tmp_users = readFromFile("users.dat");
                UserType user1 = findUserByEmail(tmp_users, mail.getText().toString());
                tmp_users.remove(user1);

                user1.setEducateInform(educate.getText().toString());
                user1.setPhone_number(telNo.getText().toString());
                if(loginUser == null){

                    name.setEnabled(false);
                    user1.setName(name.getText().toString());


                }
                System.out.println(user1);
                tmp_users.add(user1);
                writeToEndOfFile(tmp_users, "users.dat");
            }

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
    private UserType findUserByEmail(ArrayList<UserType> users, String email) {
        for (UserType user : users) {
            if (user.getE_mail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
