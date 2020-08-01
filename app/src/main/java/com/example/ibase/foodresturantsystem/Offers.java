package com.example.ibase.foodresturantsystem;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileReader;

public class Offers extends Fragment {
Button btn1,btn2;
    TextView txt;
View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       return v=inflater.inflate(R.layout.offers,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txt=(TextView)v.findViewById(R.id.textView3);
        btn1=(Button)v.findViewById(R.id.button);
        btn2=(Button)v.findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getActivity(),ViewOffers.class);
                startActivity(i1);
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getActivity(),MainActivity.class);
                startActivity(i1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getActivity(),DeleteOffers.class);
                startActivity(i1);
            }
        });
    }
}