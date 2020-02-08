package com.team11.ssisandroid.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.interfaces.CollectionApi;
import com.team11.ssisandroid.models.DepartmentCollection;
import com.team11.ssisandroid.models.UserRole;
import com.team11.ssisandroid.util.CollectionAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectionFragment extends Fragment {

    View rootView;
    private RecyclerView recyclerView;
    private String token;
    private String email;
    private String role;
    private String departmentId;

    public interface CollectionLoadedListener {
        void onDataLoaded(DepartmentCollection departmentCollection);
    }

    public CollectionFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve authentication token, email and role from shared preferences
        SharedPreferences userDetails = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        token = userDetails.getString("token", null);
        email = userDetails.getString("email", null);
        role = userDetails.getString("role", null);
//        departmentId = userDetails.getString("departmentId", null);

        getCollection(new CollectionLoadedListener() {
            @Override
            public void onDataLoaded(DepartmentCollection departmentCollection) {
                // 1. Create adapter after response from server
                CollectionAdapter mAdapter = new CollectionAdapter(getContext(), departmentCollection, role, departmentId, token);

                // 2. Set adapter
                recyclerView.setAdapter(mAdapter);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_collection, container, false);

        // 1. Get a reference to recycler view
        recyclerView = rootView.findViewById(R.id.recyclerViewDepartmentCollection);

        // 2. Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    private void getCollection(final CollectionLoadedListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        CollectionApi collectionApi = retrofit.create(CollectionApi.class);

        // Retrieve authentication token, email and departmentId from shared preferences
        SharedPreferences userDetails = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        departmentId = null;

        //If is Employee, take the current User's departmentId
        if(role.contains("Employee")){
            departmentId = userDetails.getString("departmentId", null);

        //If is Store Clerk, take the departmentId provided from StoreCollection fragment
        } else if(role.contains("StoreClerk")) {
            Bundle bundle = this.getArguments();
            if(bundle != null){
                departmentId = bundle.getString("departmentId");
            }
        }

        // Set up user role
        UserRole userRole = new UserRole(email, null, departmentId);
        Call<DepartmentCollection> call = collectionApi.getDepartmentCollection(token, userRole);

        call.enqueue(new Callback<DepartmentCollection>() {
            @Override
            public void onResponse(Call<DepartmentCollection> call, Response<DepartmentCollection> response) {
                if(response.isSuccessful()){
                    DepartmentCollection mDepartmentCollection = response.body();
                    listener.onDataLoaded(mDepartmentCollection);
                }
            }

            @Override
            public void onFailure(Call<DepartmentCollection> call, Throwable t) {

            }
        });
    }
}
