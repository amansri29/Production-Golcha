package com.golcha.golchaproduction.ui.finish_production;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;
import com.golcha.golchaproduction.ui.plan_production.PlannedOrderModel;


import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class FinishedPro_List extends Fragment {
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    FinishListAdapter adapter;
    String username,password;
    private Calendar calendar,calendar2;
    private SimpleDateFormat dateFormat;
    private String date;
    String Source_No;
    String Description;
    String Routing_No;
    String Quantity;
    String No;
    EditText seach_view;
    Activity activity;
    ArrayList<FinishOrderModel>list ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_finished_pro__list, container, false);
        recyclerView =(RecyclerView)root.findViewById(R.id.recycleview3);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgress(0);
        progressDialog.setMessage("please wait while we are fetching details");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);

        list = new ArrayList<>();
        activity = getActivity();

        calendar.getInstance().getTime();
        Date todayDate = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String mytodayDate = dateFormat.format(todayDate);

        calendar2 = Calendar.getInstance();
        calendar2.setTime(todayDate);
        calendar2.add(Calendar.DAY_OF_YEAR, -30);
        Date newDate = calendar2.getTime();
        String Back_date = dateFormat.format(newDate);

        date =Back_date+".."+mytodayDate;

        seach_view = (EditText) root.findViewById(R.id.auto_search2);

        seach_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter_data(s.toString());
            }
        });



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username= sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");




        new WebFinish_Pro().execute();
        return root;
    }
    void filter_data(String text){
        ArrayList<FinishOrderModel> result= new ArrayList<>();
        for(FinishOrderModel data: list){
            String search_item = data.getNo() + data.getRoutingno()
                    + data.getDesc()  + data.getSourceno()+ data.getQuantity();
            if(search_item.toLowerCase().contains(text.toLowerCase())){
                result.add(data);
            }
        }
        //update recyclerview
        adapter.updateList(result);
    }



    class WebFinish_Pro extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Log.i(TAG,date);
                SoapObject request = SoapApis.Final_Production_List(username,password,date);

                for(int i=0;i<request.getPropertyCount();i++){

                    SoapObject result2 = (SoapObject) request.getProperty(i);
                    try {
                        Source_No=String.valueOf(result2.getProperty("Source_No"));
                        Description=String.valueOf(result2.getProperty("Description")) + " " +
                                String.valueOf(result2.getProperty("Description_2"));
//                        Description=String.valueOf(result2.getProperty("Description"));
                        No = String.valueOf(result2.getProperty("No"));
                        Routing_No = String.valueOf(result2.getProperty("Location_Code"));
                        Quantity = String.valueOf(result2.getProperty("Quantity"));
                        list.add(new FinishOrderModel(Source_No,Description,No,Routing_No,Quantity));
                        Log.i(TAG,No+" "+Quantity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            adapter = new FinishListAdapter(list,activity);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

        }
    }


}
