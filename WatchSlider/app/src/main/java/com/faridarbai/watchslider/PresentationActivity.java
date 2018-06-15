package com.faridarbai.watchsliderbeta;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.PrintWriter;
import java.net.Socket;

public class PresentationActivity extends WearableActivity {
    static public int SERVER_PORT = 1010;
    
    static public String BACK_CODE = "BACK\n";
    static public String NEXT_CODE = "NEXT\n";
    
    static public Socket socket;
    static public PrintWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        Intent calling_intent = getIntent();
        
        String ip_address = calling_intent.getExtras().getString("ip_address");
        
        ConnectTask connect_task = new ConnectTask(ip_address);
        connect_task.execute();
    }

    
    class ConnectTask extends AsyncTask<Void, Void, Void> {
        String ip_address;
        
        public ConnectTask(String ip_address) {
            super();
            this.ip_address = ip_address;;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket(this.ip_address, SERVER_PORT);
                writer = new PrintWriter(socket.getOutputStream());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }
    
    class MessagingTask extends AsyncTask<Void, Void, Void> {
        String message;
        
        public MessagingTask(String message){
            super();
            this.message = message;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                writer.write(message);
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }
    
    
    @Override
    public boolean onKeyDown(int key_code, KeyEvent key_event){
        MessagingTask messaging_task = new MessagingTask(BACK_CODE);
        messaging_task.execute();

        return true;
    }

    public void onNextClicked(View v){
        MessagingTask messaging_task = new MessagingTask(NEXT_CODE);
        messaging_task.execute();
    }

    public void onBackClicked(View v){
        MessagingTask messaging_task = new MessagingTask(BACK_CODE);
        messaging_task.execute();
    }
}
