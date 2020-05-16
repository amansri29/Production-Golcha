package com.golcha.golchaproduction.ui.dashboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;
import com.golcha.golchaproduction.ui.CustomAutoCompleteTextView;

import org.ksoap2.serialization.SoapObject;

public class ReleasePro_OnList_Click extends Fragment {
    EditText editno,editdes,editdes2,editsourcetype,edit_quantity,editsourceno,editdepart,editlocation
            ,editmachine,editQunatity_send,editQunatity_sending
            ,editQunatity_accepted,editQunatity_rejected,editQunatity_rewoked;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String username,password;
    Activity activity;
    CheckBox checkBox1,checkBox2;
    String no,KEY;
    String no2,desc1,desc2,location,machine,department,source_type,
            source_no,p_quantity,Q_send,Q_sending,Q_accepted,Q_rejected,Q_Revoke;
    private static final String TAG = "ReleasePro_OnList_Click";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_release_pro__on_list__click, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);
        activity = getActivity();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);//passing saves username and pass to plan production
        username= sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");


        no=getArguments().getString("Releaseno");

        checkBox1=(CheckBox)root.findViewById(R.id.checkBox);
        checkBox2=(CheckBox)root.findViewById(R.id.checkBox2);
        checkBox1.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(compoundButton.isChecked()==true){
                            checkBox2.setChecked(false);
                        }
                    }
                }
        );
        checkBox2.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(compoundButton.isChecked()==true){
                            checkBox1.setChecked(false);
                        }
                    }
                }
        );




        editno=(EditText) root.findViewById(R.id.editTextno1);
        editdes=(EditText)root.findViewById(R.id.editTextdesc1);
        editdes2=(EditText)root.findViewById(R.id.editTextdesc2_1);
        editsourcetype=(EditText)root.findViewById(R.id.editTextsourcetype1);
        editsourceno=(EditText)root.findViewById(R.id.editTextsourceno1);
        edit_quantity=(EditText)root.findViewById(R.id.editTextpro_quan1);

        editdepart=(EditText)root.findViewById(R.id.editTextdepart1);

        editlocation=(EditText)root.findViewById(R.id.editTextloca1);

        editmachine = (EditText)root.findViewById(R.id.editTextMachine1);
        editQunatity_send=(EditText)root.findViewById(R.id.editQuantity_Sent_To_Quality);
        editQunatity_sending=(EditText)root.findViewById(R.id.editQuantity_Sent_To_Quality);

        editQunatity_accepted=(EditText)root.findViewById(R.id.editQuantity_Accepted);

        editQunatity_rejected=(EditText)root.findViewById(R.id.editQuantity_Rejected);

        editQunatity_rewoked = (EditText)root.findViewById(R.id.editQuantity_Rework);

        new MyCallwebService().execute();
        return root;
    }



    class MyButton_click extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {


            return "";
        }

        @Override
        protected void onPostExecute(String s) {


        }
    }





    class MyCallwebService extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                SoapObject result = SoapApis.getReleaseCardDetails(username,password,no);
                SoapObject result3 = (SoapObject) result.getProperty("ProdOrderLines");
                SoapObject result4 = (SoapObject) result3.getProperty("Released_Prod_Order_Lines");
                try {
                    no2 = String.valueOf(result.getProperty("No"));
                    KEY = String.valueOf(result.getProperty("Key"));
                    desc1 = String.valueOf(result.getProperty("Description"));
                    desc2 = String.valueOf(result.getProperty("Description_2"));
                    source_type = String.valueOf(result.getProperty("Source_Type"));
                    source_no = String.valueOf(result.getProperty("Source_No"));
                    p_quantity = String.valueOf(result.getProperty("Production_Quantity"));
                    department = String.valueOf(result.getProperty("Shortcut_Dimension_1_Code"));
                    location = String.valueOf(result.getProperty("Location_Code"));
                    machine = String.valueOf(result.getProperty("Shortcut_Dimension_2_Code"));
                    Q_send = String.valueOf(result4.getProperty("Quantity_Sent_To_Quality"));
                    Q_sending= String.valueOf(result4.getProperty("Quantity_Sending_To_Quality"));
                    Q_accepted = String.valueOf(result4.getProperty("Quantity_Accepted"));
                    Q_rejected = String.valueOf(result4.getProperty("Quantity_Rejected"));
                    Q_Revoke = String.valueOf(result4.getProperty("Quantity_Rework"));

                    Log.i(TAG, "New RESULT::" + no2 + " " + desc1 + " " + desc2);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            editno.setText(no2);
            editdes.setText(desc1);
            editdes2.setText(desc2);
            editsourcetype.setText(source_type);
            editsourceno.setText(source_no);
            edit_quantity.setText(p_quantity);

            editdepart.setText(department);

            editlocation.setText(location);

            editmachine.setText(machine);
            editQunatity_send.setText(Q_send);
            editQunatity_sending.setText(Q_sending);

            editQunatity_accepted.setText(Q_accepted);

            editQunatity_rejected.setText(Q_rejected);

            editQunatity_rewoked.setText(Q_Revoke);

        }
    }


}
