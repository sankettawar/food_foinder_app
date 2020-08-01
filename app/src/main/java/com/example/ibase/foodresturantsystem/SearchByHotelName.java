package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
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

public class SearchByHotelName extends AppCompatActivity {
ListView lst;
String hotelname[];
String uid[];
String area[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_hotel_name);
        lst=(ListView)findViewById(R.id.list);

        try {
           SearchHotelName logs=new SearchHotelName();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"/GetHotelsName.php";
            logs.execute(url);

        }catch (Exception e){
            Toast.makeText(SearchByHotelName.this,""+e,Toast.LENGTH_SHORT);
        }

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1=new Intent(SearchByHotelName.this,HotelFoodsearch.class);
                i1.putExtra("uid",uid[i]);
                i1.putExtra("hotelname",hotelname[i]);
                startActivity(i1);
            }
        });
    }

    private class SearchHotelName extends AsyncTask<String, Integer, String> {
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
            progress = ProgressDialog.show(SearchByHotelName.this, null,"Loading...");

            super.onPreExecute();
        }





        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
       //  Toast.makeText(SearchByHotelName.this, " "+out, Toast.LENGTH_SHORT).show();
            try{
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();
                uid =new String[arraylength];
                hotelname =new String[arraylength];
                area =new String[arraylength];


                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                  uid[i]=jsonChildNode.optString("Uid");
                   hotelname[i]=jsonChildNode.optString("HotelName");
                  area[i]=jsonChildNode.optString("Area");

                    // Toast.makeText(MyNetwork.this, ""+ itemname[i], 500).show();
                    // employeeList.add(createEmployee("employees", outPut));
                    //totaljoining= totaljoining+1;

                }
                //tjoining.setText("Total Joining :"+totaljoining);
            }catch(Exception e){
               // Toast.makeText(SearchByHotelName.this," Json"+e, Toast.LENGTH_SHORT).show();
            }

       try{

                LevelAdapterhotelname adapter=new LevelAdapterhotelname(SearchByHotelName.this, hotelname,area);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(SearchByHotelName.this,"No records Found"+e, Toast.LENGTH_SHORT).show();
            }

            progress.dismiss();
        }



    }
}
