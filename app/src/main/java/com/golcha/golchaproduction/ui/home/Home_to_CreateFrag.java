package com.golcha.golchaproduction.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Home_to_CreateFrag extends Fragment {
    ArrayList<String> mylist;
    ProgressDialog progressDialog;
    AutoCompleteTextView autocomp_textView,autocom_items;
    EditText source_edittxt,pro_quantity_edittxt;
    String resultof_newPlan;
    SharedPreferences sharedPreferencesl;
    Activity activity;

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home_to__create, container, false);
        activity=getActivity();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        mylist = new ArrayList<>();
        autocomp_textView = (AutoCompleteTextView)root.findViewById(R.id.edit_loca_code);
        autocom_items = (AutoCompleteTextView)root.findViewById(R.id.edit_item_select);
        String[] list_itms = root.getResources().getStringArray(R.array.myitems);
        ArrayAdapter<String>  list_items_adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line,list_itms);
        autocom_items.setThreshold(1);
        autocom_items.setAdapter(list_items_adapter);
        autocom_items.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String x = autocom_items.getText().toString().trim();
                        new CallserviceGetResult(x).execute();
                    }
                }
        );


        source_edittxt = (EditText)root.findViewById(R.id.edit_create_sourceno);
        pro_quantity_edittxt = (EditText)root.findViewById(R.id.edit_prod_quan) ;

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);

        Button button = (Button)root.findViewById(R.id.create_new_plan);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(autocomp_textView.getText().toString().isEmpty() || source_edittxt.getText().toString().isEmpty()
                           || pro_quantity_edittxt.getText().toString().isEmpty())
                        {
                            Toast.makeText(getContext(),"Empty Field",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            new CallserviceGetResult("Create_new").execute();
                        }
                    }
                }
        );

        new CallwebService_forlocationList().execute();
        return root;
    }

    class CallwebService_forlocationList extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            ArrayList<String> list = SoapApis.getLocationlist();
            for (int i = 0; i < list.size(); i++) {
                String[] location = list.get(i).split("-");
                mylist.add(location[0]);
                //Log.i("location_code",location[0] );
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,mylist);
            autocomp_textView.setThreshold(1);
            autocomp_textView.setAdapter(adapter);
            super.onPostExecute(s);
        }
    }





    class CallserviceGetResult extends AsyncTask<String, Void, String> {
        private String x;
        public CallserviceGetResult(String x) {
            this.x=x;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if(x.equals("Create_new")) {
                background_locationlist();
            }
            else {

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(x.equals("Create_new")) {
                post_locationList();
            }
            else {
                Toast.makeText(activity,x,Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }



    public void background_locationlist(){
        String source_no,product_quantity,location_code;
        source_no = source_edittxt.getText().toString().trim();
        product_quantity  = pro_quantity_edittxt.getText().toString().trim();
        location_code = autocomp_textView.getText().toString().trim();

        resultof_newPlan= SoapApis.CreatenewPlan(source_no,product_quantity,location_code);
        Log.i("number",resultof_newPlan);

    }
    public  void post_locationList(){
        AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
        builder.setMessage(resultof_newPlan);
        builder.setTitle("NEW NUMBER");
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
        //Toast.makeText(getContext(),"Number :" + resultof_newPlan,Toast.LENGTH_SHORT).show();
    }
}
