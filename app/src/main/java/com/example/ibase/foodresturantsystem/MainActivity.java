package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class MainActivity extends AppCompatActivity {
EditText uname,pass;
Button btn,skip;
TextView reg,forget;
SharedPreferences shr;
    Custiom_Adapter4 adapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new Custiom_Adapter4(this);
        viewPager.setAdapter(adapter);
shr= PreferenceManager.getDefaultSharedPreferences(this);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(), 2000, 4000);
        uname = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        btn=(Button)findViewById(R.id.submit);
        skip=(Button)findViewById(R.id.button4);
        reg=(TextView)findViewById(R.id.button5);
forget=(TextView)findViewById(R.id.button7);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity.this,CutomerRegister.class);
                startActivity(i1);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity.this,ForgetPassword.class);
                startActivity(i1);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity.this,Foodsearch.class);
                startActivity(i1);
            }
        });
    }



        public class MyTimeTask extends TimerTask {

            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(2);
                        } else if (viewPager.getCurrentItem() == 2) {
                            viewPager.setCurrentItem(3);

                        }else if (viewPager.getCurrentItem() == 3) {
                        viewPager.setCurrentItem(4);

                    }
                        else {
                            viewPager.setCurrentItem(0);
                        }

                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String get_username,get_password;
Boolean status=true;
                        get_username=uname.getText().toString().trim();
                        get_password=pass.getText().toString().trim();


                        if(get_username.contentEquals("")){

                       uname.setError("Enter Username");
                           uname.requestFocus();
                            status=false;
                        }

                        if(get_password.contentEquals("")){

                            pass.setError("Enter Password");
                            pass.requestFocus();
                            status=false;
                        }

                        if(status)
                        {
try {
    UserLogin logs=new UserLogin();
    DbParameter host=new DbParameter();
    String url=host.getHostpath();
    url=url+"/UserLogin.php?uname="+ URLEncoder.encode(get_username)+"&";
    url=url+"pass="+URLEncoder.encode(get_password);
    logs.execute(url);
    Log.d("login url",url);
}catch (Exception e){
    Toast.makeText(MainActivity.this,"Please Entered Correct EmailId and Password",Toast.LENGTH_SHORT).show();

}
                            if(get_username.contentEquals("admin") && get_password.contentEquals("admin")){
                                Intent i1=new Intent(MainActivity.this,AdminDashboard.class);
                                startActivity(i1);
                            }




                        }
                    }
                });
            }
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
            progress = ProgressDialog.show(MainActivity.this, null,
                    "Login...");

            super.onPreExecute();
        }





        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();

                JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);

                //itemname[i]=jsonChildNode.optString("TransactionDate");
                //itemname[i]="12FEb";
                String name=jsonChildNode.optString("Name");
                String role=jsonChildNode.optString("Role");
                String uid=jsonChildNode.optString("Uid");
                String hotelname=jsonChildNode.optString("HotelName");
                String hotelid=jsonChildNode.optString("HotelId");

                SharedPreferences.Editor edit=shr.edit();
                edit.putString("name", name);
                edit.putString("userid",uid);
               // edit.putString("Uid",password);
                edit.putString("hotelname",hotelname);
         //       edit.putString("hotelid",hotelid);
                edit.commit();

                //Toast.makeText(MainActivity.this, "Name"+ name, 500).show();
                if(role.contentEquals("Owner")){
                    Intent i1= new Intent(MainActivity.this,AdminNavigation.class);
                    startActivity(i1);
                    Toast.makeText(MainActivity.this,"OwnerDashboard", Toast.LENGTH_SHORT).show();
                }
                if(role.contentEquals("Customer")){
                    Intent i1= new Intent(MainActivity.this,Foodsearch.class);
                    startActivity(i1);
                    Toast.makeText(MainActivity.this,"CustomerDashboard", Toast.LENGTH_SHORT).show();
                }


                // employeeList.add(createEmployee("employees", outPut));
                //count=count+1;

            }catch(Exception e){
                Toast.makeText(MainActivity.this, "Invalid Username And Password", Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();
        }


    }

}