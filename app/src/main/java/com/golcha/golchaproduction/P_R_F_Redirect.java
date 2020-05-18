package com.golcha.golchaproduction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.golcha.golchaproduction.ui.dashboard.DashboardFragment;
import com.golcha.golchaproduction.ui.home.HomeFragment;
import com.golcha.golchaproduction.ui.notifications.NotificationsFragment;


public class P_R_F_Redirect extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_p__r__f__redirect, container, false);
        Button plan = (Button)root.findViewById(R.id.plan_production);
        plan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Fragment fragment = new HomeFragment();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction().replace(R.id.nav_host_fragment,fragment)

                                .commit();


                    }
                }
        );
        Button release = (Button)root.findViewById(R.id.release_production);
        release.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Fragment fragment = new DashboardFragment();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction().replace(R.id.nav_host_fragment,fragment)
                                .addToBackStack(null)
                                .commit();

                    }
                }
        );
        Button finaal = (Button)root.findViewById(R.id.final_production);
        finaal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Fragment fragment = new NotificationsFragment();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction().replace(R.id.nav_host_fragment,fragment)
                                .commit();


                    }
                }
        );
        return  root;
    }
}
