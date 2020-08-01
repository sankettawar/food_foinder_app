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

public class Orders extends Fragment {
    View v;
    TextView txt;
Button btn1,btn2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return v=inflater.inflate(R.layout.orders,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txt=(TextView)v.findViewById(R.id.textView3);
btn1=(Button)v.findViewById(R.id.button);

        btn2=(Button)v.findViewById(R.id.button2);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getActivity(),MainActivity.class);
                startActivity(i1);
            }
        });
btn1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i1=new Intent(getActivity(),ViewRecentOrder.class);
        startActivity(i1);
    }
});

btn2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i1=new Intent(getActivity(),DispatchOrder.class);
        startActivity(i1);
    }
});
    }
}

