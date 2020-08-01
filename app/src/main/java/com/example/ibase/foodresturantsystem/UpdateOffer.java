package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class UpdateOffer extends AppCompatActivity {
    String offer_get,get_menuid;
    EditText offer;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_offer);
        Bundle bundle=getIntent().getExtras();

        offer_get=bundle.getString("offer");
        get_menuid=bundle.getString("menuid");

        offer=(EditText)findViewById(R.id.menuoffer);
        submit=(Button)findViewById(R.id.submit);
        offer.setText(offer_get);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean status=true;
                String get_offer;
                get_offer=offer.getText().toString().trim();
                if (get_offer.contentEquals("")){
                    offer.setError("Please Enter Offer");
                    offer.requestFocus();
                    status=false;
                }
                if(status){
try {
    UpdateMenu gettrans=new UpdateMenu();
    DbParameter host=new DbParameter();
    String url=host.getHostpath();
    url=url+"/ViewOffers.php?menuid="+ URLEncoder.encode(get_menuid)+"&";
    url=url+"offer="+URLEncoder.encode(get_offer)+"&";

    gettrans.execute(url);
}catch (Exception e){

}
                }
            }
        });


    }


    private class UpdateMenu extends AsyncTask<String, Integer, String> {
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
            progress = ProgressDialog.show(UpdateOffer.this, null,
                    "Updating...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //  Toast.makeText(Addmenu.this, " "+out, Toast.LENGTH_SHORT).show();
            //Toast.makeText(SignUp.this, "Output"+out, 500).show();
            if(out.contains("1")){
                Toast.makeText(UpdateOffer.this, "Menu Update Sucessfully", Toast.LENGTH_SHORT).show();

                offer.setText("");
                finish();
                //password.setText("");
            }

            progress.dismiss();
            super.onPostExecute(result);
        }


    }
}
