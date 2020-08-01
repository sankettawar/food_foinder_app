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

public class LevelAdaptersearchhotlname extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] cost;
	private final String[] itemname;
	//private final String[] hotle_name;
	CardView cardView;
	public LevelAdaptersearchhotlname(Activity context, String[] cost, String[] itemname) {
		super(context, R.layout.levellisthotelname, cost);
		// TODO Auto-generated constructor stub

		this.context=context;
		this.cost=cost;
		this.itemname=itemname;
		//this.hotle_name=hotelname;
	}
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.levellisthotelname, null,true);

		TextView txtTitle = (TextView) rowView.findViewById(R.id.person_name);
		TextView extratxt = (TextView) rowView.findViewById(R.id.person_status);
		//TextView txthotelname = (TextView) rowView.findViewById(R.id.hotelname);
// add a click handler to ensure the CardView handles touch events
// otherwise the animation won't work


		txtTitle.setText(cost[position]);
		extratxt.setText(itemname[position]);
		//txthotelname.setText(hotle_name[position]);
		return rowView;

	}

}
