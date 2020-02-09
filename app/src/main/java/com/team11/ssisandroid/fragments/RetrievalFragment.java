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
import com.team11.ssisandroid.interfaces.RetrievalApi;
import com.team11.ssisandroid.models.Retrieval;
import com.team11.ssisandroid.util.RetrievalAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrievalFragment extends Fragment {

    View rootView;
    private RecyclerView recyclerView;
    private String token;
    private String email;

    public interface RetrievalLoadedListener {
        void onDataLoaded(Retrieval[] retrievals);
    }

    public RetrievalFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve authentication token, email and role from shared preferences
        SharedPreferences userDetails = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        token = userDetails.getString("token", null);
        email = userDetails.getString("email", null);

        getRetrievals(new RetrievalLoadedListener() {
            @Override
            public void onDataLoaded(Retrieval[] retrievals) {
                // 1. Create adapter after response from server
                RetrievalAdapter mAdapter = new RetrievalAdapter(getContext(), retrievals, "3", token);

                // 2. Set adapter
                recyclerView.setAdapter(mAdapter);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_retrieval, container, false);

        // 1. Get a reference to recycler view
        recyclerView = rootView.findViewById(R.id.recyclerViewRetrieval);

        // 2. Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    private void getRetrievals(final RetrievalLoadedListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RetrievalApi retrievalApi = retrofit.create(RetrievalApi.class);

        Call<Retrieval[]> call = retrievalApi.getRetrievals(token);

        call.enqueue(new Callback<Retrieval[]>() {
            @Override
            public void onResponse(Call<Retrieval[]> call, Response<Retrieval[]> response) {
                if(response.isSuccessful()){
                    Retrieval[] retrievals = response.body();
                    listener.onDataLoaded(retrievals);
                }
            }

            @Override
            public void onFailure(Call<Retrieval[]> call, Throwable t) {

            }
        });
    }
}
