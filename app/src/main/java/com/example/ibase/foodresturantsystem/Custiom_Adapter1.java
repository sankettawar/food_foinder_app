package com.example.ibase.foodresturantsystem;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Shaheen on 05/02/2018.
 */

public class Custiom_Adapter1 extends PagerAdapter{
private int[] imgs={R.drawable.kk,R.drawable.aapa,R.drawable.drink,R.drawable.soap};
private LayoutInflater inflater;
private Context ctx;

public  Custiom_Adapter1(Context ctx){

    this.ctx=ctx;
}
    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view ==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.swipe1,container,false);
        ImageView imageView=(ImageView)v.findViewById(R.id.image_view);
       // TextView tv=(TextView)v.findViewById(R.id.text_view);
        imageView.setImageResource(imgs[position]);
//tv.setText("Images"+position);
container.addView(v);
return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.invalidate();
    }
}


