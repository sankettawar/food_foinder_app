package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class Payamount extends AppCompatActivity {
TextView total;
    String uname;
    EditText number;
     Button btn,pay,cart;
    SharedPreferences shr;
    String get_total;
    int c,q,t;
    String  uid;
    String quantity;
    String menu_title,address,hotelid,menu_id,b_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payamount);
        shr= PreferenceManager.getDefaultSharedPreferences(this);
btn=(Button)findViewById(R.id.submit);
number=(EditText)findViewById(R.id.number);
        cart=(Button)findViewById(R.id.button6);

      uid=shr.getString("userid","uid");
   uname=shr.getString("name","name");

        Bundle bundle=getIntent().getExtras();
total=(TextView)findViewById(R.id.total);
        final String quantity=bundle.getString("quantity");
        String cost=bundle.getString("cost");
        menu_title=bundle.getString("menutitle");
     //   address=bundle.getString("address");
        hotelid=bundle.getString("hotelid");
   //     hotelid=bundle.getString("hotelid");
       menu_id=bundle.getString("menuid");
b_number=bundle.getString("number");
      pay=(Button)findViewById(R.id.paytm);
//Toast.makeText(Payamount.this,""+b_number,Toast.LENGTH_SHORT).show();

c=Integer.parseInt(quantity);
q=Integer.parseInt(cost);
         t=c*q;
         /*
if (t>100){
    String total_amount=""+t;
    total.setText(total_amount);
}else{
    Toast.makeText(Payamount.this,"Please Choose greater than 100",Toast.LENGTH_SHORT).show();
    finish();
}
*/
       // Toast.makeText(Payamount.this,""+t,Toast.LENGTH_SHORT).show();

        try {
            String iscart="0";
            // Toast.makeText(PlaceOrder.this, , Toast.LENGTH_SHORT).show();
            RegisterUser gettrans=new RegisterUser();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"/PlaceOrder.php?menutitle="+ URLEncoder.encode(menu_title)+"&";
            //url=url+"address="+ URLEncoder.encode(address)+"&";
              url=url+"HotelId="+URLEncoder.encode(hotelid)+"&";
              url=url+"MenuId="+URLEncoder.encode(menu_id)+"&";
             url=url+"Quantity="+URLEncoder.encode(""+c)+"&";
         //   url=url+"name="+URLEncoder.encode(uname)+"&";
         //   url=url+"uid="+URLEncoder.encode(uid)+"&";
               url=url+"amount="+URLEncoder.encode(""+t)+"&";
               url=url+"iscart="+URLEncoder.encode("1")+"&";
            gettrans.execute(url);
        }catch (Exception e){
            Toast.makeText(Payamount.this, ""+e, Toast.LENGTH_SHORT).show();
        }






        /*
       btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Boolean status=true;
        String get_number=number.getText().toString();
        get_total=total.getText().toString();
        String mobilepattern = "[0-9]{16}";
        if (get_number.matches(mobilepattern)==false) {
            number.setError("Please Enter 16 Digit Number");
            number.requestFocus();
            status = false;
        }

        if(status){
            try {
                String iscart="0";
                // Toast.makeText(PlaceOrder.this, , Toast.LENGTH_SHORT).show();
                RegisterUser gettrans=new RegisterUser();
                DbParameter host=new DbParameter();
                String url=host.getHostpath();
                url=url+"/PlaceOrder.php?menutitle="+ URLEncoder.encode(menu_title)+"&";
               // url=url+"address="+ URLEncoder.encode(address)+"&";
              url=url+"HotelId="+URLEncoder.encode(hotelid)+"&";
                url=url+"MenuId="+URLEncoder.encode(menu_id)+"&";
                url=url+"Quantity="+URLEncoder.encode(quantity)+"&";
                url=url+"name="+URLEncoder.encode(uname)+"&";
                url=url+"uid="+URLEncoder.encode(uid)+"&";
                url=url+"amount="+URLEncoder.encode(get_total)+"&";
                url=url+"iscart="+URLEncoder.encode(iscart)+"&";
                gettrans.execute(url);
            }catch (Exception e){
                Toast.makeText(Payamount.this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
    }
});

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           String iscart="1";
               try {
                   Boolean status=true;
                   String get_number=number.getText().toString();
                   get_total=total.getText().toString();


                       // Toast.makeText(PlaceOrder.this, , Toast.LENGTH_SHORT).show();
                       RegisterUser gettrans = new RegisterUser();
                       DbParameter host = new DbParameter();
                       String url = host.getHostpath();
                       url = url + "/PlaceOrder.php?menutitle=" + URLEncoder.encode(menu_title) + "&";
                       // url=url+"address="+ URLEncoder.encode(address)+"&";
                       url = url + "HotelId=" + URLEncoder.encode(hotelid) + "&";
                       url = url + "MenuId=" + URLEncoder.encode(menu_id) + "&";
                       url = url + "Quantity=" + URLEncoder.encode(quantity) + "&";
                       url = url + "name=" + URLEncoder.encode(uname) + "&";
                       url = url + "uid=" + URLEncoder.encode(uid) + "&";
                       url = url + "iscart=" + URLEncoder.encode(iscart) + "&";
                       url = url + "amount=" + URLEncoder.encode(get_total) + "&";
                       gettrans.execute(url);
                       Intent i1 = new Intent(Payamount.this, Foodsearch.class);
                       startActivity(i1);

                } catch (Exception e) {
                    Toast.makeText(Payamount.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }

        });

       pay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                PackageManager pm = getPackageManager();

                   try {

                       pm.getPackageInfo("paytm.com", PackageManager.GET_ACTIVITIES);

                       Intent LaunchIntent = pm.getLaunchIntentForPackage("com.paytm.com");

                       LaunchIntent.setFlags(0);

                       startActivityForResult(LaunchIntent, 5);

                   } catch (PackageManager.NameNotFoundException e) {

                       Uri URLURI = Uri.parse("https://play.google.com/store/apps/details?id=net.one97.paytm&hl=en");

                       Intent intent = new Intent(Intent.ACTION_VIEW, URLURI);

                       startActivity(intent);
                   }

           }
       });
       */
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
            progress = ProgressDialog.show(Payamount.this, null,
                    "Processing...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
         //  Toast.makeText(Payamount.this, " "+out, Toast.LENGTH_SHORT).show();
           // Toast.makeText(Payamount.this, "Output"+out, Toast.LENGTH_SHORT).show();
try {
    if(out.contains("1")){
        if(address.contentEquals("rajkamal")){
            Toast.makeText(Payamount.this,"10 minutes order deliver at your house",Toast.LENGTH_SHORT).show();
        }else if(address.contentEquals("rajapeth")){
            Toast.makeText(Payamount.this,"15 minutes order deliver at your house",Toast.LENGTH_SHORT).show();
        }else if(address.contentEquals("rukmani nagar")){
            Toast.makeText(Payamount.this,"25 minutes order deliver at your house",Toast.LENGTH_SHORT).show();
        }else if(address.contentEquals("panchvati")){
            Toast.makeText(Payamount.this,"40 minutes order deliver at your house",Toast.LENGTH_SHORT).show();
        }else if(address.contentEquals("sai nagar")){
            Toast.makeText(Payamount.this,"30 minutes order deliver at your house",Toast.LENGTH_SHORT).show();
        }else if(address.contentEquals("gopal nagar")){
            Toast.makeText(Payamount.this,"24 minutes order deliver at your house",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Payamount.this,"60 minutes minimum for order deliver at your house",Toast.LENGTH_SHORT).show();

        }
        /*
        Toast.makeText(Payamount.this, "You are Place Order Sucessfully", Toast.LENGTH_SHORT).show();
        String sms="Place Order"+address+""+menu_title+"By Customer";
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(b_number,null,sms,null,null);
        Toast.makeText(Payamount.this,"Owner Sent Request Of your Food",Toast.LENGTH_SHORT).show();
        number.setText("");
        */

        //address.setText("");

    }

}catch (Exception e){
    Toast.makeText(Payamount.this,"60 minutes minimum for order deliver at your house",Toast.LENGTH_SHORT).show();

}


            progress.dismiss();
            finish();
            Intent i1= new Intent(Payamount.this,Foodsearch.class);
            startActivity(i1);
            super.onPostExecute(result);
        }



    }
}
