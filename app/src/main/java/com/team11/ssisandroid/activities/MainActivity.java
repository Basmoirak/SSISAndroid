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
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.team11.ssisandroid.R;
import com.team11.ssisandroid.fragments.CollectionFragment;
import com.team11.ssisandroid.fragments.RequisitionFragment;
import com.team11.ssisandroid.fragments.RetrievalFragment;
import com.team11.ssisandroid.interfaces.UserClient;
import com.team11.ssisandroid.models.UserRole;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    SharedPreferences pref;
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

        //Get User Department Id and Department Role, and store in shared preferences
        getUserDetails();

        pref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String role = pref.getString("role", null);
        Log.i("role", role + "");
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


    private void getUserDetails(){
        //Store departmentId and user role in shared preferences
        pref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String token = "Bearer " + pref.getString("accessToken", null);
        Call<UserRole> call = userClient.getUserRole(token);
        Log.i("XSADIISA", token);
        call.enqueue(new Callback<UserRole>() {
            @Override
            public void onResponse(Call<UserRole> call, Response<UserRole> response) {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit();
                    editor.putString("departmentId", response.body().getDepartmentId());
                    editor.putString("role", response.body().getRoleName());
                    Log.i("DepartmentId", response.body().getDepartmentId());
                    Log.i("DepartmentRole", response.body().getRoleName());
                }
            }

            @Override
            public void onFailure(Call<UserRole> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Issue with retrieving role", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
