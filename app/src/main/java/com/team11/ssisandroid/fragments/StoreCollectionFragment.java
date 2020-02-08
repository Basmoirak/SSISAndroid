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
import com.team11.ssisandroid.models.StoreCollection;
import com.team11.ssisandroid.util.StoreCollectionAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreCollectionFragment extends Fragment {
    View rootView;
    private RecyclerView recyclerView;

    public interface StoreCollectionLoadedListener {
        void onDataLoaded(StoreCollection storeCollection);
    }

    public StoreCollectionFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getStoreCollection(new StoreCollectionLoadedListener() {
            @Override
            public void onDataLoaded(StoreCollection storeCollection) {
                // 1. Create adapter after response from the server
                StoreCollectionAdapter mAdapter = new StoreCollectionAdapter(getContext(), storeCollection);

                // 2. Set adapter
                recyclerView.setAdapter(mAdapter);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_store_collection, container, false);

        // 1. Get reference to recycler view
        recyclerView = rootView.findViewById(R.id.recyclerViewStoreCollection);

        // 2. Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    private void getStoreCollection(final StoreCollectionLoadedListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        CollectionApi collectionApi = retrofit.create(CollectionApi.class);

        // Retrieve authentication token from shared preferences
        SharedPreferences userDetails = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = userDetails.getString("token", null);

        //Set up data retrieval
        Call<StoreCollection> call = collectionApi.getStoreCollection(token);

        call.enqueue(new Callback<StoreCollection>() {
            @Override
            public void onResponse(Call<StoreCollection> call, Response<StoreCollection> response) {
                //Once server provides response, callback to populate recycler view with data
                if(response.isSuccessful()){
                    StoreCollection mStoreCollection = response.body();
                    listener.onDataLoaded(mStoreCollection);
                }
            }

            @Override
            public void onFailure(Call<StoreCollection> call, Throwable t) {

            }
        });
    }
}
