package com.examle.yogeshkumar.sms_demo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText num,message;
    Button send,sendsmsapi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    num=(EditText)findViewById(R.id.noet);
        message=(EditText)findViewById(R.id.msget);
        send=(Button)findViewById(R.id.sendsms);
        sendsmsapi=(Button)findViewById(R.id.sendapi);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=num.getText().toString();
                String msg=message.getText().toString();
                Intent smsIntent=new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address",number);
                smsIntent.putExtra("sms_body",msg);
                startActivity(smsIntent);
            }
        });

        sendsmsapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=num.getText().toString();
                String msg=message.getText().toString();
                String SMS_SENT="SMS_SENT";
                String SMS_DELIVERED="SMS_DELIVERED";

                PendingIntent sendIntent=PendingIntent.getBroadcast(MainActivity.this,0,new Intent(SMS_SENT),0);
                PendingIntent deliverIntent=PendingIntent.getBroadcast(MainActivity.this,0,new Intent(SMS_DELIVERED),0);

                 registerReceiver(new BroadcastReceiver() {
                 @Override
                 public void onReceive(Context context, Intent intent) {
                  switch (getResultCode()){
                   case Activity.RESULT_OK:
                   Toast.makeText(context,"Message sent succesfully",Toast.LENGTH_SHORT).show();
                       break;

        }
    }


        },new IntentFilter(SMS_SENT));




                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {

                        switch(getResultCode()){
                            case Activity.RESULT_OK:
                                Toast.makeText(getBaseContext(),"sms delivered",Toast.LENGTH_SHORT).show();
                                break;

                            case Activity.RESULT_CANCELED:
                                Toast.makeText(getBaseContext(), "sms not sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                },new IntentFilter(SMS_DELIVERED));
                SmsManager manager=SmsManager.getDefault();
                manager.sendTextMessage(number,null,msg,sendIntent,deliverIntent);
            }
        });

    }
}
