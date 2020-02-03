package com.team11.ssisandroid.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.team11.ssisandroid.R;
import com.team11.ssisandroid.fragments.CollectionFragment;
import com.team11.ssisandroid.fragments.RequisitionFragment;
import com.team11.ssisandroid.fragments.RetrievalFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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

    //Based on item selected in menu, go to new fragment/page
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_requisition:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RequisitionFragment()).commit();
                break;
            case R.id.nav_retrieval:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RetrievalFragment()).commit();
                break;
            case R.id.nav_collection:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CollectionFragment()).commit();
                break;
        }

        return true;
    }
}
