package com.example.ibase.foodresturantsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class ViewOwner extends AppCompatActivity {
String hotelname[];
String name[];
String number[];
String area[];
String uid[];
String emailid[];
ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_owner);
lst=(ListView)findViewById(R.id.list);
        try {

           // Toast.makeText(ViewOwner.this, ""+hotelid, Toast.LENGTH_LONG).show();
   ViewOwnerData opd= new ViewOwnerData();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"/ViewOwner.php";
            //url=url+"/GetMenu.php?hotelid=";
            opd.execute(url);
        }catch (Exception e){
            Toast.makeText(ViewOwner.this,""+e,Toast.LENGTH_SHORT).show();
        }


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final String get_uid=uid[i];
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewOwner.this);

                // Setting Dialog Title
                alertDialog.setTitle("Would You Like To Update And Delete...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure want Update?");

                // Setting Icon to Dialog
                alertDialog.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,int which) {


                                Intent i1=new Intent(ViewOwner.this,UpdateOwner.class);
i1.putExtra("uid",uid[i]);
i1.putExtra("name",name[i]);
i1.putExtra("hotelname",hotelname[i]);
i1.putExtra("number",number[i]);
i1.putExtra("emailid",emailid[i]);
i1.putExtra("area",area[i]);
                                startActivity(i1);
                                Toast.makeText(ViewOwner.this, "Update Here", Toast.LENGTH_SHORT).show();
                            }
                        });
                //	Intent i1=new Intent(Booking.this,BookingPaid.class);
                //startActivity(i1);

                alertDialog.setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                try {
                              DeleteOwnaerData opd= new  DeleteOwnaerData();
                                    DbParameter host=new DbParameter();
                                    String url=host.getHostpath();
                                    url=url+"/DeleteOwner.php?uid="+get_uid;
                                    //url=url+"/GetMenu.php?hotelid=";
                                    opd.execute(url);

                                }catch (Exception e){
                                    Toast.makeText(ViewOwner.this,""+e,Toast.LENGTH_SHORT).show();
                                }
                                // Write your code here to execute after dialog
                               // Toast.makeText(ViewOwner.this, "Delete", Toast.LENGTH_SHORT).show();
                               finish();
                            }
                        });

                // Showing Alert Message
                alertDialog.show();
            }
        });


    }
    private class ViewOwnerData extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        @Override
        protected String doInBackground(String... geturl) {

            try{

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
            progress = ProgressDialog.show(ViewOwner.this, null,
                    "Generating Menu...");

            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
         // Toast.makeText(ViewOwner.this, " "+out, Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength = jsonMainNode.length();
             hotelname = new String[arraylength];
number=new String[arraylength];
               uid = new String[arraylength];
                area = new String[arraylength];
               name = new String[arraylength];
emailid=new String[arraylength];
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
uid[i]=jsonChildNode.optString("Uid");
                    hotelname[i] = jsonChildNode.optString("HotelName");
                    name[i] = jsonChildNode.optString("Name");
                    area[i] = jsonChildNode.optString("Area");
                   number[i] = jsonChildNode.optString("ContactNumber");
emailid[i]=jsonChildNode.optString("Email");

                    // Toast.makeText(MyNetwork.this, ""+ itemname[i], 500).show();
                    // employeeList.add(createEmployee("employees", outPut));
                    //totaljoining= totaljoining+1;

                }
                //tjoining.setText("Total Joining :"+totaljoining);
            } catch (Exception e) {
                Toast.makeText(ViewOwner.this, "No records Found" + e, Toast.LENGTH_SHORT).show();
            }


           try{

                LevelAdapterowner adapter=new LevelAdapterowner(ViewOwner.this,name,hotelname,area);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(ViewOwner.this,"No records Found"+e, Toast.LENGTH_SHORT).show();
            }



            progress.dismiss();

        }
    }

    private class  DeleteOwnaerData extends AsyncTask<String, Integer, String>  {
        private ProgressDialog progress = null;
        String out="";
        @Override
        protected String doInBackground(String... geturl) {

            try{

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
            progress = ProgressDialog.show(ViewOwner.this, null,
                    "Processing...");

            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(ViewOwner.this, " Delete Succsessfully", Toast.LENGTH_SHORT).show();




            //*******************-compose list*****************************


            progress.dismiss();

        }


    }


}
