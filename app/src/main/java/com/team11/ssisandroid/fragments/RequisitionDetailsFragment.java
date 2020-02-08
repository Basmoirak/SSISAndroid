package com.team11.ssisandroid.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.team11.ssisandroid.models.RequisitionDetail;
import com.team11.ssisandroid.util.RequisitionDetailsAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequisitionDetailsFragment extends Fragment {

    View rootView;
    private RecyclerView recyclerView;
    private RequisitionDetail[] requisitionDetailsArr;
    String requisitionId;
    String token;
    String role;
    String departmentId;

    public interface RequisitionDetailsLoadListener {
        void onDataLoaded(RequisitionDetail[] requisitionDetails);
    }

    public RequisitionDetailsFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences userDetails = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        token = userDetails.getString("token", null);
        role = userDetails.getString("role", null);
        departmentId = userDetails.getString("departmentId", null);

        getRequisitionDetailsList(new RequisitionDetailsLoadListener() {
            @Override
            public void onDataLoaded(RequisitionDetail[] requisitionDetails) {
                int count = requisitionDetails.length;
                requisitionDetailsArr = new RequisitionDetail[count];

                for(int i = 0; i < count; i++){
                    requisitionDetailsArr[i] = requisitionDetails[i];
                }

                // 1. Create adapter after response from server
                RequisitionDetailsAdapter mAdapter = new RequisitionDetailsAdapter(getContext(), requisitionDetailsArr, role, requisitionId, departmentId, token);

                // 2. Set adapter
                recyclerView.setAdapter(mAdapter);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_requisition_details, container, false);

        // 1. Get a reference to recycler view
        recyclerView = rootView.findViewById(R.id.recyclerViewRequisitionDetail);

        // 2. Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    private void getRequisitionDetailsList(final RequisitionDetailsLoadListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RequisitionApi requisitionApi = retrofit.create(RequisitionApi.class);

        // Retrieve authentication token, email and departmentId from shared preferences
        SharedPreferences userDetails = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = userDetails.getString("token", null);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            requisitionId = bundle.getString("requisitionId");
        }

        //Set up Requisition class
        Requisition requisition = new Requisition(requisitionId, departmentId, null, null, null);
        Call<RequisitionDetail[]> call = requisitionApi.getRequisitionDetails(token, requisition);

        call.enqueue(new Callback<RequisitionDetail[]>() {
            @Override
            public void onResponse(Call<RequisitionDetail[]> call, Response<RequisitionDetail[]> response) {
                if(response.isSuccessful()){
                    RequisitionDetail[] list = response.body();
                    listener.onDataLoaded(list);
                }
            }

            @Override
            public void onFailure(Call<RequisitionDetail[]> call, Throwable t) {

            }
        });
    }
}
