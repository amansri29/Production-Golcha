package com.golcha.golchaproduction.ui.home;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;

import org.ksoap2.serialization.SoapObject;


public class HomExtendFrag extends Fragment {
    private static final String TAG = "HomExtendFrag";
    SharedPreferences sharedPreferences;
    String username,password;
    String no;
    Activity activity;
    String Button_clickresult = "";
    ProgressDialog progressDialog,progressDialog2;
    Button refresh,changestatus;
    String no2,desc1,desc2,source_type,source_no,p_quantity,department,location,machine;
    EditText editno,editdes,editdes2,editsourcetype,editsourceno,edit_quantity,editdepart,editlocation,editmachine;
    TextView Textno,Textdes,Textdes2,Textsourcetype,Textsourceno,Text_quantity,Textdepart,Textlocation,Textmachine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_hom_extend, container, false);
        activity=getActivity();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);//passing saves username and pass to plan production
        username= sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");



        activity = getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);


        no=getArguments().getString("no");


        editno=(EditText) root.findViewById(R.id.editTextno);
        editdes=(EditText)root.findViewById(R.id.editTextdesc);
        editdes2=(EditText)root.findViewById(R.id.editTextdesc2);
        editsourcetype=(EditText)root.findViewById(R.id.editTextsourcetype);
        editsourceno=(EditText)root.findViewById(R.id.editTextsourceno);
        edit_quantity=(EditText)root.findViewById(R.id.editTextpro_quan);
        editdepart=(EditText)root.findViewById(R.id.editTextdepart);
        editlocation=(EditText)root.findViewById(R.id.editTextloca);
        editdepart = (EditText)root.findViewById(R.id.editTextdepart);
        editmachine = (EditText)root.findViewById(R.id.editTextMachine);


        Textno =(TextView)root.findViewById(R.id.textViewno);
        Textdes=(TextView)root.findViewById(R.id.textViewdesc);
        Textdes2=(TextView)root.findViewById(R.id.textViewdesc2);
        Textsourcetype=(TextView)root.findViewById(R.id.textViewsourectype);
        Textsourceno=(TextView)root.findViewById(R.id.textViewsourceno);
        Text_quantity=(TextView)root.findViewById(R.id.textViewpro_quan);
        Textdepart=(TextView)root.findViewById(R.id.textViewdepart);
        Textlocation=(TextView)root.findViewById(R.id.textViewloca);
        Textmachine = (TextView)root.findViewById(R.id.editTextMachine);
        Textno.setText("number");
        Textdes.setText("Description_1");
        Textdes2.setText("Description_2");
        Textsourcetype.setText("Source_type");
        Textsourceno.setText("Source_no");
        Textdepart.setText("Department");
        Text_quantity.setText("Product_qty");
        Textlocation.setText("Location");






        refresh = (Button)root.findViewById(R.id.refreshbutton);
        refresh.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CallWebService_ref_create("refresh").execute();
                    }
                }
        );
        changestatus = (Button)root.findViewById(R.id.changestatus);
        changestatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CallWebService_ref_create("changestatus").execute();
                    }
                }
        );
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
                    department = String.valueOf(result.getProperty("Shortcut_Dimension_1_Code"));
                    location = String.valueOf(result.getProperty("Location_Code"));
                    machine = String.valueOf(result.getProperty("Shortcut_Dimension_2_Code"));

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
            editno.setText(no);
            editdes.setText(desc1);
            editdes2.setText(desc2);
            editsourcetype.setText(source_type);
            editsourceno.setText(source_no);
            edit_quantity.setText(p_quantity);
            editlocation.setText(location);
            editdepart.setText(department);
            editmachine.setText(machine);

        }
    }
    class CallWebService_ref_create extends AsyncTask<String, Void, String> {
        String button_click;
        public CallWebService_ref_create(String button_click) {
            this.button_click=button_click;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String result3 = "";
            if(button_click.equals("refresh")){
                Button_clickresult = SoapApis.Refreshbutton(activity,username,password,no);
            }
            else{
                Button_clickresult = SoapApis.ChangeStatus_button(activity,username,password,no);
            }

            return result3;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
            builder.setMessage(Button_clickresult);
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


        }
    }
}
