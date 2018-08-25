package com.faridarbai.watchsliderbeta;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PresentationDescriptor implements Serializable {
	private static final String TAG = "PresentationDescriptor";
	
	private int duration;
	private int vibration_strength;
	private ArrayList<Integer> checkpoints;
	
	
	public PresentationDescriptor(){
		this.duration = 0;
		this.vibration_strength = 0;
		this.checkpoints = new ArrayList<>();
	}
	
	public PresentationDescriptor(int duration, int vibration_strength, ArrayList<Integer> checkpoints){
		this.duration = duration;
		this.vibration_strength = vibration_strength;
		this.checkpoints = checkpoints;
	}
	
	public PresentationDescriptor(int duration){
		this.duration = duration;
	}
	
	public int getNumberOfPoints(){
		return this.checkpoints.size();
	}
	
	public int getCheckpoint(int index){
		return this.checkpoints.get(index);
	}
	
	public int getTimestamp(int index){
		int checkpoint = this.getCheckpoint(index);
		int timestamp = this.duration - checkpoint;
		return timestamp;
	}
	
	public int getVibrationStrength(){
		return this.vibration_strength;
	}
	
	public int getDuration(){
		return this.duration;
	}
	
	public ArrayList<Integer> getCheckpoints() {
		return checkpoints;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	public void setVibrationStrength(int vibration_strength){
		this.vibration_strength = vibration_strength;
	}
	
	public void setPoints(ArrayList<Integer> checkpoints) {
		this.checkpoints = checkpoints;
	}
	
	
	public void addCheckpoint(int checkpoint){
		this.checkpoints.add(checkpoint);
	}
	
	public void prepareCheckpoints(){
		Collections.sort(this.checkpoints);
		int n_checkpoints = this.checkpoints.size();
		int n_iter = 0;
		int indx = 0;
		int checkpoint;
		
		//1. Sort array
		while(n_iter < n_checkpoints){
			checkpoint = this.checkpoints.get(indx);
			if(checkpoint>this.duration){
				this.checkpoints.remove(indx);
			}
			else{
				indx++;
			}
			
			n_iter++;
		}
		
		//2. Remove duplicates
		int current;
		int compared;
		boolean repeated;
		int i = 0;
		
		while(i!=(this.checkpoints.size()-1)){
			current = this.checkpoints.get(i);
			repeated = false;
			for(int j=(i+1); (j<this.checkpoints.size())&&(!repeated); j++){
				compared = this.checkpoints.get(j);
				if(compared==current){
					repeated = true;
				}
			}
			
			if(repeated){
				this.checkpoints.remove(i);
			}
			else{
				i++;
			}
		}
		
	}
}
