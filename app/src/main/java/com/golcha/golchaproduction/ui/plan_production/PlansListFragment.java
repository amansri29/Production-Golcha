package com.golcha.golchaproduction.ui.plan_production;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class PlansListFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    ArrayList<PlannedOrderModel> list2= new ArrayList<>();
    RecyclerView recyclerView;
    String Source_No;
    String Description;
    String Routing_No;
    String Quantity;
    String No;
    Activity activity;

    private HomeViewModel homeViewModel;
    SharedPreferences sharedPreferences;
    String username,password;
    Toolbar toolbar;
    EditText seach_view;
    PlanListAdapter planProducListAdaper;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_plan_production_order, container, false);

        activity = getActivity();




        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);//passing saves username and pass to plan production
        username= sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");

        seach_view = (EditText) root.findViewById(R.id.auto_search);

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



        recyclerView = (RecyclerView)root.findViewById(R.id.recycleview2);
        Button button=(Button)root.findViewById(R.id.button_createfrag);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment, new PlansCreateFragment())
                                .commit();


                    }
                }
        );

        new CallWebService(1).execute();
        return root;
    }

    void filter_data(String text){
        ArrayList<PlannedOrderModel> result= new ArrayList<>();
        for(PlannedOrderModel data: list2){
            String search_item = data.getNo()
                    + data.getDesc()  + data.getSourceno()+ data.getQuantity();
            if(search_item.toLowerCase().contains(text.toLowerCase())){
                result.add(data);
            }
        }
        //update recyclerview
        planProducListAdaper.updateList(result);
    }






    class CallWebService extends AsyncTask<String, Void, String> {
        private int a;
        public CallWebService(int a) {
            this.a=a;
        }
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context;
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait, We are fetching the data");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result3 = "";


            try {
                SoapObject result = SoapApis.getPlannedOrderList(activity,username,password);
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject result2 = (SoapObject) result.getProperty(i);
                    try {
                        Source_No=String.valueOf(result2.getProperty("Source_No"));
//                        Description=String.valueOf(result2.getProperty("Description")) + " " +
//                                String.valueOf(result2.getProperty("Description_2"));
                        Description=String.valueOf(result2.getProperty("Description"));
                        No = String.valueOf(result2.getProperty("No"));
                        Routing_No = String.valueOf(result2.getProperty("Routing_No"));
                        Quantity = String.valueOf(result2.getProperty("Quantity"));
                        list2.add(new PlannedOrderModel(Source_No,Description,No,Routing_No,Quantity));
                      //  Log.i(TAG, "Planned List " +Source_No);
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
            planProducListAdaper = new PlanListAdapter(list2, activity);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(planProducListAdaper);
        }
    }

}
