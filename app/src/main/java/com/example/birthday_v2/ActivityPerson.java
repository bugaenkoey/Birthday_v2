package com.example.birthday_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityPerson extends AppCompatActivity implements View.OnClickListener {
    Button btnGoEvent;
    final String LOG_TAG = "LogsActivityPerson";
    Person person = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        btnGoEvent = (Button) findViewById(R.id.go_event);
        btnGoEvent.setOnClickListener(this);


    }


    @Override
    protected void onStart() {
        super.onStart();
        inListPerson();
        ListView lvPerson = (ListView) this.findViewById(R.id.list_person);
        ArrayAdapter<Person> lvAdapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                person.arrayListPerson);
        lvPerson.setAdapter(lvAdapter1);
//        lvPerson.setSelection(0);
        //++++++++++++++++++++++
        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG,"%%%%%%%%%%%%%%%%%%%long id ="+id+"  ---"+parent.getItemAtPosition(position));

                int idPersone = person.arrayListPerson.get(position).idPerson;
                Toast.makeText(ActivityPerson.this, "Выбрано ->" + String.valueOf(position)
                        + " = " + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "Выбрано ->" + String.valueOf(position)
                        + " = " + parent.getAdapter().getItem(position) + " idPerson = "
                        + person.arrayListPerson.get(position).idPerson);

                //++++++++
                Intent intent = new Intent(ActivityPerson.this, ActivityAddEvent.class);
                intent.putExtra("position", position);
                startActivity(intent);
                //==========
            }
        });
        //    lvPerson.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        //======================
    }

    private void inListPerson() {
        person.arrayListPerson.clear();
        // подключаемся к БД
        SQLiteDatabase db = MainActivity.dbHelper.getWritableDatabase();

        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("person", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int surNameColIndex = c.getColumnIndex("surname");
            int nameColIndex = c.getColumnIndex("name");
            int patronymicColIndex = c.getColumnIndex("patronymic");
            int telephoneColIndex = c.getColumnIndex("telephone");

            do {
                Person person = new Person();
                person.setIdPerson(c.getInt(idColIndex));
                person.setSurname(c.getString(surNameColIndex));
                person.setName(c.getString(nameColIndex));
                person.setPatronymic(c.getString(patronymicColIndex));
                person.setTelephone(c.getString(telephoneColIndex));

                person.arrayListPerson.add(person);

                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", surname = " + c.getString(surNameColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", patronymic = " + c.getString(patronymicColIndex) +
                                ", telephone = " + c.getString(telephoneColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else

            c.close();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ActivityEvent.class);
        startActivity(intent);
    }
}