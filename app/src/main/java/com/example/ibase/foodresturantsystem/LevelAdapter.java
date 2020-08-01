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

public class LevelAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] cost;
	private final String[] itemname;
	CardView cardView;
	public LevelAdapter(Activity context, String[] cost,  String[] itemname) {
		super(context, R.layout.levellist, cost);
		// TODO Auto-generated constructor stub

		this.context=context;
		this.cost=cost;
		this.itemname=itemname;
	}
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.levellist, null,true);

		TextView txtTitle = (TextView) rowView.findViewById(R.id.person_name);
		TextView extratxt = (TextView) rowView.findViewById(R.id.person_status);

// add a click handler to ensure the CardView handles touch events
// otherwise the animation won't work


		txtTitle.setText(cost[position]);
		extratxt.setText(itemname[position]);
		return rowView;
		
	}

}
