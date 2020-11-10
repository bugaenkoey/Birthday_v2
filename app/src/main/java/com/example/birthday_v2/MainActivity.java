package com.example.birthday_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnGoPerson, btnPrev,btnNext;
    ListView listView;

   static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGoPerson = (Button) findViewById(R.id.go_person);
        btnPrev =(Button) findViewById(R.id.btn_prev_month);
        btnNext =(Button) findViewById(R.id.btn_next_month);
        btnGoPerson.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        listView =(ListView) findViewById(R.id.lv_date_month);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ActivityPerson.class);
        startActivity(intent);
    }
}