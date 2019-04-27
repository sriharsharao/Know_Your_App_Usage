package com.apptime.shyam.know_your_app_usage;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class alarm extends AppCompatActivity {
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        start= (Button) findViewById(R.id.button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlert();
            }
        });
    }

    public void startAlert(){
        EditText text = (EditText) findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        Toast.makeText(this, "Alarm set in " + i + " seconds",Toast.LENGTH_LONG).show();

        EditText text1 = (EditText) findViewById(R.id.time);
        Integer t = Integer.parseInt(text1.getText().toString());
        final TextView ve =(TextView)findViewById(R.id.textView4);
        CountDownTimer countDownTimer = new CountDownTimer(t * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                ve.setText("Seconds remaining: " + millisUntilFinished / 1000);

            }
            public void onFinish() {
                ve.setText("Done !");


            }
        };
        countDownTimer.start();



    }
    public void nn(View view)
    {



    }

}