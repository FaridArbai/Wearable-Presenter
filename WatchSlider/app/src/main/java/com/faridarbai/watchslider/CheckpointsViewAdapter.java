package com.faridarbai.watchsliderbeta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CheckpointsViewAdapter  extends RecyclerView.Adapter<CheckpointsViewAdapter.ViewHolder>{
	private static final String TAG = "CheckpointsViewAdapter";
	
	/**
	 * Lista con los valores de cada campo
	 */
	private ArrayList<Integer> checkpoints;
	
	/**
	 * Contexto extraido del activity
	 */
	private Context context;
	
	/**
	 * Constructor por defecto
	 * @param checkpoints
	 * @param duration
	 * @param context
	 */
	public CheckpointsViewAdapter(ArrayList<Integer> checkpoints, int duration, Context context) {
		this.checkpoints = checkpoints;
		this.context = context;
	}
	
	/**
	 * Añade un nuevo campo
	 * @param checkpoint
	 */
	public void add(int checkpoint){
		this.checkpoints.add(checkpoint);
	}
	
	
	/**
	 * Esta funcion se encarga de gestionar los elementos GUI de cada item (sus vistas)
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@NonNull
	@Override
	public CheckpointsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkpoint_item, parent, false);
		CheckpointsViewAdapter.ViewHolder holder = new CheckpointsViewAdapter.ViewHolder(view);
		
		return holder;
	}
	
	/**
	 * Esta es la función llamada cuando la vista del item actual se añade, por lo
	 * tanto es donde se realiza la conexión entre las variables y los elementos GUI
	 * @param holder
	 * @param position
	 */
	@Override
	public void onBindViewHolder(@NonNull CheckpointsViewAdapter.ViewHolder holder, int position) {
		final int checkpoint = this.checkpoints.get(position);
		final EditText checkpoint_value = holder.checkpoint_value;
		final int index = position;
		
		holder.checkpoint_number.setText(String.format("                • Point #%d: ",(position+1)));
		checkpoint_value.setText(String.format("%d",checkpoint));
		
		checkpoint_value.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				try{
					int new_value = Integer.parseInt(checkpoint_value.getText().toString());
					CheckpointsViewAdapter.this.checkpoints.set(index, new_value);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Devuelve el numero de elementos internos
	 * @return
	 */
	@Override
	public int getItemCount() {
		
		return this.checkpoints.size();
	}
	
	
	/**
	 * Clase interna para representar la vista de cada item
	 */
	public class ViewHolder extends RecyclerView.ViewHolder{
		TextView checkpoint_number;
		EditText checkpoint_value;
		
		public ViewHolder(View itemView) {
			super(itemView);
			
			this.checkpoint_number = (TextView) itemView.findViewById(R.id.checkpoint_number);
			this.checkpoint_value = (EditText) itemView.findViewById(R.id.checkpoint_value);
		}
	}
	
}
