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
import com.team11.ssisandroid.models.Collection;
import com.team11.ssisandroid.models.Requisition;
import com.team11.ssisandroid.models.UserRole;
import com.team11.ssisandroid.util.CollectionAdapter;
import com.team11.ssisandroid.util.RequisitionAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectionFragment extends Fragment {

    View rootView;
    private RecyclerView recyclerView;

    public interface CollectionLoadedListener {
        void onDataLoaded(Collection collection);
    }

    public CollectionFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getCollection(new CollectionLoadedListener() {
            @Override
            public void onDataLoaded(Collection collection) {
                // 1. Create adapter after response from server
                CollectionAdapter mAdapter = new CollectionAdapter(getContext(), collection);

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
        String token = userDetails.getString("token", null);
        String email = userDetails.getString("email", null);
        String departmentId = userDetails.getString("departmentId", null);

        // Set up user role
        UserRole userRole = new UserRole(email, null, departmentId);
        Call<Collection> call = collectionApi.getDepartmentCollection(token, userRole);

        call.enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if(response.isSuccessful()){
                    Collection mCollection = response.body();
                    listener.onDataLoaded(mCollection);
                }
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {

            }
        });
    }
}
