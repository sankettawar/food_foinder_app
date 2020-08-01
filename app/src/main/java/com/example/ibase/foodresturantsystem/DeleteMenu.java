package com.example.ibase.foodresturantsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class DeleteMenu extends AppCompatActivity {
    ListView lst;
    String itemname[];
 String menuid[];
    String offer[];
    String hotel_id[];
    String cost[];
    String  hotelid;
    SharedPreferences shr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_menu);
        shr= PreferenceManager.getDefaultSharedPreferences(DeleteMenu.this);
        lst=(ListView)findViewById(R.id.updatelist);

        hotelid=shr.getString("userid", "");
        try {
           // Toast.makeText(UpdateMenu.this, ""+hotelid, Toast.LENGTH_LONG).show();
          GetMenu opd= new GetMenu();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"/GetMenu.php?hotelid="+hotelid;
            //url=url+"/GetMenu.php?hotelid=";
            opd.execute(url);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(DeleteMenu.this, ""+e, Toast.LENGTH_SHORT).show();
        }



        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String menu_id=menuid[i];
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeleteMenu.this);

                // Setting Dialog Title
                alertDialog.setTitle("Delete Menu Here...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure Want To Delete Menu?");

                // Setting Icon to Dialog
                alertDialog.setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,int which) {

                                try {
                                    //Toast.makeText(DeleteMenu.this,""+menu_id, Toast.LENGTH_SHORT).show();
                                    GetMenu1 opd= new GetMenu1();
                                    DbParameter host=new DbParameter();
                                    String url=host.getHostpath();
                                    url=url+"/DeleteMenu.php?menuid="+menu_id;
                                    //url=url+"/GetMenu.php?hotelid=";
                                    opd.execute(url);

                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    Toast.makeText(DeleteMenu.this, ""+e, Toast.LENGTH_SHORT).show();
                                }

                                  Toast.makeText(DeleteMenu.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                //	Intent i1=new Intent(Booking.this,BookingPaid.class);
                //startActivity(i1);

                alertDialog.setNegativeButton("No Thanks",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Write your code here to execute after dialog

                                dialog.cancel();
                                Toast.makeText(DeleteMenu.this, "No Thanks", Toast.LENGTH_SHORT).show();


                                //getActivity().finish();
                            }
                        });

                // Showing Alert Message
                alertDialog.show();
            }
        });
    }

    private class GetMenu extends AsyncTask<String, Integer, String> {
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
            progress = ProgressDialog.show(DeleteMenu.this, null,
                    "Generating Menu...");

            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
        //    Toast.makeText(DeleteMenu.this, " "+out, Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("hotel_info");
                int arraylength = jsonMainNode.length();
                itemname = new String[arraylength];

                menuid = new String[arraylength];

                itemname = new String[arraylength];

                cost = new String[arraylength];
                offer=new String[arraylength];
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                    itemname[i] = jsonChildNode.optString("MenuTitle");
                    menuid[i] = jsonChildNode.optString("MenuId");

                    cost[i] = jsonChildNode.optString("MenuCost");
                    offer[i]=jsonChildNode.optString("Offers");
                    // Toast.makeText(MyNetwork.this, ""+ itemname[i], 500).show();
                    // employeeList.add(createEmployee("employees", outPut));
                    //totaljoining= totaljoining+1;

                }
                //tjoining.setText("Total Joining :"+totaljoining);
            } catch (Exception e) {
                Toast.makeText(DeleteMenu.this, "No records Found" + e, Toast.LENGTH_SHORT).show();
            }


            try{

                LevelAdapter adapter=new LevelAdapter(DeleteMenu.this,itemname,cost);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(DeleteMenu.this,"No records Found"+e, Toast.LENGTH_SHORT).show();
            }



            progress.dismiss();

        }
    }

    private class GetMenu1 extends AsyncTask<String, Integer, String>  {
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
            progress = ProgressDialog.show(DeleteMenu.this, null,
                    "Generating Menu...");

            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(DeleteMenu.this, " "+out, Toast.LENGTH_SHORT).show();




            //*******************-compose list*****************************


            progress.dismiss();

        }


    }

}
