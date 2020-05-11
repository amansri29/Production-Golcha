package com.golcha.golchaproduction.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.golcha.golchaproduction.Getarraylist;
import com.golcha.golchaproduction.MyadapterList;
import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";
    ArrayList<Getarraylist> list= new ArrayList<>();
    RecyclerView recyclerView;
    String Source_No;
    String Description;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        recyclerView = (RecyclerView)root.findViewById(R.id.recycleview);


        // call the webservice
        new CallWebService().execute();

        return root;
    }


    class CallWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result3 = "";


            try {
                SoapObject result = SoapApis.getPlannedOrderList();
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject result2 = (SoapObject) result.getProperty(i);
                    try {
                        Source_No=String.valueOf(result2.getProperty("Source_No"));
                        Description=String.valueOf(result2.getProperty("Description"));
                        list.add(new Getarraylist(Source_No,Description));
                        Log.i(TAG, "Planned List " +Source_No);
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
            MyadapterList myadapterList=new MyadapterList(list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(myadapterList);

        }
    }
}
