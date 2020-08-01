package com.example.ibase.foodresturantsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class HotelFoodsearch extends AppCompatActivity {
    EditText e1;
    Button b1;
    String cost[];
    String menuitem[];
    String menudesc[];
    String menucat[];
    String hotelname[];
    String offer[];
    String hotelid[];
    String menuid[];
    ListView lst;
    TextView txt;
    String number[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotelfoodsearch);
Bundle bundle=getIntent().getExtras();
final String puid=bundle.getString("uid");
final String hotelnames=bundle.getString("hotelname");
        e1=(EditText)findViewById(R.id.ed1);
        b1=(Button)findViewById(R.id.submit);
        lst=(ListView)findViewById(R.id.list);
        txt=(TextView)findViewById(R.id.textView3);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(HotelFoodsearch.this,MainActivity.class);
                startActivity(i1);
            }
        });



        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HotelFoodsearch.this);

                // Setting Dialog Title
                alertDialog.setTitle("If you Would Like To Order Here..");

                // Setting Dialog Message
                alertDialog.setMessage("If You Want Order?");

                // Setting Icon to Dialog
                alertDialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,int which) {

Intent i1=new Intent(HotelFoodsearch.this,PlaceOrder.class);
i1.putExtra("menutitle",menuitem[i]);
i1.putExtra("hotelid",hotelid[i]);
i1.putExtra("menuid",menuid[i]);
i1.putExtra("offer",offer[i]);
i1.putExtra("menudesc",menudesc[i]);
i1.putExtra("menucat",menucat[i]);
i1.putExtra("hotename",hotelnames);
i1.putExtra("number",number[i]);
                                i1.putExtra("cost",cost[i]);
startActivity(i1);


                                Toast.makeText(HotelFoodsearch.this, "Order Food here", Toast.LENGTH_SHORT).show();
                            }
                        });
                //	Intent i1=new Intent(Booking.this,BookingPaid.class);
                //startActivity(i1);

                alertDialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Write your code here to execute after dialog
                                Toast.makeText(HotelFoodsearch.this, "Cancel", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                // Showing Alert Message
                alertDialog.show();

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_text;
                get_text=e1.getText().toString().trim();

                try {
                    UserLogin logs=new UserLogin();
                    DbParameter host=new DbParameter();
                    String url=host.getHostpath();
                    url=url+"/SearchFoodByHotelName.php?hotelid="+URLEncoder.encode(puid)+"&";
                            url=url+"keyword="+ URLEncoder.encode(get_text);
                    logs.execute(url);

                }catch (Exception e){
                    Toast.makeText(HotelFoodsearch.this,""+e,Toast.LENGTH_SHORT);
                }
            }
        });



    }

    private class UserLogin extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        int count=0;
        @Override
        protected String doInBackground(String... geturl) {


            try{
                //	String url= ;
                HttpClient http=new DefaultHttpClient();
                HttpPost http_get= new HttpPost(geturl[0]);
                HttpResponse response=http.execute(http_get);
                HttpEntity http_entity=response.getEntity();
                BufferedReader br= new BufferedReader(new InputStreamReader(http_entity.getContent()));
                out = br.readLine();

            }catch (Exception e){

                out= e.toString();
            }
            return out;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(HotelFoodsearch.this, null,"Loding...");

            super.onPreExecute();
        }





        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
         //Toast.makeText(Foodsearch.this, " "+out, Toast.LENGTH_SHORT).show();
            try{
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("menu_info");
                int arraylength=jsonMainNode.length();
                cost =new String[arraylength];
                menuitem =new String[arraylength];
                menudesc =new String[arraylength];
                menucat =new String[arraylength];
                hotelname =new String[arraylength];
                offer =new String[arraylength];
                hotelid=new String[arraylength];
                menuid=new String[arraylength];
                number=new String[arraylength];

                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    cost[i]=jsonChildNode.optString("MenuCost");
                    menuitem[i]=jsonChildNode.optString("MenuTitle");
                    menudesc[i]=jsonChildNode.optString("MenuDescription");
                    menucat[i]=jsonChildNode.optString("Category");
                    offer[i]=jsonChildNode.optString("Offers");
                    hotelname[i]=jsonChildNode.optString("HotelName");
hotelid[i]=jsonChildNode.optString("Uid");
menuid[i]=jsonChildNode.optString("MenuId");
number[i]=jsonChildNode.optString("ContactNumber");
                    // Toast.makeText(MyNetwork.this, ""+ itemname[i], 500).show();
                    // employeeList.add(createEmployee("employees", outPut));
                    //totaljoining= totaljoining+1;

                }
                //tjoining.setText("Total Joining :"+totaljoining);
            }catch(Exception e){
                Toast.makeText(HotelFoodsearch.this," Json"+e, Toast.LENGTH_SHORT).show();
            }

           try{

                LevelAdaptersearchhotlname adapter=new LevelAdaptersearchhotlname(HotelFoodsearch.this,cost, menuitem);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(HotelFoodsearch.this,"No records Found"+e, Toast.LENGTH_SHORT).show();
            }

            progress.dismiss();
        }



    }
}
