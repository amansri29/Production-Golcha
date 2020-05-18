package com.golcha.golchaproduction;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.golcha.golchaproduction.soapapi.SoapApis;
import com.golcha.golchaproduction.ui.dashboard.DashboardFragment;
import com.golcha.golchaproduction.ui.dashboard.ReleasePro_OnList_Click;
import com.golcha.golchaproduction.ui.home.HomExtendFrag;
import com.golcha.golchaproduction.ui.home.HomeFragment;
import com.golcha.golchaproduction.ui.home.Home_to_CreateFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.ksoap2.serialization.SoapObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //EditText password;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new P_R_F_Redirect();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.nav_host_fragment,fragment)
                .commit();
        this.getFragmentManager().popBackStack();







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_button,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.acion_button:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                Login_activity.passedittext.setText("");
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if(fragment instanceof Home_to_CreateFrag){
            Fragment fragment1 = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.nav_host_fragment,fragment1)
                    .commit();

        }
        if(fragment instanceof HomeFragment){
            Fragment fragment1 = new P_R_F_Redirect();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.nav_host_fragment,fragment1)
                    .commit();

        }
        if(fragment instanceof DashboardFragment){
            Fragment fragment1 = new P_R_F_Redirect();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.nav_host_fragment,fragment1)
                    .commit();

        }
        if(fragment instanceof ReleasePro_OnList_Click){
            Fragment fragment1 = new DashboardFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.nav_host_fragment,fragment1)
                    .commit();

        }
        if(fragment instanceof HomExtendFrag){
            Fragment fragment1 = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.nav_host_fragment,fragment1)
                    .commit();

        }






        return true;
    }
}
