package com.example.ibase.foodresturantsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminDashboard extends AppCompatActivity {
Button addowner,viewowner,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        logout=(Button)findViewById(R.id.btn3);
        addowner=(Button)findViewById(R.id.btn);
        viewowner=(Button)findViewById(R.id.btn1);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(AdminDashboard.this,MainActivity.class);
                startActivity(i1);
            }
        });
        addowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(AdminDashboard.this,RegistraionAdmin.class);
                startActivity(i1);
            }
        });

        viewowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(AdminDashboard.this,ViewOwner.class);
                startActivity(i1);
            }
        });
            }
}
