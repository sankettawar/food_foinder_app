package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class PlaceOrder extends AppCompatActivity {
String b_menuid,b_hotelid,b_itemname,b_offer,b_desc,b_cat,b_cost,b_hotelname,b_number;
TextView itemname,offer,desc,cat,cost,hotelname;
EditText address,quan;
String uname,pass;
Button btn;
SharedPreferences shr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Bundle bundle=getIntent().getExtras();
        shr= PreferenceManager.getDefaultSharedPreferences(this);
hotelname=(TextView)findViewById(R.id.hotelname);
        uname=shr.getString("Uid","uid");
        pass=shr.getString("name","name");
                btn=(Button)findViewById(R.id.submit);

        b_menuid=bundle.getString("menuid");
        b_hotelid=bundle.getString("hotelid");
       b_itemname=bundle.getString("menutitle");
       b_offer=bundle.getString("offer");
       b_desc=bundle.getString("menudesc");
       b_cat=bundle.getString("menucat");
        b_cost=bundle.getString("cost");
    b_number=bundle.getString("number");
                b_hotelname=bundle.getString("hotename");
quan=(EditText)findViewById(R.id.qua);
//address=(EditText)findViewById(R.id.address);

itemname=(TextView)findViewById(R.id.item_name);
      offer=(TextView)findViewById(R.id.offers);
   desc=(TextView)findViewById(R.id.des);
  cat=(TextView)findViewById(R.id.category);

cost=(TextView)findViewById(R.id.menu_cost);

itemname.setText(b_itemname);
offer.setText(b_offer);
desc.setText(b_desc);
cat.setText(b_cat);
cost.setText(b_cost);
        hotelname.setText(b_hotelname);

     //   Toast.makeText(PlaceOrder.this,""+b_number,Toast.LENGTH_SHORT);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_quantity, get_address, get_item_name, get_menucost, get_itemoffer, get_desc, get_cat;
                get_quantity = quan.getText().toString();
               // get_address = address.getText().toString();


                get_item_name = itemname.getText().toString().trim();
                get_menucost = cost.getText().toString().trim();
                get_itemoffer = offer.getText().toString().trim();
                get_desc = desc.getText().toString().trim();
                get_cat = cat.getText().toString().trim();

                //  get_itemtitle=itemname.getText().toString();
                Boolean status = true;
                if (get_quantity.contentEquals("")) {
                    quan.setError("please Enter Quantity");
                    quan.requestFocus();
                    status = false;
                }
               /* if (get_address.contentEquals("")) {
                    address.setError("Please Enter address");
                    address.requestFocus();
                    status = false;
                }*/
                if (status) {


                   Intent i1 = new Intent(PlaceOrder.this, Payamount.class);
                   i1.putExtra("quantity",get_quantity);
                   i1.putExtra("cost", get_menucost);
                   i1.putExtra("hotelid",b_hotelid);
                   i1.putExtra("menuid",b_menuid);
                 //   i1.putExtra("address",get_address);
                    i1.putExtra("menutitle",get_item_name);
                    i1.putExtra("number",b_number);
                    startActivity(i1);
                    finish();
                }
            }
            });
        }






}
