package com.golcha.golchaproduction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.golcha.golchaproduction.ui.finish_production.FinishedPro_List;
import com.golcha.golchaproduction.ui.release_production.ReleaseListFragment;
import com.golcha.golchaproduction.ui.release_production.ReleaseDetailFragment;
import com.golcha.golchaproduction.ui.plan_production.PlanDetailsFragment;
import com.golcha.golchaproduction.ui.plan_production.PlansListFragment;
import com.golcha.golchaproduction.ui.plan_production.PlansCreateFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //EditText password;
    LinearLayout linearLayout;
    private Calendar calendar,calendar2;
    private SimpleDateFormat dateFormat;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new PrfRedirectFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.nav_host_fragment,fragment)
                .commit();
        this.getFragmentManager().popBackStack();
        sharedPreferences2 =getSharedPreferences("Save_Username", Context.MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();








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
                String username = sharedPreferences.getString("username","");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                editor2.putString("myusername",username);
                editor2.commit();
                Intent intent = new Intent(MainActivity.this,Login_activity.class);
                MainActivity.this.finish();
                startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment1 = null;


        if(fragment instanceof PlansCreateFragment){
            fragment1 = new PlansListFragment();
        }
        else if(fragment instanceof PlansListFragment){
            fragment1 = new PrfRedirectFragment();
        }

        else if(fragment instanceof ReleaseDetailFragment){
            fragment1 = new ReleaseListFragment();
        }
        else if(fragment instanceof ReleaseListFragment){
            fragment1 = new PrfRedirectFragment();
        }
        else if(fragment instanceof PlanDetailsFragment){
            fragment1 = new PlansListFragment();
        }
        else if(fragment instanceof FinishedPro_List){
            fragment1 = new PrfRedirectFragment();
        }
        if(fragment1 != null) {

            manager.beginTransaction().replace(R.id.nav_host_fragment, fragment1)
                    .commit();
        }

        return true;
    }
}
