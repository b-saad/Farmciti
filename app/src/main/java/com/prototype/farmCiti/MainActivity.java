package com.prototype.farmCiti;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.prototype.farmCiti.fragments.Analyticsfrag;
import com.prototype.farmCiti.fragments.Dashboardfrag;
import com.prototype.farmCiti.fragments.Entryformfrag;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener  mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_analytics:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main, Analyticsfrag.newInstance(), getString(R.string.analytics_frag)).commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main, Dashboardfrag.newInstance(), getString(R.string.dashboard_frag)).commit();
                    return true;
                case R.id.navigation_entryform:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main, Entryformfrag.newInstance(), getString(R.string.entryform_frag)).commit();
                    return true;
                default:
                    return false;
            }
        }
    };;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container_main, Entryformfrag.newInstance(), getString(R.string.entryform_frag)).commit();
    }

}
