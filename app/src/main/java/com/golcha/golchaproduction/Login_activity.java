package com.golcha.golchaproduction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.golcha.golchaproduction.soapapi.SoapApis;

import java.util.ArrayList;

public class Login_activity extends AppCompatActivity {

    ArrayList<String> list;
    ArrayList<String> mylist;
    Activity activity;
    EditText useredittext;
    public  static EditText passedittext;
    String Logins =null;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        activity = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);

        list = new ArrayList<>();
        mylist = new ArrayList<>();
        useredittext = (EditText)findViewById(R.id.username);
        passedittext = (EditText)findViewById(R.id.password);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean logins = sharedPreferences.getBoolean("Loginaccess",false);
        if(logins==true){
            Intent intent = new Intent(Login_activity.this ,MainActivity.class);
            startActivity(intent);
            finish();
        }


        Button button = (Button)findViewById(R.id.login);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Callwebser().execute();
                    }}
        );
        }
        class Callwebser extends AsyncTask<String,Void,String>{
            @Override
            protected void onPreExecute() {
                progressDialog.show();
                super.onPreExecute();
            }


            @Override
            protected String doInBackground(String... strings) {
                String username = useredittext.getText().toString().trim();
                String password = passedittext.getText().toString().trim();
                Logins = SoapApis.Login(activity,username,password);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();

                if (!Logins.equals("successful")) {
                    AlertDialog.Builder builder =new AlertDialog.Builder(Login_activity.this);
                    builder.setMessage(Logins);
                    builder.setTitle("Failed!");
                    builder.setCancelable(false);
                    builder.setPositiveButton(
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }
                    );
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                super.onPostExecute(s);
            }

        }


}
