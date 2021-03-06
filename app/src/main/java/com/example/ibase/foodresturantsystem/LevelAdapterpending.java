package com.example.ibase.foodresturantsystem;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LevelAdapterpending extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] cost;
	private final String[] itemname;
	private final String[]address;
	private final String[]qauntity;
	CardView cardView;
	public LevelAdapterpending(Activity context, String[] cost,  String[] itemname,String[] address,String[] qauntity) {
		super(context, R.layout.levellistpending, cost);
		// TODO Auto-generated constructor stub

		this.context=context;
		this.cost=cost;
		this.itemname=itemname;
		this.address=address;
		this.qauntity=qauntity;
	}
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.levellistpending, null,true);

		TextView txtTitle = (TextView) rowView.findViewById(R.id.person_name);
		TextView extratxt = (TextView) rowView.findViewById(R.id.person_status);
		TextView txtquantity = (TextView) rowView.findViewById(R.id.quantity);
		TextView extaddress = (TextView) rowView.findViewById(R.id.address);

// add a click handler to ensure the CardView handles touch events
// otherwise the animation won't work


		txtTitle.setText(cost[position]);
		extratxt.setText(itemname[position]);
		txtquantity.setText(qauntity[position]);
		extaddress.setText(address[position]);
		return rowView;
		
	}

}
