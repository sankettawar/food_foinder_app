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

public class UpdateMenu extends AppCompatActivity {
    ListView lst;
    String itemname[];
    String desc[];
    String hotelid;
    String hotel_id[];
    String menuid[];
    String category[];
    String offer[];
    String cost[];
    SharedPreferences shr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        lst=(ListView)findViewById(R.id.updatelist);
        shr= PreferenceManager.getDefaultSharedPreferences(UpdateMenu.this);

        hotelid=shr.getString("userid", "");
        //Toast.makeText(UpdateMenu.this, " "+hotelid, Toast.LENGTH_SHORT).show();
        try {
          //  Toast.makeText(UpdateMenu.this, ""+hotelid, Toast.LENGTH_LONG).show();
            GetMenu opd= new GetMenu();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"/GetMenu.php?hotelid="+hotelid;
            //url=url+"/GetMenu.php?hotelid=";
            opd.execute(url);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(UpdateMenu.this, ""+e, Toast.LENGTH_SHORT).show();
        }

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateMenu.this);

                // Setting Dialog Title
                alertDialog.setTitle("Update The Menu Item...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure Want To Update?");

                // Setting Icon to Dialog
                alertDialog.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,int which) {

                                Intent i1=new Intent(UpdateMenu.this,UpdateMenuHere.class);
                              i1.putExtra("menttitle",itemname[i]);
                              i1.putExtra("cost",cost[i]);
                              i1.putExtra("desc",desc[i]);
                              i1.putExtra("offer",offer[i]);
                              i1.putExtra("menuid",menuid[i]);

                                startActivity(i1);
                                Toast.makeText(UpdateMenu.this, "Update here", Toast.LENGTH_SHORT).show();
                            }
                        });
                //	Intent i1=new Intent(Booking.this,BookingPaid.class);
                //startActivity(i1);

                alertDialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Write your code here to execute after dialog
                                Toast.makeText(UpdateMenu.this, "Not Update Till", Toast.LENGTH_SHORT).show();
                               finish();
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
            progress = ProgressDialog.show(UpdateMenu.this, null,
                    "Generating Menu...");

            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Toast.makeText(UpdateMenu.this, " "+out, Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("hotel_info");
                int arraylength = jsonMainNode.length();
                itemname = new String[arraylength];

                menuid = new String[arraylength];
                hotel_id = new String[arraylength];
                category = new String[arraylength];
                itemname = new String[arraylength];
                desc = new String[arraylength];
                cost = new String[arraylength];
                offer=new String[arraylength];
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                    itemname[i] = jsonChildNode.optString("MenuTitle");
                    menuid[i] = jsonChildNode.optString("MenuId");
                    hotel_id[i] = jsonChildNode.optString("Hoteld");
                    category[i] = jsonChildNode.optString("Category");

                    desc[i] = jsonChildNode.optString("MenuDescription");
                    cost[i] = jsonChildNode.optString("MenuCost");
                    offer[i]=jsonChildNode.optString("Offers");
                    // Toast.makeText(MyNetwork.this, ""+ itemname[i], 500).show();
                    // employeeList.add(createEmployee("employees", outPut));
                    //totaljoining= totaljoining+1;

                }
                //tjoining.setText("Total Joining :"+totaljoining);
            } catch (Exception e) {
                Toast.makeText(UpdateMenu.this, "No records Found" + e, Toast.LENGTH_SHORT).show();
            }


            try{

                LevelAdapter adapter=new LevelAdapter(UpdateMenu.this,itemname,cost);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(UpdateMenu.this,"No records Found"+e, Toast.LENGTH_SHORT).show();
            }



            progress.dismiss();

        }
    }
}
