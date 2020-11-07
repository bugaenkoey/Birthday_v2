package com.example.birthday_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityPerson extends AppCompatActivity implements View.OnClickListener {
    Button btnGoEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        btnGoEvent = (Button)findViewById(R.id.go_event);
        btnGoEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ActivityEvent.class);
        startActivity(intent);
    }
}