package com.golcha.golchaproduction.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


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
import android.widget.Toast;

import com.golcha.golchaproduction.DropDownAdapter;
import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;
import com.golcha.golchaproduction.ui.CustomAutoCompleteTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Home_to_CreateFrag extends Fragment {
    ArrayList<String> mylocationlist;
    ArrayList<String> Department_list;
    ArrayList<String> machine_list;
    ArrayList<String> Source_no;
    ProgressDialog progressDialog;
    CustomAutoCompleteTextView autocomp_textView, autocomp_department,autocom_machine,mysourceno ;
    Spinner autocom_items;
    EditText pro_quantity_edittxt;
    String resultof_newPlan;
    SharedPreferences sharedPreferences;
    Activity activity;
    String username,password;
    DropDownAdapter Locationadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home_to__create, container, false);
        activity=getActivity();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        username= sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");
        mylocationlist = new ArrayList<>();
        Department_list = new ArrayList<>();
        machine_list = new ArrayList<>();
        Source_no = new ArrayList<>();

        autocomp_textView = (CustomAutoCompleteTextView) root.findViewById(R.id.edit_loca_code);
        showSuggestionsOnClick(autocomp_textView);
        setSelectedValue(autocomp_textView);

        mysourceno = (CustomAutoCompleteTextView) root.findViewById(R.id.edit_auto_sourceno);
        showSuggestionsOnClick(mysourceno);
        setSelectedValue(mysourceno);

        autocomp_department= (CustomAutoCompleteTextView) root.findViewById(R.id.edit_item_select_department);
        showSuggestionsOnClick(autocomp_department);
        setSelectedValue(autocomp_department);


        autocom_machine = (CustomAutoCompleteTextView) root.findViewById(R.id.edit_item_select_machine);
        showSuggestionsOnClick(autocom_machine);
        setSelectedValue(autocom_machine);



        autocom_items = (Spinner) root.findViewById(R.id.edit_item_select);
        String[] list_itms = root.getResources().getStringArray(R.array.myitems);
        ArrayAdapter<String>  list_items_adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line,list_itms);


        autocom_items.setAdapter(list_items_adapter);
        autocom_items.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String x = autocom_items.getSelectedItem().toString().trim();
                new CallserviceGetResult(x).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });




        pro_quantity_edittxt = (EditText)root.findViewById(R.id.edit_prod_quan) ;

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);

        Button button = (Button)root.findViewById(R.id.create_new_plan);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(autocomp_textView.getText().toString().isEmpty() || mysourceno.getText().toString().isEmpty()
                           || pro_quantity_edittxt.getText().toString().isEmpty() || mysourceno.getText().toString().isEmpty())
                        {
                            Toast.makeText(getContext(),"Empty Field",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            new CallserviceGetResult("Create_new").execute();
                        }
                    }
                }
        );

        new CallwebService_forlocationList().execute();
        return root;
    }

    class CallwebService_forlocationList extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            mylocationlist= SoapApis.getLocationlist(activity, username, password);
            Department_list = SoapApis.get_deprt_machine(activity,username,password,"1");
            machine_list = SoapApis.get_deprt_machine(activity,username,password,"2");

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
//            ArrayAdapter<String> Locationadapter = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,mylocationlist);
//            ArrayAdapter<String> Departmentadapter = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,Department_list);
//            ArrayAdapter<String> Machineadapter= new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,machine_list);
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

                 Locationadapter = new DropDownAdapter(getContext(), R.layout.drop_down_items,mynewLocationlist );
            }
            else{
                 Locationadapter = new DropDownAdapter(getContext(), R.layout.drop_down_items,mylocationlist );
            }

            DropDownAdapter Departmentadapter = new DropDownAdapter(getContext(), R.layout.drop_down_items, Department_list);
            DropDownAdapter Machineadapter = new DropDownAdapter(getContext(), R.layout.drop_down_items, machine_list);

            autocomp_textView.setThreshold(1);
            autocomp_textView.setAdapter(Locationadapter);

            autocomp_department.setThreshold(1);
            autocomp_department.setAdapter(Departmentadapter);

            autocom_machine.setThreshold(1);
            autocom_machine.setAdapter(Machineadapter);
            super.onPostExecute(s);
        }
    }





    class CallserviceGetResult extends AsyncTask<String, Void, String> {
        private String x;
        // Construct the data source
        ArrayList source_array = new ArrayList<String>();

        public CallserviceGetResult(String x) {
            this.x=x;
        }


        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if(x.equals("Create_new")) {
                background_locationlist();
            }
            else {
                source_array = SoapApis.getSource_no(activity,username,password,x+"*");

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(x.equals("Create_new")) {
                post_locationList();
            }
            else {
//
                DropDownAdapter adapter = new DropDownAdapter(activity, R.layout.drop_down_items, source_array);
                Log.i("Background", "onPostExecute: " + source_array.size());
                adapter.notifyDataSetChanged();
                mysourceno.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }
    }



    public void background_locationlist(){
        String source_no,product_quantity,location_code,department,machine;
        source_no = mysourceno.getText().toString().trim();
        product_quantity  = pro_quantity_edittxt.getText().toString().trim();
        location_code = autocomp_textView.getText().toString().trim();
        department = autocomp_department.getText().toString().trim();
        machine = autocom_machine.getText().toString().trim();
       // Log.i("loc_dep_machine",locationsplit[0] +" "+ departmentsplit[0] + " "+ machinesplit[0]);

        resultof_newPlan= SoapApis.CreatenewPlan(username,password,source_no,product_quantity,location_code,department,machine);
        Log.i("number",resultof_newPlan);

    }


    public  void post_locationList(){
        final String result_number[] =resultof_newPlan.split(" ");
        AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
        builder.setMessage(resultof_newPlan);
        builder.setTitle("NEW NUMBER");
        builder.setCancelable(false);
        builder.setPositiveButton(
                "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        if (!result_number[0].equals("Earror")) {
                            Fragment fragment1 = new HomExtendFrag();
                            Bundle bundle =new Bundle();
                            bundle.putString("no",resultof_newPlan);
                            fragment1.setArguments(bundle) ;
                            FragmentManager manager = getFragmentManager();
                            manager.beginTransaction().replace(R.id.nav_host_fragment,fragment1)
                                    .commit();
                        }

                    }
                }
        );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //Toast.makeText(getContext(),"Number :" + resultof_newPlan,Toast.LENGTH_SHORT).show();



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
