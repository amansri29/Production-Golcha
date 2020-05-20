package com.golcha.golchaproduction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.golcha.golchaproduction.ui.finish_production.FinishedPro_List;
import com.golcha.golchaproduction.ui.release_production.ReleaseListFragment;
import com.golcha.golchaproduction.ui.plan_production.PlansListFragment;
import com.golcha.golchaproduction.ui.finish_production.FinishListFragment;


public class PrfRedirectFragment extends Fragment {

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

                        Fragment fragment = new PlansListFragment();
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

                        Fragment fragment = new ReleaseListFragment();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction().replace(R.id.nav_host_fragment,fragment)
                                .commit();

                    }
                }
        );
        Button final_btn = (Button)root.findViewById(R.id.final_production);
        final_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Fragment fragment = new FinishedPro_List();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction().replace(R.id.nav_host_fragment,fragment)
                                .commit();


                    }
                }
        );
        return  root;
    }
}
