package com.example.birthday_v2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.birthday_v2.MainActivity.dbHelper;
import static com.example.birthday_v2.Person.arrayListPerson;

public class ActivityAddEvent extends AppCompatActivity implements View.OnClickListener {
    EditText etDate, etEvent;
    TextView tvDate, tvPersona;
    Button btnData, btnOk, btnCancel;
    String formatDate;
    long date;//= "";
    final String LOG_TAG = "LogsActivityEvent";
    int position;
    int idPersone;
    ArrayList arrayListEventThisPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", position);//Получили перданные данные из ActivityPerson
        String toStr = arrayListPerson.get(position).toString();
        idPersone = arrayListPerson.get(position).getIdPerson();
        tvPersona = (TextView) findViewById(R.id.persona);
        tvPersona.setText(toStr);
        tvDate = (TextView) findViewById(R.id.date_event);

        btnData = (Button) findViewById(R.id.date_pick);
        btnOk = (Button) findViewById(R.id.ok_event);
        btnCancel = (Button) findViewById(R.id.cancel_event);

        btnData.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

//        etDate = (EditText) findViewById(R.id.add_date_event);
        etEvent = (EditText) findViewById(R.id.add_event);
//        selectData = (Button) findViewById(R.id.date_pick);
        Log.d(LOG_TAG, "iiiiiiiiiii position = " + position);


        arrayListEventThisPerson = new ArrayList();
        inListEventThisPerson();
        ListView lvEve = (ListView) this.findViewById(R.id.lv_eve);
        // ListView lvEvent = (ListView) this.findViewById(R.id.list_event);
        ArrayAdapter<Event> lvAdapterMonth = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                arrayListEventThisPerson);
        lvEve.setAdapter(lvAdapterMonth);
        lvEve.setSelection(0);
        //++++++++++++++++++++++
        lvEve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ActivityAddEvent.this, "Выбрано ->" + String.valueOf(position)
                        + " = " + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void inListEventThisPerson() {
        arrayListEventThisPerson.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // переменные для query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // делаем запрос всех данных из таблицы event, получаем Cursor
        Cursor cursor = db.query("event", null, null, null, null, null, null);
//+++++++++++++++++++++++++++++
//
//        String query = "SELECT * "// + "id" + ", " + "date" + ", " + "event" + ", " + "idperson"
//                + " FROM " + "event "
//                +"WHERE idperson = ?";
//        Cursor cursor = db.rawQuery(query, new String[] {"idPersone"});
//====================
//        String newDate = new Date().toString();
//
//        Log.d(LOG_TAG, "@@@@@@@@======== " + newDate + "calendar.get(Calendar.MONTH)========@@@@@@@" + calendar.get(Calendar.MONTH));

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (cursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = cursor.getColumnIndex("id");
            int dateColIndex = cursor.getColumnIndex("date");
            int eventColIndex = cursor.getColumnIndex("event");
            int id_personColIndex = cursor.getColumnIndex("idperson");

            do {
                Event event = new Event();
                event.setId(cursor.getInt(idColIndex));
                event.setDate(cursor.getString(dateColIndex));
                event.setEvent(cursor.getString(eventColIndex));
                event.setIdPerson(cursor.getInt(id_personColIndex));

                Log.d(LOG_TAG, ">>>>>>>>>>> c.getString(dateColIndex) " + cursor.getString(dateColIndex));
                int thisPerson = (event.getIdPerson());
                Log.d(LOG_TAG, "<<<<<<<<<<<" + thisPerson + "<<<<<<<<<<<<<<<<<<" );

                if (thisPerson != idPersone) {
                    Log.d(LOG_TAG, idPersone + " ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ if ");
                    cursor.moveToNext();
                    continue;
                }
                arrayListEventThisPerson.add(event);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (cursor.moveToNext());
        } else
            cursor.close();
    }

    //    @RequiresApi(api = Build.VERSION_CODES.O)
    public void okEvent() {
        // создаем объект для данных
        ContentValues contentValues = new ContentValues();

        // получаем данные из полей ввода

        String event = etEvent.getText().toString();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//

        Log.d(LOG_TAG, "--- Insert in event table: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение
        contentValues.put("date", formatDate);
        contentValues.put("event", event);
        contentValues.put("idperson", idPersone);

        // вставляем запись и получаем ее ID
        long rowID = db.insert("event", null, contentValues);

        Log.d(LOG_TAG, "row inserted event, ID = " + rowID);
        finish();
    }


    public void datePick() {
        Calendar C = Calendar.getInstance();//возвращает объект класса Calendar для региональных данных и часового пояса по умолчанию

        final long[] unixTime = new long[1];
        final int[] year = {C.get(Calendar.YEAR)};
        final int[] month = {C.get(Calendar.MONTH)};
        final int[] day = {C.get(Calendar.DAY_OF_MONTH)};
        DatePickerDialog picker = new DatePickerDialog(this, null, year[0], month[0], day[0]) {
            @Override
            public void onDateChanged(DatePicker view, int _year, int _month, int _day) {
                unixTime[0] = new GregorianCalendar(_year, _month, _day).getTimeInMillis();
            }
        };

        picker.setButton(DialogInterface.BUTTON_POSITIVE, "Выбрать", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                date = ((day[0] < 10) ? "0" : "") + day[0] + "/" + ((month[0] < 9) ? "0" : "") + (month[0] + 1) + "/" + year[0];
//                Toast.makeText(ActivityAddEvent.this, "Выбранная	дата	дд/мм/гггг	:	" + date, Toast.LENGTH_SHORT).show();
                date = unixTime[0];
                Date d = new Date(date);

                formatDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
//                date = year[0] + "-" + ((month[0] < 9) ? "0" : "") + (month[0] + 1) + "-" + ((day[0] < 10) ? "0" : "") + day[0];
                Toast.makeText(ActivityAddEvent.this, "Выбранная дата dd-MM-yyyy	:" + formatDate, Toast.LENGTH_SHORT).show();

                tvDate.setText(formatDate);

            }
        });
        picker.setButton(DialogInterface.BUTTON_NEGATIVE, "Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ActivityAddEvent.this, "Отменено", Toast.LENGTH_SHORT).show();
                //   tvDate.setText(date);

            }
        });
        picker.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_pick:
                datePick();
                break;
            case R.id.ok_event:
                okEvent();
                break;
            case R.id.cancel_event:
                finish();
                break;
        }
    }
}
