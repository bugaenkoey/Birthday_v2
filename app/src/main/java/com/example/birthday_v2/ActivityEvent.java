package com.example.birthday_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import static com.example.birthday_v2.MainActivity.dbHelper;

public class ActivityEvent extends AppCompatActivity implements View.OnClickListener {
    Button btnOk, btnCancel;
    EditText etSurname, etName, etPatronymic, etTelephone;
    Event event = new Event();
    final String LOG_TAG = "LogsActivityEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        etSurname = (EditText) findViewById(R.id.surname_person);
        etName = (EditText) findViewById(R.id.name_person);
        etPatronymic = (EditText) findViewById(R.id.patronymic_person);
        etTelephone = (EditText) findViewById(R.id.telephone_person);

        btnOk= (Button)findViewById(R.id.ok_person);
        btnCancel= (Button)findViewById(R.id.cancel_person);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
//        btnGoAddEvent = (Button) findViewById(R.id.go_add_event);
//        btnGoAddEvent.setOnClickListener(this);

        inListEvent();
        ListView lvEvent = (ListView) this.findViewById(R.id.list_event);
        ArrayAdapter<Event> lvAdapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                event.arrayListEvent);
        lvEvent.setAdapter(lvAdapter2);
        lvEvent.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.date_pick:
//                break;
            case R.id.ok_person:
                addPerson();
                break;
            case R.id.cancel_person:
                finish();
                break;
        }
    }
//        Intent intent = new Intent(this, ActivityAddEvent.class);
//        startActivity(intent);
//    }

    public void addPerson() {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String patronymic = etPatronymic.getText().toString();
        String telephone = etTelephone.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
        Log.d(LOG_TAG, "--- Insert in person: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение
        cv.put("surname", surname);
        cv.put("name", name);
        cv.put("patronymic", patronymic);
        cv.put("telephone", telephone);

        // вставляем запись и получаем ее ID
        long rowID = db.insert("person", null, cv);

        Log.d(LOG_TAG, "row inserted person, ID = " + rowID);
//        inListEvent();
        finish();
    }

    private void inListEvent() {
        event.arrayListEvent.clear();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // делаем запрос всех данных из таблицы event, получаем Cursor
        Cursor c = db.query("event", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int dateColIndex = c.getColumnIndex("date");
            int eventColIndex = c.getColumnIndex("event");
            int id_personColIndex = c.getColumnIndex("idperson");

            do {
                Event event = new Event();
                event.setId(c.getInt(idColIndex));
                event.setDate(c.getString(dateColIndex));
                event.setEvent(c.getString(eventColIndex));
                event.setIdPerson(c.getInt(id_personColIndex));

                event.arrayListEvent.add(event);

                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", date = " + c.getString(dateColIndex) +
                                ", event = " + c.getString(eventColIndex) +
                                ", id_person = " + c.getString(id_personColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else

            c.close();
    }
}