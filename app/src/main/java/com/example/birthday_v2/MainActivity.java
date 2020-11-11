package com.example.birthday_v2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnGoPerson, btnPrev, btnNext;
    ListView listView;
    TextView textView;
    int month;
    final String LOG_TAG = "LogsActivityMain";

    static DBHelper dbHelper;
    static Calendar calendar = Calendar.getInstance();
    int monthEvent = calendar.get(Calendar.MONTH) + 1;
//    Date date = new Date(); // получаем текущую дату

    //int year = date.getYear();
//		int monthEvent = date.getMonth()+1;// номер месяца
    //int dey = date.getDayOfMonth();// номер дня в месяце

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGoPerson = (Button) findViewById(R.id.go_person);
        btnPrev = (Button) findViewById(R.id.btn_prev_month);
        btnNext = (Button) findViewById(R.id.btn_next_month);
        btnGoPerson.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.lv_date_month);
        textView = (TextView) findViewById(R.id.tv_month);
        textView.setText(String.valueOf(monthEvent));//преобразуем int месяца в  String
        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

    }

//    @Override
//    public void onClick(View v) {
//
//    }

//    public void onClickPrev(View view) {
//    }
//
//    public void onClickNext(View view) {
//    }
//
//    public void onClickPerson(View view) {
//        Intent intent = new Intent(this,ActivityPerson.class);
//        startActivity(intent);
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_next_month:

                // monthEvent = calendar.get(Calendar.MONTH)
                calendar.add(Calendar.MONTH, 1);
                monthEvent = calendar.get(Calendar.MONTH) + 1;

                textView.setText(String.valueOf(monthEvent));//преобразуем int месяца в  String
                Log.d(LOG_TAG, "--- btn_next_month: ---" + monthEvent);
                break;
            case R.id.btn_prev_month:

                calendar.add(Calendar.MONTH, -1);
                monthEvent = calendar.get(Calendar.MONTH) + 1;

                textView.setText(String.valueOf(monthEvent));//преобразуем int месяца в  String
                Log.d(LOG_TAG, "--- btn_prev_month: ---" + monthEvent);
                break;
            case R.id.go_person:
                Log.d(LOG_TAG, "--- go_person: ---");
                Intent intent = new Intent(this, ActivityPerson.class);
                startActivity(intent);
                break;
        }
    }
}