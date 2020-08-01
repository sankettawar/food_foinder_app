package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class CutomerRegister extends AppCompatActivity {
    Button submit;
String get_contact,get_password,get_emailid;
    EditText name,contactnumber,emailid,password,address;
    SharedPreferences shr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_register);

        shr= PreferenceManager.getDefaultSharedPreferences(CutomerRegister.this);
        submit=(Button)findViewById(R.id.submit);
        name=(EditText)findViewById(R.id.name);
        contactnumber=(EditText)findViewById(R.id.contactnumber);
        emailid=(EditText)findViewById(R.id.emailid);
        password=(EditText)findViewById(R.id.password);
        address=(EditText)findViewById(R.id.address);

       // hotelname=(EditText)findViewById(R.id.hotelname);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status=true;

                String get_name,get_address,get_hotelname,get_role;

                get_name=name.getText().toString().trim();
                get_emailid=emailid.getText().toString().trim();
                get_password=password.getText().toString().trim();
                get_contact=contactnumber.getText().toString().trim();
                get_hotelname=" ";
                get_address=address.getText().toString().trim();
                get_role="Customer";
                String mobilepattern = "[0-9]{10}";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                String number=contactnumber.getText().toString();

                if(get_name.contentEquals("")){

                    name.setError("Enter Your Name");
                    name.requestFocus();
                    status=false;

                }


                if(get_address.contentEquals("")){

                    address.setError("Enter Your Address");
                    address.requestFocus();
                    status=false;

                }

                if(contactnumber.getText().toString().length()>10 || number.matches(mobilepattern)==false) {


                    contactnumber.setError("Enter Valid Contact Number");
                    contactnumber.requestFocus();
                    status=false;
                }
String emailid1="[a-zA-Z0-9._-]+@[a-z]+.[a-z]";
                if (get_emailid.matches(emailid1)) {

                    emailid.setError("Invalid Email Address");
                    emailid.requestFocus();
                    status=false;
                }
                if(get_password.contentEquals("")){

                    password.setError("Enter Password");
                    password.requestFocus();
                    status=false;
                }


                if(status) {


                    try {
                     RegisterUser gettrans = new RegisterUser();
                        DbParameter host = new DbParameter();
                        String url = host.getHostpath();
                        url = url + "/UserRegistration.php?name=" + URLEncoder.encode(get_name) + "&";
                        url = url + "contact_number=" + URLEncoder.encode(get_contact) + "&";
                        url = url + "email=" + URLEncoder.encode(get_emailid) + "&";
                        url = url + "pass=" + URLEncoder.encode(get_password) + "&";
                        url = url + "role=" + URLEncoder.encode(get_role) + "&";
                        url = url + "area=" + URLEncoder.encode(get_address) + "&";
                        url = url + "hotelname="+URLEncoder.encode(get_hotelname)+"&";
                        Log.d("url :",url);
                        Log.d("Error1",url.toString());
                        gettrans.execute(url);
                    } catch (Exception e) {
                        Toast.makeText(CutomerRegister.this, "" + e, Toast.LENGTH_SHORT).show();
                        Log.d("Error",e.toString());
                    }
                }
            }
        });
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
            progress = ProgressDialog.show(CutomerRegister.this, null,
                    "Registering...");

           super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //   Toast.makeText(RegistraionAdmin.this, " "+out, Toast.LENGTH_SHORT).show();
            //Toast.makeText(SignUp.this, "Output"+out, 500).show();

String sms="Your Registration Successfull "+" Your Emailid is "+get_emailid+" and Your Password is "+get_password;
            SmsManager smsmanager=SmsManager.getDefault();

            smsmanager.sendTextMessage(get_contact,null,sms, null, null);
            Toast.makeText(CutomerRegister.this, "Sms sent On Your Register Mobile Number", Toast.LENGTH_SHORT).show();
            if(out.contains("1")){
                Toast.makeText(CutomerRegister.this, "You are registerd Sucessfully ", Toast.LENGTH_SHORT).show();
                name.setText("");
                contactnumber.setText("");
                emailid.setText("");
                password.setText("");

                address.setText("");
                finish();
            }

            progress.dismiss();
            super.onPostExecute(result);
        }


    }
}
