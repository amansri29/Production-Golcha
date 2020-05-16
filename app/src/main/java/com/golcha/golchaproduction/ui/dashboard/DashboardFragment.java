package com.golcha.golchaproduction.ui.dashboard;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
    Activity activity;
    private static final String TAG = "DashboardFragment";
    ArrayList<GetReleasearraylist> list= new ArrayList<>();
    RecyclerView recyclerView;
    String Source_No;
    String Description;
    String Routing_No;
    String Quantity;
    String No;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    String username,password;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        activity=getActivity();
        final TextView textView = root.findViewById(R.id.text_dashboard);
        recyclerView = (RecyclerView)root.findViewById(R.id.recycleview);
        progressBar=(ProgressBar)root.findViewById(R.id.progresbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);//passing saves username and pass to plan production
        username= sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");



        // call the webservice
        new CallWebService().execute();

        return root;
    }


    class CallWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
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
                        Description = String.valueOf(result2.getProperty("Description"));
                        No = String.valueOf(result2.getProperty("No"));
                        Routing_No = String.valueOf(result2.getProperty("Routing_No"));
                        Quantity = String.valueOf(result2.getProperty("Quantity"));
                        list.add(new GetReleasearraylist(Source_No,Description,No,Routing_No,Quantity));
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
            progressBar.setVisibility(View.INVISIBLE);
            ReleaseProd_Listadapter releaseProdListadapter =new ReleaseProd_Listadapter(activity,list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(releaseProdListadapter);

        }
    }
}
