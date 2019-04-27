package com.apptime.shyam.know_your_app_usage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationView extends AppCompatActivity {
    TextView textView;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Integer t = Integer.parseInt(value);
        Integer t=100;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        textView = (TextView) findViewById(R.id.textView);
        textView1=(TextView)findViewById(R.id.textView4);
        //getting the notification message
        String message=getIntent().getStringExtra("warning ");
        textView.setText(message);


    }






}