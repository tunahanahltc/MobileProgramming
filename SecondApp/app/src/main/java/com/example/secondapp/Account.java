package com.example.secondapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;



public class Account extends Activity implements Serializable,View.OnClickListener {

    UserType tempUser,loginUser;
    ArrayList<UserType> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setupListView();
        Intent intent = getIntent();
        if (intent != null) {
             loginUser = (UserType) intent.getSerializableExtra("loginUser");

    }}

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }

    private void setupListView() {
        ListView listViev_ex = findViewById(R.id.listAccounts);
        users = readFromFile("users.dat");

        final ArrayAdapter<UserType> adapter = new ArrayAdapter<UserType>(this, android.R.layout.simple_list_item_1, users) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                int color = getColorForUser(getItem(position));
                view.setBackgroundColor(color);
                textView.setTextColor(Color.BLACK);
                return view;
            }

            private int getColorForUser(UserType user) {
                if (user.getE_mail().contains("@std.yildiz.edu.tr")) {
                    return Color.rgb(255, 204, 204);
                } else if (user.getE_mail().contains("@yildiz.edu.tr")) {
                    return Color.rgb(204, 229, 255);
                } else {
                    return Color.WHITE;
                }
            }
        };

        listViev_ex.setAdapter(adapter);
        listViev_ex.setOnItemClickListener((parent, view, position, id) -> {
            tempUser = adapter.getItem(position);
            onClick(view);
            onDestroy();
        });
    }
    public ArrayList<UserType> readFromFile(String fileName) {
        ArrayList<UserType> objectList = new ArrayList<>();

        String file_path = getApplicationContext().getFilesDir() + File.separator + fileName;
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


    @Override
    public void onClick(View v) {
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra("userType", tempUser);
            intent.putExtra("loginUser",loginUser);

            startActivity(intent);

    }
}