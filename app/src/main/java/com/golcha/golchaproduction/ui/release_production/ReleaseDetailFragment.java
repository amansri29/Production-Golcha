package com.golcha.golchaproduction.ui.release_production;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.golcha.golchaproduction.PrfRedirectFragment;
import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;

import org.ksoap2.serialization.SoapObject;

public class ReleaseDetailFragment extends Fragment {
    EditText editno,editdes,edit_quantity,editsourceno,editdepart,editlocation
            ,editmachine,editQunatity_send,editQunatity_sending
            ,editQunatity_accepted,editQunatity_rejected,editQunatity_rewoked,
        edit_no_of_bags;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String username,password,UpdateresulT;
    Activity activity;
    CheckBox checkBox1,checkBox2;
    String no,KEY,KEY2,Button_clickresult;
    String no2,desc1,desc2,location,machine,department,source_type,
            source_no,p_quantity,Q_send,Q_sending,Q_accepted,Q_rejected,Q_Revoke, bags;
    private static final String TAG = "ReleasePro_OnList_Click";
    Boolean hourlyy =true ,compositt =false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_release_details, container, false);
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
                            hourlyy = true;
                            compositt = false;
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
                             compositt= true;
                             hourlyy = false;
                            checkBox1.setChecked(false);
                        }
                    }
                }
        );



        edit_no_of_bags = (EditText) root.findViewById(R.id.no_of_bags);
        editno=(EditText) root.findViewById(R.id.editTextno1);
        editdes=(EditText)root.findViewById(R.id.editTextdesc1);
//        editdes2=(EditText)root.findViewById(R.id.editTextdesc2_1);
//        editsourcetype=(EditText)root.findViewById(R.id.editTextsourcetype1);
        editsourceno=(EditText)root.findViewById(R.id.editTextsourceno1);
        edit_quantity=(EditText)root.findViewById(R.id.editTextpro_quan1);

        editdepart=(EditText)root.findViewById(R.id.editTextdepart1);

        editlocation=(EditText)root.findViewById(R.id.editTextloca1);

        editmachine = (EditText)root.findViewById(R.id.editTextMachine1);
        editQunatity_send=(EditText)root.findViewById(R.id.editQuantity_Sent_To_Quality);
        editQunatity_sending=(EditText)root.findViewById(R.id.editQuantity_Sending_To_Quality);

        editQunatity_accepted=(EditText)root.findViewById(R.id.editQuantity_Accepted);

        editQunatity_rejected=(EditText)root.findViewById(R.id.editQuantity_Rejected);

        editQunatity_rewoked = (EditText)root.findViewById(R.id.editQuantity_Rework);

        Button button_create_ins = (Button)root.findViewById(R.id.create_inspection);
        button_create_ins.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String bags = edit_no_of_bags.getText().toString();
                        float quant = Float.parseFloat(editQunatity_sending.getText().toString());
                        Log.i(TAG, "onClick: "+quant);
                        if(!bags.isEmpty() & quant > 0)
                        {
                            new MyButton_click("createIns").execute();
                        }
                        else
                        {
                            Toast.makeText(activity, "No. of bags and Quantity sending to quality should not be empty!", Toast.LENGTH_LONG).show();
                        }


                    }
                }
        );
        Button changestatus_button = (Button)root.findViewById(R.id.changestatus_release);
        changestatus_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MyButton_click("changestatus").execute();
                    }
                }
        );

        new MyCallwebService().execute();
        return root;
    }



    class MyButton_click extends AsyncTask<String,Void,String>{
        String button_click;
        public MyButton_click(String button_click) {
            this.button_click = button_click;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();;

        }

        @Override
        protected String doInBackground(String... strings) {
            if (button_click.equals("createIns")) {
                Q_sending = editQunatity_sending.getText().toString().trim();
                bags = edit_no_of_bags.getText().toString();

                try {
                    UpdateresulT = SoapApis.UpdatenewRelease(username, password, hourlyy, compositt, Q_sending, KEY, KEY2, bags);
                    Log.i("mygetting numbet", UpdateresulT + " " + no);
                    if (UpdateresulT.equals(no)) {
                        Button_clickresult = SoapApis.CreateInspection_Release(username, password, UpdateresulT);
                        if (Button_clickresult.equals("SUCCESSFULLY Created Inspection")) {
                            try {
                                SoapObject result = SoapApis.getReleaseCardDetails(username, password, no);
                                SoapObject result3 = (SoapObject) result.getProperty("ProdOrderLines");
                                SoapObject result4 = (SoapObject) result3.getProperty("Released_Prod_Order_Lines");
                                try {
                                    KEY = String.valueOf(result.getProperty("Key"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Q_send = String.valueOf(result4.getProperty("Quantity_Sent_To_Quality"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Q_sending = String.valueOf(result4.getProperty("Quantity_Sending_To_Quality"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Q_accepted = String.valueOf(result4.getProperty("Quantity_Accepted"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Q_rejected = String.valueOf(result4.getProperty("Quantity_Rejected"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Q_Revoke = String.valueOf(result4.getProperty("Quantity_Rework"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    KEY2 = String.valueOf(result4.getProperty("Key"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Log.i(TAG, "Vipulsharma key" + "    " + KEY);
                                Log.i(TAG, "Vipulsharma key2" + "    " + KEY2);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
//                            In case of result failure
                            SoapObject result = SoapApis.getReleaseCardDetails(username,password,no);
                            SoapObject result3 = (SoapObject) result.getProperty("ProdOrderLines");
                            SoapObject result4 = (SoapObject) result3.getProperty("Released_Prod_Order_Lines");
                            try {
                                KEY = String.valueOf(result.getProperty("Key"));
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            try {
                                KEY2 = String.valueOf(result4.getProperty("Key"));
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }

                    }
                } catch (Exception e5) {
                    e5.printStackTrace();
                    //                            In Case of failure also update the key
                }
            }
            else{
                if(button_click.equals("changestatus")){
                    Button_clickresult = SoapApis.ChangeStatus_Releasebutton(activity,username,password,no);
                }
            }



            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (button_click.equals("createIns")) {
                if (UpdateresulT != null & UpdateresulT.equals(no)) {
                    AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                    builder.setMessage(Button_clickresult);
                    builder.setTitle("Inspection order successfully created.");
                    builder.setCancelable(false);
                    builder.setPositiveButton(
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    if(Button_clickresult.equals("SUCCESSFULLY Created Inspection")){
                                        editQunatity_send.setText(Q_send);
                                        editQunatity_sending.setText(Q_sending);

                                        editQunatity_accepted.setText(Q_accepted);

                                        editQunatity_rejected.setText(Q_rejected);

                                        editQunatity_rewoked.setText(Q_Revoke);

                                        edit_no_of_bags.setText("");

                                    }
                                }
                            }
                    );
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                    builder.setMessage(UpdateresulT);
                    builder.setTitle("Update Failed!");
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
            else {
                if( button_click.equals("changestatus")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(Button_clickresult);
                    builder.setTitle("FINAL PRODUCTION");
                    builder.setCancelable(false);
                    builder.setPositiveButton(
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    String x[] = Button_clickresult.split(" ");
                                    if(!x[0].equals("Earror")){
                                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.nav_host_fragment,new PrfRedirectFragment())
                                                .commit();
                                    }
                                }
                            }
                    );
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    getActivity().getFragmentManager().popBackStack();

                }


        }}
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
                    try {
                        no2 = String.valueOf(result.getProperty("No"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        KEY = String.valueOf(result.getProperty("Key"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        desc1 = String.valueOf(result.getProperty("Description"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        desc2 = String.valueOf(result.getProperty("Description_2"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        source_type = String.valueOf(result.getProperty("Source_Type"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        source_no = String.valueOf(result.getProperty("Source_No"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        p_quantity = String.valueOf(result.getProperty("Production_Quantity"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        department = String.valueOf(result.getProperty("Shortcut_Dimension_1_Code"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        location = String.valueOf(result.getProperty("Location_Code"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        machine = String.valueOf(result.getProperty("Shortcut_Dimension_2_Code"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Q_send = String.valueOf(result4.getProperty("Quantity_Sent_To_Quality"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Q_sending= String.valueOf(result4.getProperty("Quantity_Sending_To_Quality"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Q_accepted = String.valueOf(result4.getProperty("Quantity_Accepted"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Q_rejected = String.valueOf(result4.getProperty("Quantity_Rejected"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Q_Revoke = String.valueOf(result4.getProperty("Quantity_Rework"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        KEY2 = String.valueOf(result4.getProperty("Key"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG,"Vipulsharma key" +"    " + KEY);
                    Log.i(TAG,"Vipulsharma key2" +"    " + KEY2);

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
            editdes.setText(desc1 + " " + desc2);
//            editdes2.setText(desc2);
//            editsourcetype.setText(source_type);
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
