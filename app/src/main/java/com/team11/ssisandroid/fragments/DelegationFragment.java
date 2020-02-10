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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.team11.ssisandroid.R;
import com.team11.ssisandroid.interfaces.DelegationApi;
import com.team11.ssisandroid.models.Delegation;
import com.team11.ssisandroid.models.UserRole;
import com.team11.ssisandroid.util.DelegationAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DelegationFragment extends Fragment {

    View rootView;
    private RecyclerView recyclerView;
    private Delegation[] delegations;

    public interface DelegationLoadedListener {
        void onDataLoaded(Delegation[] delegations);
    }

    public DelegationFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDelegationUsers(new DelegationLoadedListener() {
            @Override
            public void onDataLoaded(Delegation[] delegations) {

                // 1. Create adapter after response from server
                DelegationAdapter mAdapter = new DelegationAdapter(getContext(), delegations);

                // 2. Set adapter
                recyclerView.setAdapter(mAdapter);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_delegation, container, false);

        // 1. Get a reference to recycler view
        recyclerView = rootView.findViewById(R.id.recyclerViewDelegation);

        // 2. Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;

    }

    private void getDelegationUsers(final DelegationLoadedListener listener){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        DelegationApi delegationApi = retrofit.create(DelegationApi.class);

        // Retrieve authentication token, email and departmentId from shared preferences
        SharedPreferences userDetails = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = userDetails.getString("token", null);
        String departmentId = userDetails.getString("departmentId", null);

        //Set up user role
        UserRole userRole = new UserRole(null, null, departmentId);
        Call<Delegation[]> call = delegationApi.getDelegationUsers(token, userRole);

        call.enqueue(new Callback<Delegation[]>() {
            @Override
            public void onResponse(Call<Delegation[]> call, Response<Delegation[]> response) {
                if(response.isSuccessful()){
                    Delegation[] list = response.body();
                    listener.onDataLoaded(list);
                }
            }

            @Override
            public void onFailure(Call<Delegation[]> call, Throwable t) {

            }
        });
    }
}
