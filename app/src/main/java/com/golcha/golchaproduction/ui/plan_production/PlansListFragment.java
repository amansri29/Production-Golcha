package com.golcha.golchaproduction.ui.plan_production;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.soapapi.SoapApis;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class PlansListFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    ArrayList<GetPlanArrayList> list2= new ArrayList<>();
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
                        list2.add(new GetPlanArrayList(Source_No,Description,No,Routing_No,Quantity));
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
            PlanListAdapter planProducListAdaper = new PlanListAdapter(list2, activity);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(planProducListAdaper);

        }
    }

}
