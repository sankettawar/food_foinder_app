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

public class Menu extends Fragment {
View v;
TextView txt;
    Button btn,updatebtn,deletemenu;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    return  v=inflater.inflate(R.layout.menu,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btn=(Button)v.findViewById(R.id.button);
txt=(TextView)v.findViewById(R.id.textView3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getActivity(),Addmenu.class);
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
        updatebtn=(Button)v.findViewById(R.id.button2);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getActivity(),UpdateMenu.class);
                startActivity(i1);
            }
        });

        deletemenu=(Button)v.findViewById(R.id.button3);
        deletemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getActivity(),DeleteMenu.class);
                startActivity(i1);
            }
        });
    }
}