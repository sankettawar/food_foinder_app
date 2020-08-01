package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgetPassword extends AppCompatActivity {
EditText emailid;
String pass[];
String number[];
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailid = (EditText) findViewById(R.id.email);

        btn = (Button) findViewById(R.id.submit);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_emailid;

                get_emailid = emailid.getText().toString();

                try {

                    ForgetData opd = new ForgetData();
                    DbParameter host = new DbParameter();
                    String url = host.getHostpath();
                    url = url + "/ForgetPassword.php?uname="+ URLEncoder.encode(get_emailid);
                    //url=url+"/GetMenu.php?hotelid=";
                    opd.execute(url);

                } catch (Exception e) {
                    Toast.makeText(ForgetPassword.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
        private class  ForgetData extends AsyncTask<String, Integer, String> {
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
                progress = ProgressDialog.show(ForgetPassword.this, null,
                        "Processing...");

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
          //  Toast.makeText(ForgetPassword.this, " "+out, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonResponse = new JSONObject(out);
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                    int arraylength = jsonMainNode.length();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);
                    String password=jsonChildNode.optString("Password");
String cnumber=jsonChildNode.optString("ContactNumber");
String mess="Your Password is "+password;
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(cnumber,null,mess,null,null);
                    Toast.makeText(ForgetPassword.this, "Sms sent On Your Register Mobile Number", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonMainNode.length(); i++) {



                        // Toast.makeText(MyNetwork.this, ""+ itemname[i], 500).show();
                        // employeeList.add(createEmployee("employees", outPut));
                        //totaljoining= totaljoining+1;

                    }
                    //tjoining.setText("Total Joining :"+totaljoining);
                } catch (Exception e) {
                    Toast.makeText(ForgetPassword.this, "No records Found" + e, Toast.LENGTH_SHORT).show();
                }





                progress.dismiss();

            }
        }
    }

