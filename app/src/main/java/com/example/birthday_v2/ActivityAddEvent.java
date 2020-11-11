package com.example.birthday_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import static com.example.birthday_v2.MainActivity.dbHelper;

public class ActivityAddEvent extends AppCompatActivity {
    EditText etDate, etEvent;

    final String LOG_TAG = "LogsActivityEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        etDate = (EditText) findViewById(R.id.add_date_event);
        etEvent = (EditText) findViewById(R.id.add_event);

    }

    public void on_click_add_event(View view) {
        // создаем объект для данных
        ContentValues cve = new ContentValues();

        // получаем данные из полей ввода
        String date = etDate.getText().toString();
        String event = etEvent.getText().toString();
        int id_person = 1;
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//

        Log.d(LOG_TAG, "--- Insert in event table: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение
        cve.put("date", date);
        cve.put("event", event);
        cve.put("idperson", id_person);

        // вставляем запись и получаем ее ID
        long rowID = db.insert("event", null, cve);

        Log.d(LOG_TAG, "row inserted event, ID = " + rowID);
    }


}
