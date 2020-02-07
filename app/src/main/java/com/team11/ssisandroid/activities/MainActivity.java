package com.team11.ssisandroid.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.team11.ssisandroid.R;
import com.team11.ssisandroid.fragments.CollectionFragment;
import com.team11.ssisandroid.fragments.RequisitionFragment;
import com.team11.ssisandroid.fragments.RetrievalFragment;
import com.team11.ssisandroid.interfaces.UserClient;
import com.team11.ssisandroid.models.Requisition;
import com.team11.ssisandroid.models.UserRole;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    SharedPreferences pref;
    NavigationView navigationView;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        role = pref.getString("role", null);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        if(role.contains("StoreClerk")){
            navigationView.getMenu().findItem(R.id.nav_store_retrieval).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_store_collection).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_stock_adjustment).setVisible(true);
        }

        else if (role.contains("StoreSupervisor") || role.contains("StoreManager")){
            navigationView.getMenu().findItem(R.id.nav_stock_adjustment_approval).setVisible(true);
        }

        else if(role.contains("DepartmentHead")){
            navigationView.getMenu().findItem(R.id.nav_requisition_approval).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_department_delegation).setVisible(true);
        }

        else if(role.contains("Employee")){
            navigationView.getMenu().findItem(R.id.nav_requisition).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_department_collection).setVisible(true);
        }

        return true;
    }

    //Based on item selected in menu, go to new fragment/page
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_requisition:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RequisitionFragment()).commit();
                break;
            case R.id.nav_store_retrieval:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RetrievalFragment()).commit();
                break;
            case R.id.nav_department_collection:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CollectionFragment()).commit();
                break;
        }

        return true;
    }

}
