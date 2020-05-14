package com.golcha.golchaproduction.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    ArrayList<Getplanarraylist> list2= new ArrayList<>();
    RecyclerView recyclerView;
    String Source_No;
    String Description;
    String Routing_No;
    String Quantity;
    String No;
    ProgressBar progressBar;
    Activity activity;

    private HomeViewModel homeViewModel;
    SharedPreferences sharedPreferences;
    String username,password;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        activity = getActivity();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);//passing saves username and pass to plan production
        username= sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");

        recyclerView = (RecyclerView)root.findViewById(R.id.recycleview2);
        progressBar = (ProgressBar)root.findViewById(R.id.progresbar2);
        Button button=(Button)root.findViewById(R.id.button_createfrag);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment, new Home_to_CreateFrag())
                                .addToBackStack(null)
                                .commit();
                        getActivity().getFragmentManager().popBackStack();

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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
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
                        Description=String.valueOf(result2.getProperty("Description"));
                        No = String.valueOf(result2.getProperty("No"));
                        Routing_No = String.valueOf(result2.getProperty("Routing_No"));
                        Quantity = String.valueOf(result2.getProperty("Quantity"));
                        list2.add(new Getplanarraylist(Source_No,Description,No,Routing_No,Quantity));
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
            progressBar.setVisibility(View.INVISIBLE);
            PlanProduc_ListAdaper planProducListAdaper = new PlanProduc_ListAdaper(list2, activity);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(planProducListAdaper);

        }
    }
}
