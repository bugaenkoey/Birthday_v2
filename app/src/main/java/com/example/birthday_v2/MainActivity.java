package com.example.birthday_v2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnGoPerson, btnPrev, btnNext;
    ListView listView;
    TextView textView;
    final String LOG_TAG = "LogsActivityMain";
    static DBHelper dbHelper;
    static Calendar calendar = Calendar.getInstance();
//    Date d = new Date();
    //    String simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
    int monthEvent = calendar.get(Calendar.MONTH) + 1;
    ArrayList arrayListMonth;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//--- Получение идентификатора выбранного пункта меню---
        int id = item.getItemId();
        //--- Выполнение действия для выбранного пункта меню ---
        switch (id) {
//--- Выбран пункт меню "Settings" ------------------
            case R.id.action_settings:
                Toast.makeText(this,
                        "Settings",
                        Toast.LENGTH_LONG).show();
                return true; //Пункт меню обработан

            case R.id.about: {
              //  msg = "author";
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("Разработчик программы");
                aboutDialog.setMessage("(c)2020 студент академии ШАГ\nБугаенко Евгений.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
                aboutDialog.show();
            }
            break;
        }
            return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onStart() {
        super.onStart();
        arrayListMonth = new ArrayList();
        inListMonth();
        ListView lvMonth = (ListView) this.findViewById(R.id.lv_date_month);
        // ListView lvEvent = (ListView) this.findViewById(R.id.list_event);
        ArrayAdapter<Event> lvAdapterMonth = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                arrayListMonth);
        lvMonth.setAdapter(lvAdapterMonth);
        lvMonth.setSelection(0);
        //++++++++++++++++++++++
        lvMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Выбрано ->" + String.valueOf(position)
                        + " = " + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();

                //++++++++
//                Intent intent = new Intent(MainActivity.this, ActivityAddEvent.class);
//                startActivity(intent);
                //==========
            }
        });

    }

    private void inListMonth2() {
//        arrayListMonth.clear();
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        String query = "SELECT " + "id" + ", "
//                + "date" + ", " + "event" + ", " + "idperson"
//                + " FROM " + "event "
//                +"WHERE date <> ?";
//        Cursor cursor = db.rawQuery(query, new String[] {"13-06-1977"});
    }

    private void inListMonth() {
        arrayListMonth.clear();
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

//        String query = "SELECT * "// + "id" + ", " + "date" + ", " + "event" + ", " + "idperson"
//                + " FROM " + "event "
//                +"WHERE date <> ?";
//        Cursor cursor = db.rawQuery(query, new String[] {"13-06-1977"});
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
                int nMontch = myGetMonth(event.getDate());
                Log.d(LOG_TAG, "<<<<<<<<<<<" + monthEvent + "<<<<<<<<<<<<<<<<<<" + nMontch);

                if (nMontch != monthEvent) {
                    Log.d(LOG_TAG, monthEvent + " ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ if ");
                    cursor.moveToNext();
                    continue;
                }

                arrayListMonth.add(event);

                // получаем значения по номерам столбцов и пишем все в лог
//                    Log.d(LOG_TAG,
//                            "ID = " + cursor.getInt(idColIndex) +
//                                    ", date = " + cursor.getString(dateColIndex) +
//                                    ", event = " + cursor.getString(eventColIndex) +
//                                    ", id_person = " + cursor.getString(id_personColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (cursor.moveToNext());
        } else
            cursor.close();
    }

    private int myGetMonth(String strGetDate) {
        // разделение строки на части
        String[] parts = strGetDate.split("-");
        return Integer.parseInt(parts[1]);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_next_month:
                // Увеличить на 1 месяц
                monthEvent = monthEvent == 12 ? 1 : ++monthEvent;
                break;
            case R.id.btn_prev_month:
                // Увеличить на -1 месяц (уменьшить на 1 )
                monthEvent = monthEvent == 1 ? 12 : --monthEvent;
                break;
            case R.id.go_person:
                Log.d(LOG_TAG, "--- go_person: ---");
                Intent intent = new Intent(this, ActivityPerson.class);
                startActivity(intent);
                break;
        }
        textView.setText(String.valueOf(monthEvent));//преобразуем int месяца в  String
        onStart();
    }


    void msg() {
        Log.d(LOG_TAG, "--- btnMsg: ---");
        // Идентификатор уведомления
        final int NOTIFY_ID = 101;
        // Идентификатор канала
        String CHANNEL_ID = "My channel";

        String msgTitle = "Напоминание";
        String msgText = "Сегодня День рождения";

        Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(msgTitle)
                        .setContentText(msgText)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(contentIntent);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}