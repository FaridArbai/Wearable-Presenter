package com.faridarbai.watchsliderbeta;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.activity.WearableActivity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PresentationTimer extends CountDownTimer{
	private final static int WEAK_VIBRATION_THRESHOLD = 100;
	private final Vibrator VIBRATOR;
	private final WearableActivity ENVIRONMENT;
	private final PresentationDescriptor descriptor;
	private final TextView remaining_time;
	private int finished_checkpoints;
	private int total_checkpoints;
	private int target_checkpoint;
	private boolean finished_all_checkpoints;
	
	
	
	public PresentationTimer(long duration_millis, long count_down,
									 PresentationDescriptor descriptor,
									 TextView remaining_time, WearableActivity context){
		super(duration_millis, count_down);
		this.descriptor = descriptor;
		this.remaining_time = remaining_time;
		
		this.finished_checkpoints = 0;
		this.total_checkpoints = this.descriptor.getNumberOfPoints();
		this.target_checkpoint = this.descriptor.getTimestamp(finished_checkpoints);
		
		this.VIBRATOR = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		this.ENVIRONMENT = context;
		
		this.finished_all_checkpoints = false;
	}
	
	@Override
	public void onTick(long millis_until_finished) {
		long remaining_seconds = (long)(millis_until_finished/1000);
		long remaining_minutes = (remaining_seconds/60);
		long additional_seconds = (remaining_seconds%60);
		
		String countdown_str = String.format("%02d:%02d", remaining_minutes, additional_seconds);
		this.remaining_time.setText(countdown_str);
		
		if(!finished_all_checkpoints) {
			if ((remaining_minutes == target_checkpoint)&&(additional_seconds==0)) {
				this.emitNotification();
				this.finished_checkpoints++;
				if (this.finished_checkpoints != this.total_checkpoints) {
					target_checkpoint = this.descriptor.getTimestamp(finished_checkpoints);
				}
				else{
					finished_all_checkpoints = true;
				}
			}
		}
		
	}
	
	@Override
	public void onFinish() {
		this.remaining_time.setText("FINISHED!");
	}
	
	private void emitNotification(){
		int strength = descriptor.getVibrationStrength();
		if(strength < WEAK_VIBRATION_THRESHOLD){
			this.performHapticFeedback();
		}
		else{
			this.performVibeFeedback(strength);
		}
	}
	
	private void performHapticFeedback(){
		PresentationTimer.this.ENVIRONMENT.getWindow().
						getDecorView().performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS);
	}
	
	private void performVibeFeedback(int strength){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			VIBRATOR.vibrate(VibrationEffect.createOneShot(strength,VibrationEffect.DEFAULT_AMPLITUDE));
		}else{
			VIBRATOR.vibrate(strength);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
