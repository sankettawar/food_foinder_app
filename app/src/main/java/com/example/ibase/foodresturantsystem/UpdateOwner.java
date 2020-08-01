package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class UpdateOwner extends AppCompatActivity {
    Button submit;
    String b_name,b_hotelname,b_number,b_emailid,b_area,b_uid;

    EditText name,contactnumber,emailid,address,hotelname;
    SharedPreferences shr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_owner);

        shr= PreferenceManager.getDefaultSharedPreferences(UpdateOwner.this);

        submit=(Button)findViewById(R.id.submit);
        name=(EditText)findViewById(R.id.name);
        contactnumber=(EditText)findViewById(R.id.contactnumber);
        emailid=(EditText)findViewById(R.id.emailid);

        address=(EditText)findViewById(R.id.address);
        hotelname=(EditText)findViewById(R.id.hotelname);



        Bundle bundle=getIntent().getExtras();
        b_name=bundle.getString("name");
        b_hotelname=bundle.getString("hotelname");
        b_emailid=bundle.getString("emailid");
        b_area=bundle.getString("area");
        b_number=bundle.getString("number");

b_uid=bundle.getString("uid");




name.setText(b_name);
hotelname.setText(b_hotelname);
emailid.setText(b_emailid);
contactnumber.setText(b_number);
address.setText(b_area);


submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        boolean status = true;

        String get_name, get_emailid, get_address, get_hotelname, get_role, get_contact;

        get_name = name.getText().toString().trim();
        get_emailid = emailid.getText().toString().trim();

        get_contact = contactnumber.getText().toString().trim();
        get_hotelname = hotelname.getText().toString().trim();
        get_address = address.getText().toString().trim();
        get_role = "Owner";


        String mobilepattern = "[0-9]{10}";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String number = contactnumber.getText().toString();


        if (get_name.contentEquals("")) {

            name.setError("Enter Your Name");
            name.requestFocus();
            status = false;

        }

        if (hotelname.getText().toString().length() > 10 || number.matches(mobilepattern) == false) {


            hotelname.setError("Enter Hotel NameNumber");
            hotelname.requestFocus();
            status = false;
        }
        if (get_address.contentEquals("")) {

            address.setError("Enter Your Address");
            address.requestFocus();
            status = false;

        }

        if (contactnumber.getText().toString().length() > 10 || number.matches(mobilepattern) == false) {


            contactnumber.setError("Enter Valid Contact Number");
            contactnumber.requestFocus();
            status = false;
        }

        if (!get_emailid.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {

            emailid.setError("Invalid Email Address");
            emailid.requestFocus();
            status = false;
        }
        if (status)
            try {
                RegisterUser gettrans = new RegisterUser();
                DbParameter host = new DbParameter();
                String url = host.getHostpath();
                url = url + "/UpdateOwner.php?uid=" + URLEncoder.encode(b_uid) + "&";
                url = url + "name=" + URLEncoder.encode(get_name) + "&";
                url = url + "contact_number=" + URLEncoder.encode(get_contact) + "&";
                url = url + "email=" + URLEncoder.encode(get_emailid) + "&";


                url = url + "area=" + URLEncoder.encode(get_address) + "&";
                url = url + "hotelname=" + URLEncoder.encode(get_hotelname) + "&";
                gettrans.execute(url);

            } catch (Exception e) {
                Toast.makeText(UpdateOwner.this, "" + e, Toast.LENGTH_SHORT).show();
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
            progress = ProgressDialog.show(UpdateOwner.this, null,
                    "Processing...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //   Toast.makeText(RegistraionAdmin.this, " "+out, Toast.LENGTH_SHORT).show();
            //Toast.makeText(SignUp.this, "Output"+out, 500).show();

            if(out.contains("1")){
                Toast.makeText(UpdateOwner.this, "You are Update Sucessfully", Toast.LENGTH_SHORT).show();
                name.setText("");
                contactnumber.setText("");
                emailid.setText("");

                hotelname.setText("");
                address.setText("");
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
