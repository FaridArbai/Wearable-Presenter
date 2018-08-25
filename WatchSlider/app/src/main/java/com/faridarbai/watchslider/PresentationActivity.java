package com.faridarbai.watchsliderbeta;

import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.wear.widget.CircularProgressLayout;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.PrintWriter;
import java.net.Socket;

public class PresentationActivity extends WearableActivity {
	private static final String TAG = "PresentationActivity";
	static public String BACK_CODE = "BACK\n";
	static public String NEXT_CODE = "NEXT\n";
 
	private PresentationTimer time_handler;
	
	private PresentationDescriptor descriptor;
	private GestureDetector detector;
	private DismissOverlayView dismiss_overlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.presentation_layout);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		this.getDescriptorFromIntent();
		this.initPresentationLayout();
		this.initGesturesActions();
	}
	
	private void initGesturesActions(){
		this.dismiss_overlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
		this.dismiss_overlay.setIntroText("Something");
		this.dismiss_overlay.showIntroIfNecessary();
		
		this.detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
			@Override
			public void onLongPress(MotionEvent e) {
				Log.d(TAG, "onLongPress: LONG PRESS");
				super.onLongPress(e);
				PresentationActivity.this.finish();
			}
			
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				Log.d(TAG, "onDoubleTap: DOUBLE TAP");
				PresentationActivity.this.requestBack();
				return super.onDoubleTap(e);
			}
			
			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				Log.d(TAG, "onSingleTapConfirmed: ONE TAP");
				PresentationActivity.this.requestNext();
				return super.onSingleTapConfirmed(e);
			}
		});
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.detector.onTouchEvent(ev)  || super.onTouchEvent(ev);
    }
	
	
	private void getDescriptorFromIntent(){
		Intent intent = getIntent();
    	this.descriptor = (PresentationDescriptor)intent.getExtras().getSerializable("PresentationDescriptor");
	}
	
	private void initPresentationLayout(){
		final TextView remaining_time = this.findViewById(R.id.remaining_time);
		int duration = this.descriptor.getDuration();
		int duration_millis = (duration*60*1000);
		
		CircularProgressLayout background_progress = findViewById(R.id.background_progress);
		background_progress.setTotalTime(1000);
		background_progress.startTimer();
		
		CircularProgressLayout progress = findViewById(R.id.progress);
		progress.setTotalTime(duration_millis);
		progress.startTimer();
		
		this.time_handler = new PresentationTimer(duration_millis, 1000,
				this.descriptor, remaining_time, this);
		
		this.time_handler.start();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.time_handler.cancel();
	}
	
	@Override
	public boolean onKeyDown(int key_code, KeyEvent key_event){
		this.requestBack();
		
		return true;
	}
	
	public void requestNext(){
		MainActivity.MessagingTask messaging_task = new MainActivity.MessagingTask(NEXT_CODE);
		messaging_task.execute();
		Log.d(TAG, "requestNext: SENDING NEXT");
	}

	public void requestBack(){
		MainActivity.MessagingTask messaging_task = new MainActivity.MessagingTask(BACK_CODE);
		messaging_task.execute();
		Log.d(TAG, "requestBack: SENDING BACK");
	}
}
