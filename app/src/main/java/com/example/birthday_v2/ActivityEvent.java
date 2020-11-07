package com.example.birthday_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityEvent extends AppCompatActivity implements View.OnClickListener {
Button btnGoAddEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        btnGoAddEvent = (Button)findViewById(R.id.go_add_event);
        btnGoAddEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ActivityAddEvent.class);
        startActivity(intent);
    }
}