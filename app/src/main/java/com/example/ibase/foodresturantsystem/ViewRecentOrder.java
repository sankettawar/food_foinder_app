package com.example.ibase.foodresturantsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ViewRecentOrder extends AppCompatActivity {
ListView lst;
SharedPreferences shr;
String quantity[];
String add[];
String name[];
String total_amount[];
String orderid[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recent_order);
shr= PreferenceManager.getDefaultSharedPreferences(this);

String uid=shr.getString("userid","userid");
        lst=(ListView)findViewById(R.id.list);
//Toast.makeText(ViewRecentOrder.this,""+uid,Toast.LENGTH_SHORT).show();
        try {

       UserLogin logs=new UserLogin();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"/PendingOrders.php?hotelid="+ URLEncoder.encode(uid);
            logs.execute(url);
        }catch (Exception e){
            Toast.makeText(ViewRecentOrder.this,""+e,Toast.LENGTH_SHORT).show();
        }


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               final String orderuid=orderid[i];
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewRecentOrder.this);

                // Setting Dialog Title
                alertDialog.setTitle("Would You Like To Dispatch Order...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure want Despatch?");

                // Setting Icon to Dialog
                alertDialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,int which) {

                                try{
                                RegisterUser gettrans=new RegisterUser();
                                    DbParameter host=new DbParameter();
                                    String url=host.getHostpath();
                                    url=url+"/DispatchOrder.php?OrderId="+ URLEncoder.encode(orderuid)+"&";

                                    gettrans.execute(url);
                                }catch(Exception e){
                                    Toast.makeText(ViewRecentOrder.this, ""+e, Toast.LENGTH_SHORT).show();
                                }


                                Toast.makeText(ViewRecentOrder.this, "Yes Dispatch", Toast.LENGTH_SHORT).show();
                            }
                        });
                //	Intent i1=new Intent(Booking.this,BookingPaid.class);
                //startActivity(i1);

                alertDialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Write your code here to execute after dialog
                                Toast.makeText(ViewRecentOrder.this, "Not Dispatch", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                // Showing Alert Message
                alertDialog.show();

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
            progress = ProgressDialog.show(ViewRecentOrder.this, null,"Login...");

            super.onPreExecute();
        }





        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
        //    Toast.makeText(ViewRecentOrder.this, " "+out, Toast.LENGTH_SHORT).show();
            try{
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("menu_info");
                int arraylength=jsonMainNode.length();
             quantity  =new String[arraylength];
           total_amount=new String[arraylength];
            add=new String[arraylength];
        name=new String[arraylength];
                    orderid =new String[arraylength];


                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
               name[i]=jsonChildNode.optString("MenuTitle");
                   add[i]=jsonChildNode.optString("DelAddress");
                 quantity[i]=jsonChildNode.optString("Quantity");
                 total_amount[i]=jsonChildNode.optString("Totalamount");
                   orderid[i]=jsonChildNode.optString("OrderId");

                    // Toast.makeText(MyNetwork.this, ""+ itemname[i], 500).show();
                    // employeeList.add(createEmployee("employees", outPut));
                    //totaljoining= totaljoining+1;

                }
                //tjoining.setText("Total Joining :"+totaljoining);
            }catch(Exception e){
                Toast.makeText(ViewRecentOrder.this," Json"+e, Toast.LENGTH_SHORT).show();
            }

            try{

                LevelAdapterpending adapter=new LevelAdapterpending(ViewRecentOrder.this,name,quantity,total_amount,add);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(ViewRecentOrder.this,"No records Found"+e, Toast.LENGTH_SHORT).show();
            }

            progress.dismiss();
        }



    }

    public class RegisterUser extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
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
            progress = ProgressDialog.show(ViewRecentOrder.this, null,
                    "Processing...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //   Toast.makeText(RegistraionAdmin.this, " "+out, Toast.LENGTH_SHORT).show();

            if(out.contains("1")){
                Toast.makeText(ViewRecentOrder.this, "You are registerd Sucessfully", Toast.LENGTH_SHORT).show();

                finish();
            }
			/*  if(out.contains("2")){

				   Editor edit=shr.edit();
				   edit.putString("new_uname","");
				   edit.putString("pass_uname","");
				   edit.commit();
				   Toast.makeText(NewRegistration.this, "You are Registered Sucessfully", 500).show();
				//   Intent i1= new Intent(SignUp.this,AskRefralId.class);
				 //  startActivity(i1);
				   finish();
			   }*/
            progress.dismiss();
            super.onPostExecute(result);
        }


    }
}
