package com.example.smsreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmsActivity extends AppCompatActivity {

    TextView textView, textView2, textView3;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Intent intent = getIntent();
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        button = findViewById(R.id.button);

        String sender = intent.getStringExtra("sender");
        String contents = intent.getStringExtra("contents");
        String recievedDate = intent.getStringExtra("recievedDate");

        textView.setText(sender);
        textView2.setText(contents);
        textView3.setText(recievedDate);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
