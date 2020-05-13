package com.golcha.golchaproduction.ui.home;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;

import org.ksoap2.serialization.SoapObject;


public class HomExtendFrag extends Fragment {
    private static final String TAG = "HomExtendFrag";
    String no;
    ProgressDialog progressDialog;
    String no2,desc1,desc2,source_type,source_no,p_quantity,department,location;
    TextView textViewno,textViewdes,textViewdes2,textViewsourcetype,textViewsourceno,textViewp_quantity,textViewdepart,textViewlocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_hom_extend, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        no=getArguments().getString("no");
        textViewno=(TextView)root.findViewById(R.id.textViewno);
        textViewdes=(TextView)root.findViewById(R.id.textViewdesc);
        textViewdes2=(TextView)root.findViewById(R.id.textViewdesc2);
        textViewsourcetype=(TextView)root.findViewById(R.id.textViewsourectype);
        textViewsourceno=(TextView)root.findViewById(R.id.textViewsourceno);
        textViewp_quantity=(TextView)root.findViewById(R.id.textViewpro_quan);
        textViewdepart=(TextView)root.findViewById(R.id.textViewdepart);
        textViewlocation=(TextView)root.findViewById(R.id.textViewloca);
        new CallWebService().execute();


        return root;
    }
    class CallWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            progressDialog.show();

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String result3 = "";

            try {
                SoapObject result = SoapApis.getPlannedCardDetails(no);
                try {
                    no2 = String.valueOf(result.getProperty("No"));
                    desc1 = String.valueOf(result.getProperty("Description"));
                    desc2 = String.valueOf(result.getProperty("Description_2"));
                    source_type = String.valueOf(result.getProperty("Source_Type"));
                    source_no = String.valueOf(result.getProperty("Source_No"));
                    p_quantity = String.valueOf(result.getProperty("Production_Quantity"));
                 //   department = String.valueOf(result.getProperty(""));
                    location = String.valueOf(result.getProperty("Location_Code"));

                    Log.i(TAG, "Planned Card::" + no2 + " " + desc1 + " " + desc2);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.e(TAG, "Error api " + e.toString());
            }



            return result3;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            textViewno.setText(no);
            textViewdes.setText(desc1);
            textViewdes2.setText(desc2);
            textViewsourcetype.setText(source_type);
            textViewsourceno.setText(source_no);
            textViewp_quantity.setText(p_quantity);
            textViewlocation.setText(location);

        }
    }
}
