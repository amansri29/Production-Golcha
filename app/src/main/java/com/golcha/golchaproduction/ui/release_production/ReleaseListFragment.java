package com.golcha.golchaproduction.ui.release_production;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;
import com.golcha.golchaproduction.ui.plan_production.PlannedOrderModel;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;


public class ReleaseListFragment extends Fragment {
    Activity activity;
    private static final String TAG = "DashboardFragment";
    ArrayList<ReleaseModel> list= new ArrayList<>();
    RecyclerView recyclerView;
    String Source_No;
    String Description;
    String Routing_No;
    String Quantity;
    String No;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    String username,password;
    ReleaseListAdapter releaseProdListAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_release_order_list, container, false);
        activity=getActivity();
        recyclerView = (RecyclerView)root.findViewById(R.id.recycleview);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);//passing saves username and pass to plan production
        username= sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");



        EditText seach_view = (EditText) root.findViewById(R.id.auto_search);

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




        // call the webservice
        new CallWebService().execute();

        return root;
    }


    void filter_data(String text){
        ArrayList<ReleaseModel> result= new ArrayList<>();
        for(ReleaseModel data: list){
            String search_item = data.getNo() + data.getRoutingno()
                    + data.getDesc()  + data.getSourceno()+ data.getQuantity();
            if(search_item.toLowerCase().contains(text.toLowerCase())){
                result.add(data);
            }
        }
        //update recyclerview
        releaseProdListAdapter.updateList(result);
    }



    class CallWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String result3 = "";


            try {
                SoapObject result = SoapApis.getUpdatedOrderList(activity,username,password);
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject result2 = (SoapObject) result.getProperty(i);
                    try {
                        Source_No = String.valueOf(result2.getProperty("Source_No"));
                        Description=String.valueOf(result2.getProperty("Description")) + " " +
                                String.valueOf(result2.getProperty("Description_2"));
//                        Description = String.valueOf(result2.getProperty("Description"));
                        No = String.valueOf(result2.getProperty("No"));
                        Routing_No = String.valueOf(result2.getProperty("Location_Code"));
                        Quantity = String.valueOf(result2.getProperty("Quantity"));
                        list.add(new ReleaseModel(Source_No,Description,No,Routing_No,Quantity));
                       // Log.i(TAG, "Source_no " +Source_No);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                Log.e(TAG, "Error Inside " + e.toString());
            }


            return result3;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            releaseProdListAdapter =new ReleaseListAdapter(activity,list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(releaseProdListAdapter);

        }
    }
}
