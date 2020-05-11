package com.golcha.golchaproduction.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;

import org.ksoap2.serialization.SoapObject;


public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);


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
                        Log.i(TAG, "Planned List " + result2.getProperty("Description") + " "  + result2.getProperty("Source_No"));
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

        }
    }
}
