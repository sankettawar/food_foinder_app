package com.example.ibase.foodresturantsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class ChekoutPaylist extends AppCompatActivity {
    String totalamount[];
    String gettotalamount=null;
    String menutitle[];
    String quantity[];
    Button paynow;
    TextView view1;
    ListView lst;

    int grandtotal=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chekout_paylist);
lst=(ListView)findViewById(R.id.list);
        view1=(TextView) findViewById(R.id.textView4);
        paynow=(Button) findViewById(R.id.paynow);

        try{
          CheckOutData logs=new CheckOutData();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"/ViewCart.php";
            logs.execute(url);

            // String data=gettotalamount;

            //

        }catch (Exception e){
           // Toast.makeText(ChekoutPaylist.this,""+gettotalamount,Toast.LENGTH_SHORT).show();

        }

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(grandtotal<=100){
                    Toast.makeText(ChekoutPaylist.this,"Must have transaction amount greater than 100 Rs",Toast.LENGTH_SHORT).show();

                }else {


                    Intent i1 = new Intent(ChekoutPaylist.this, PayAmountAddtocart.class);
                    i1.putExtra("cost", "" + grandtotal);
                    startActivity(i1);
                }
            }
        });
    }


    private class   CheckOutData extends AsyncTask<String, Integer, String> {
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
            progress = ProgressDialog.show(ChekoutPaylist.this, null,"Processing...");

            super.onPreExecute();
        }





        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
           // Toast.makeText(ChekoutPaylist.this, " "+out, Toast.LENGTH_SHORT).show();
            try{
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();
                totalamount =new String[arraylength];
                menutitle=new String[arraylength];
                quantity=new String[arraylength];

                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    totalamount[i]="Total Amount :"+jsonChildNode.optString("Totalamount");
                    menutitle[i]="Menu : "+jsonChildNode.optString("MenuTitle");
                    quantity[i]="Quantity : "+jsonChildNode.optString("Quantity");
                    // Toast.makeText(MyNetwork.this, ""+ itemname[i], 500).show();
                    // employeeList.add(createEmployee("employees", outPut));
                    //totaljoining= totaljoining+1;

                    grandtotal=grandtotal+Integer.parseInt(jsonChildNode.optString("Totalamount"));
                }
               // Toast.makeText(ChekoutPaylist.this,""+totalamount[], Toast.LENGTH_SHORT).show();
                //tjoining.setText("Total Joining :"+totaljoining);
                view1.setText("Grand Total: "+grandtotal);

            }catch(Exception e){
                Toast.makeText(ChekoutPaylist.this," Json"+e, Toast.LENGTH_SHORT).show();
            }

            try{

                LevelAdapteraddtocard adapter = new LevelAdapteraddtocard(ChekoutPaylist.this, menutitle, quantity, totalamount);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(ChekoutPaylist.this,"No records Found", Toast.LENGTH_SHORT).show();
            }


            progress.dismiss();
        }

    }
}
