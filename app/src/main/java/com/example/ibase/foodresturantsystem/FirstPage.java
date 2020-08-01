package com.example.ibase.foodresturantsystem;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class FirstPage extends AppCompatActivity {
    Custiom_Adapter1 adapter;
    ViewPager viewPager;
    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        btn1=(Button)findViewById(R.id.btn1);
btn2=(Button)findViewById(R.id.btn2);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new Custiom_Adapter1(this);
        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(), 2000, 4000);


btn1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i1=new Intent(FirstPage.this,Foodsearch.class);
        startActivity(i1);
    }
});

btn2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i1=new Intent(FirstPage.this,MainActivity.class);
        startActivity(i1);
    }
});

    }




    public class MyTimeTask extends TimerTask {

        @Override
        public void run() {
            FirstPage.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);

                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });
        }
    }


}