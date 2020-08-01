package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class UpdateMenuHere extends AppCompatActivity {
    String title_get,desc_get,cost_get,offer_get,menuid_get;
    EditText title,cost,offer,descr;
    Spinner item;
    Button submit;
    SharedPreferences shr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu_here);
        Bundle bundle=getIntent().getExtras();

        title_get=bundle.getString("menttitle");
      cost_get=bundle.getString("cost");
      desc_get=bundle.getString("desc");
 offer_get=bundle.getString("offer");
        menuid_get=bundle.getString("menuid");

       // Toast.makeText(UpdateMenuHere.this,""+title+cost+desc+offer,Toast.LENGTH_SHORT).show();
        shr= PreferenceManager.getDefaultSharedPreferences(UpdateMenuHere.this);
        title=(EditText)findViewById(R.id.menutitle);
        cost=(EditText)findViewById(R.id.menucost);
        offer=(EditText)findViewById(R.id.menuoffer);
        descr=(EditText)findViewById(R.id.menudes);
        item=(Spinner)findViewById(R.id.menuitem);
        submit=(Button)findViewById(R.id.submit);

        title.setText(title_get);
        cost.setText(cost_get);
        descr.setText(desc_get);
        offer.setText(offer_get);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_title,get_cost,get_offer,get_item;
                String get_des;
                boolean status=true;
                get_title=title.getText().toString().trim();
                get_cost=cost.getText().toString().trim();
                get_des=descr.getText().toString().trim();
                get_offer=offer.getText().toString().trim();

                get_item=item.getSelectedItem().toString().trim();

                if(get_title.contentEquals("")){

                    title.setError("Enter Title Name");
                    title.requestFocus();
                    status=false;
                }
                if(get_cost.contentEquals("")){
                    cost.setError("Enter cost");
                    cost.requestFocus();
                    status=false;
                }
                if(get_des.contentEquals("")){
                    descr.setError("Enetr Menu Description");
                    descr.requestFocus();
                    status=false;
                }

                if(get_offer.contentEquals("")){
                    offer.setError("Enter Offer");
                    offer.requestFocus();
                    status=false;
                }
                if(get_item.contentEquals("")){
                    item.requestFocus();
                    status=false;
                }

                if(status){
                    try{
                        String hotelid=shr.getString("userid", "Not Found");
                 UpdateMenu gettrans=new UpdateMenu();
                        DbParameter host=new DbParameter();
                        String url=host.getHostpath();
                        url=url+"/UpdateMenu.php?menuId="+ URLEncoder.encode(menuid_get)+"&";
                        url=url+"Category="+URLEncoder.encode(get_item)+"&";
                        url=url+"menutitle="+URLEncoder.encode(get_title)+"&";
                        url=url+"desc="+URLEncoder.encode(get_des)+"&";
                        url=url+"cost="+URLEncoder.encode(get_cost)+"&";
                        url=url+"offer="+URLEncoder.encode(get_offer)+"&";
                        gettrans.execute(url);
                    }catch(Exception e){
                        Toast.makeText(UpdateMenuHere.this, ""+e, Toast.LENGTH_SHORT).show();
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
            progress = ProgressDialog.show(UpdateMenuHere.this, null,
                    "Updating...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //  Toast.makeText(Addmenu.this, " "+out, Toast.LENGTH_SHORT).show();
            //Toast.makeText(SignUp.this, "Output"+out, 500).show();
            if(out.contains("1")){
                Toast.makeText(UpdateMenuHere.this, "Menu Update Sucessfully", Toast.LENGTH_SHORT).show();
                title.setText("");
                descr.setText("");
                cost.setText("");
                offer.setText("");
                finish();
                //password.setText("");
            }

            progress.dismiss();
            super.onPostExecute(result);
        }


    }
}
