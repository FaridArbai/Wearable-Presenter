package com.faridarbai.watchsliderbeta;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends WearableActivity {
    
    EditText ip_edit1, ip_edit2, ip_edit3, ip_edit4;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.ip_edit1 = findViewById(R.id.ip_edit1);
        this.ip_edit2 = findViewById(R.id.ip_edit2);
        this.ip_edit3 = findViewById(R.id.ip_edit3);
        this.ip_edit4 = findViewById(R.id.ip_edit4);
    }
    
    public void onDoneClicked(View view){
        String ip_address = String.format("%s.%s.%s.%s", ip_edit1.getText().toString(),
				  															ip_edit2.getText().toString(),
				  															ip_edit3.getText().toString(),
				  															ip_edit4.getText().toString());
        
        Intent presentation_intent = new Intent(this, PresentationActivity.class);
        presentation_intent.putExtra("ip_address", ip_address);
        
        startActivity(presentation_intent);
    }

}
