package com.golcha.golchaproduction.ui.plan_production;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import com.golcha.golchaproduction.ui.CustomAutoCompleteTextView;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.golcha.golchaproduction.DropDownArrayAdapter;
import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;
import com.golcha.golchaproduction.ui.CustomAutoCompleteTextView;
import com.golcha.golchaproduction.ui.release_production.ReleaseListFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class PlanDetailsFragment extends Fragment {
    private static final String TAG = "HomExtendFrag";
    SharedPreferences sharedPreferences;
    String username,password;
    String no,KEY;
    Activity activity;
    Spinner autocom_items;
    String UpdateresulT;
    String Button_clickresult = "";
    ProgressDialog progressDialog;
    Button refresh,changestatus;
    String no2,desc1,desc2,source_type,source_no,p_quantity,department,location,machine;
    EditText editno,editdes,editdes2,editsourcetype,edit_quantity;
    ArrayList<String> mylocationlist;
    ArrayList<String> Department_list;
    ArrayList<String> machine_list;
    ArrayList<String>source_array;
    DropDownArrayAdapter Locationadapter;

    CustomAutoCompleteTextView editsourceno,editdepart,editlocation,editmachine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_hom_extend, container, false);
        activity=getActivity();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);//passing saves username and pass to plan production
        username= sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");
        mylocationlist = new ArrayList<>();
        Department_list = new ArrayList<>();
        machine_list = new ArrayList<>();



        activity = getActivity();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);




        no=getArguments().getString("no");


        editno=(EditText) root.findViewById(R.id.editTextno);
        editdes=(EditText)root.findViewById(R.id.editTextdesc);
        editdes2=(EditText)root.findViewById(R.id.editTextdesc2);
        editsourcetype=(EditText)root.findViewById(R.id.editTextsourcetype);

        edit_quantity=(EditText)root.findViewById(R.id.editTextpro_quan);
        editsourceno=(CustomAutoCompleteTextView)root.findViewById(R.id.editTextsourceno);
        showSuggestionsOnClick(editsourceno);
        setSelectedValue(editsourceno);

        editdepart=(CustomAutoCompleteTextView)root.findViewById(R.id.editTextdepart);
        showSuggestionsOnClick(editdepart);
        setSelectedValue(editdepart);

        editlocation=(CustomAutoCompleteTextView)root.findViewById(R.id.editTextloca);
        showSuggestionsOnClick(editlocation);
        setSelectedValue(editlocation);

        editmachine = (CustomAutoCompleteTextView)root.findViewById(R.id.editTextMachine);
        showSuggestionsOnClick(editmachine);
        setSelectedValue(editmachine);



        autocom_items = (Spinner) root.findViewById(R.id.edit_item_select);
        String[] list_itms = root.getResources().getStringArray(R.array.myitems);
        ArrayAdapter<String>  list_items_adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line,list_itms);


        autocom_items.setAdapter(list_items_adapter);
        autocom_items.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String x = autocom_items.getSelectedItem().toString().trim();
                new CallWebService_ref_create(x).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });







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
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait, We are fetching the data");
            progressDialog.show();

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String result3 = "";
            mylocationlist= SoapApis.getLocationlist(activity,username,password);
            Department_list = SoapApis.get_deprt_machine(activity,username,password,"1");
            machine_list = SoapApis.get_deprt_machine(activity,username,password,"2");

            try {
                SoapObject result = SoapApis.getPlannedCardDetails(username,password,no);
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
            editno.setEnabled(false);
            editdes.setText(desc1);
            editdes.setEnabled(false);
            editdes2.setText(desc2);
            editdes2.setEnabled(false);
            editsourcetype.setText(source_type);
            editsourcetype.setEnabled(false);
            editsourceno.setText(source_no);
            edit_quantity.setText(p_quantity);
            editlocation.setText(location);
            editdepart.setText(department);
            editmachine.setText(machine);








            Boolean fullaccess = sharedPreferences.getBoolean("fullaccess",false);
            if (fullaccess == false) {
                Gson gson = new Gson();
                String json = sharedPreferences.getString("Location_code_name","");
                Type type = new TypeToken<ArrayList<String>>(){}.getType();
                ArrayList<String> mynewLocationlist = new ArrayList<String>();
                mynewLocationlist = gson.fromJson(json,type);
                for(int i=0;i<mynewLocationlist.size();i++){
                    Log.i("mmynewLocations",mynewLocationlist.get(i));
                }

                Locationadapter = new DropDownArrayAdapter(getContext(), R.layout.drop_down_items,mynewLocationlist );
            }
            else{
                Locationadapter = new DropDownArrayAdapter(getContext(), R.layout.drop_down_items,mylocationlist );
            }

            DropDownArrayAdapter Departmentadapter = new DropDownArrayAdapter(getContext(), R.layout.drop_down_items, Department_list);
            DropDownArrayAdapter Machineadapter = new DropDownArrayAdapter(getContext(), R.layout.drop_down_items, machine_list);

            editmachine.setThreshold(1);
            editmachine.setAdapter(Machineadapter);

           editdepart.setThreshold(1);
           editdepart.setAdapter(Departmentadapter);

            editlocation.setThreshold(1);
            editlocation.setAdapter(Locationadapter);








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

                String source_no,product_quantity,location_code,department,machine;
                source_no = editsourceno.getText().toString().trim();
                product_quantity  = edit_quantity.getText().toString().trim();
                location_code = editlocation.getText().toString().trim();
                department = editdepart.getText().toString().trim();
                machine = editmachine.getText().toString().trim();
                Log.i("loc_dep_machine",location_code +" "+ department + " "+ machine);
                Log.i("Previous_key",KEY);
                UpdateresulT= SoapApis.UpdatenewPlan(username,password,source_no,product_quantity,location_code,department,machine,KEY);
                Log.i("mygetting numbet",UpdateresulT + " " +no);
               if(UpdateresulT.equals(no)){
                   Button_clickresult = SoapApis.Refreshbutton(username,password,UpdateresulT);
                   if (Button_clickresult.equals("SUCCESSFULLY REFRESHED")) {
                       try {
                           SoapObject result = SoapApis.getPlannedCardDetails(username,password,no);
                           try {
                               String key = String.valueOf(result.getProperty("Key"));
                               KEY = key;
                               Log.i(TAG," "+KEY);
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }
                       catch (Exception e) {
                           e.printStackTrace();
                       }

                   }
               }

            }
            else{
                if(button_click.equals("changestatus")){
                    Button_clickresult = SoapApis.ChangeStatus_button(activity,username,password,no);
                }
                else {
                    source_array = SoapApis.getSource_no(activity,username,password,button_click+"*");
                }
            }

            return result3;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (button_click.equals("refresh")) {
                if (UpdateresulT.equals(no)) {

                    AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                    builder.setMessage(Button_clickresult);
                    builder.setTitle("Refresh NUMBER");
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
            } else {
                if( button_click.equals("changestatus")){
                    AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                    builder.setMessage(Button_clickresult);
                    builder.setTitle("Changed NUMBER");
                    builder.setCancelable(false);
                    builder.setPositiveButton(
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    String x[]=Button_clickresult.split(" ");
                                    if (!x[0].equals("Earror")) {
                                        Fragment fragment = new ReleaseListFragment();
                                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.nav_host_fragment,fragment).commit();
                                    }
                                }
                            }
                    );
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
                else {
                    DropDownArrayAdapter adapter = new DropDownArrayAdapter(activity, R.layout.drop_down_items, source_array);
                    Log.i("Background", "onPostExecute: " + source_array.size());
                    adapter.notifyDataSetChanged();
                    editsourceno.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }


        }
    }
    private void showSuggestionsOnClick(final CustomAutoCompleteTextView customAutoCompleteTextView){
        customAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    customAutoCompleteTextView.showDropDown();
                }
            }
        });
    }

    private void setSelectedValue(final CustomAutoCompleteTextView customAutoCompleteTextView)
    {
        customAutoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String[] splited = customAutoCompleteTextView.getText().toString().split("\\s+");
                        if(splited.length > 0)
                        {
                            customAutoCompleteTextView.setText(splited[0]);
                        }
                    }
                }
        );
    }
}
