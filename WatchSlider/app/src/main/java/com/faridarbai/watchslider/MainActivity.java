package com.faridarbai.watchsliderbeta;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;

import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends WearableActivity {
    static public String SERVER_IP = "192.168.0.157";
    static public int SERVER_PORT = 1010;

    static public Button next_button;
    static public Button back_button;

    static public String BACK_CODE = "BACK\n";
    static public String NEXT_CODE = "NEXT\n";

    static public Socket socket;
    static public PrintWriter writer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAmbientEnabled();

        ConnectTask ctask = new ConnectTask("CONNECT");
        ctask.execute();

    }

    class ConnectTask extends AsyncTask<Void, Void, Void> {
        String type;
        String message;

        public ConnectTask(String type) {
            super();
            this.type = type;
        }

        public ConnectTask(String type, String message) {
            super();
            this.type = type;
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (type.equals("CONNECT")) {
                    socket = new Socket(SERVER_IP, SERVER_PORT);
                    writer = new PrintWriter(socket.getOutputStream());
                } else {
                    writer.write(this.message);
                    writer.flush();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }

    public void onNextClicked(View v){
        ConnectTask ctask = new ConnectTask("MESSAGE", NEXT_CODE);
        ctask.execute();
    }

    public void onBackClicked(View v){
        ConnectTask ctask = new ConnectTask("MESSAGE", BACK_CODE);
        ctask.execute();
    }



}
