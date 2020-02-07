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
import com.team11.ssisandroid.interfaces.RequisitionApi;
import com.team11.ssisandroid.models.Requisition;
import com.team11.ssisandroid.models.UserRole;
import com.team11.ssisandroid.util.RequisitionAdapter;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequisitionFragment extends Fragment{

    View rootView;
    private RecyclerView recyclerView;
    private Requisition[] requisitionsArr;

    public interface RequisitionLoadedListener {
        void onDataLoaded(Requisition[] requisitions);
    }

    public RequisitionFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getRequisitionList(new RequisitionLoadedListener() {
            @Override
            public void onDataLoaded(Requisition[] requisitions) {
                int count = requisitions.length;
                requisitionsArr = new Requisition[count];

                for (int i = 0; i < count; i++) {
                    requisitionsArr[i] = requisitions[i];
                }

                // 1. Create adapter after response from server
                RequisitionAdapter mAdapter = new RequisitionAdapter(getContext(), requisitionsArr);

                // 2. Set adapter
                recyclerView.setAdapter(mAdapter);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_requisition, container, false);

        // 1. Get a reference to recycler view
        recyclerView = rootView.findViewById(R.id.recyclerViewRequisition);

        // 2. Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    private void getRequisitionList(final RequisitionLoadedListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RequisitionApi requisitionApi = retrofit.create(RequisitionApi.class);

        // Retrieve authentication token, email and departmentId from shared preferences
        SharedPreferences userDetails = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = userDetails.getString("token", null);
        String email = userDetails.getString("email", null);
        String departmentId = userDetails.getString("departmentId", null);

        // Set up user role
        UserRole userRole = new UserRole(email, null, departmentId);
        Call<Requisition[]> call = requisitionApi.getDepartmentRequisitions(token, userRole);

        call.enqueue(new Callback<Requisition[]>() {
            @Override
            public void onResponse(Call<Requisition[]> call, Response<Requisition[]> response) {
                if(response.isSuccessful()){
                    Requisition[] list = response.body();
                    listener.onDataLoaded(list);
                }
            }

            @Override
            public void onFailure(Call<Requisition[]> call, Throwable t) {

            }
        });
    }

}
