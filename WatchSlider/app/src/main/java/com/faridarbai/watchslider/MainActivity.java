package com.faridarbai.watchsliderbeta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends WearableActivity {
	static public int SERVER_PORT = 1010;
	private static final String TAG = "MainActivity";
	static public Socket socket;
	static public PrintWriter writer;
	
	private PresentationDescriptor descriptor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.descriptor = new PresentationDescriptor();
		this.openIpLayout();
	}
	
	private void openIpLayout(){
		setContentView(R.layout.ip_layout);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		final ImageButton done_button = this.findViewById(R.id.done_button);
		final ImageButton exit_button = this.findViewById(R.id.exit_button);
		
		final EditText ip_field1 = this.findViewById(R.id.ip_field1);
		final EditText ip_field2 = this.findViewById(R.id.ip_field2);
		final EditText ip_field3 = this.findViewById(R.id.ip_field3);
		final EditText ip_field4 = this.findViewById(R.id.ip_field4);
		
		done_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String ip_address = String.format("%s.%s.%s.%s",
						ip_field1.getText().toString(),
						ip_field2.getText().toString(),
						ip_field3.getText().toString(),
						ip_field4.getText().toString());
				
				ConnectTask connect_task = new ConnectTask(ip_address, MainActivity.this);
				connect_task.execute();
			}
		});
		
		exit_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity.this.finish();
			}
		});
	}
	
	private void openDurationFragment(){
		this.setContentView(R.layout.value_layout);
		final EditText value = this.findViewById(R.id.value);
		final TextView title = this.findViewById(R.id.title);
		
		title.setText("Presentation duration (mins.):");
		
		final ImageButton done_button = this.findViewById(R.id.done_button);
		final ImageButton cancel_button = this.findViewById(R.id.cancel_button);
		
		done_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					int duration = Integer.parseInt(value.getText().toString());
					
					if(duration>0){
						MainActivity.this.descriptor.setDuration(duration);
						MainActivity.this.openCheckpointsFragment();
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		cancel_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.this.openIpLayout();
			}
		});
		
	}
	
	private void openCheckpointsFragment(){
		this.setContentView(R.layout.checkpoints_layout);
		final WearableRecyclerView checkpoints_view = this.findViewById(R.id.checkpoints_view);
		this.descriptor.addCheckpoint(this.descriptor.getDuration());
		
		final CheckpointsViewAdapter adapter = new CheckpointsViewAdapter(this.descriptor.getCheckpoints(), this.descriptor.getDuration(), this);
		checkpoints_view.setEdgeItemsCenteringEnabled(true);
		checkpoints_view.setAdapter(adapter);
		checkpoints_view.setLayoutManager(new WearableLinearLayoutManager(this));
		
		final ImageButton done_button = this.findViewById(R.id.done_button);
		final ImageButton add_button = this.findViewById(R.id.add_button);
		final ImageButton cancel_button = this.findViewById(R.id.cancel_button);
		
		add_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.add(MainActivity.this.descriptor.getDuration());
				adapter.notifyItemInserted((adapter.getItemCount()-1));
			}
		});
		
		done_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.this.descriptor.prepareCheckpoints();
				MainActivity.this.openVibrationStrengthFragment();
			}
		});
		
		cancel_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.this.openDurationFragment();
			}
		});
	}
	
	private void openVibrationStrengthFragment(){
		this.setContentView(R.layout.vibration_layout);
		final SeekBar vibration_value = this.findViewById(R.id.value);
		
		final ImageButton done_button = this.findViewById(R.id.done_button);
		final ImageButton cancel_button = this.findViewById(R.id.cancel_button);
		
		done_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int value = vibration_value.getProgress();
				MainActivity.this.descriptor.setVibrationStrength(value);
				MainActivity.this.startPresentation();
			}
		});
		
		cancel_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.this.openCheckpointsFragment();
			}
		});
	}
	
	private void startPresentation(){
		Intent intent = new Intent(this, PresentationActivity.class);
		intent.putExtra("PresentationDescriptor", this.descriptor);
		startActivity(intent);
	}
	
	public static class ConnectTask extends AsyncTask<Void, Void, Void> {
		String ip_address;
  		boolean connected;
  		MainActivity activity;
  		
		public ConnectTask(String ip_address, MainActivity activity) {
			super();
			this.ip_address = ip_address;
			this.activity = activity;
		}
  
		@Override
		protected Void doInBackground(Void... voids) {
			try {
				MainActivity.socket = new Socket(this.ip_address, MainActivity.SERVER_PORT);
				MainActivity.writer = new PrintWriter(MainActivity.socket.getOutputStream());
				connected = true;
			} catch (Exception ex) {
				connected = false;
				ex.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			
			if(this.connected){
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						activity.openDurationFragment();
					}
				});
			}
			else{
				//notify_error
			}
		}
	}
	
	public static class MessagingTask extends AsyncTask<Void, Void, Void> {
		String message;
		
		public MessagingTask(String message){
			super();
			this.message = message;
		}
  
		@Override
		protected Void doInBackground(Void... voids) {
			try {
				MainActivity.writer.write(message);
				MainActivity.writer.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
