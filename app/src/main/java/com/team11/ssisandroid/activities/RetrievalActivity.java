package com.team11.ssisandroid.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.interfaces.RetrievalApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrievalActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationery_retrieval);

        listView = (ListView) findViewById(R.id.listViewStationeries);

        getStationery();
    }
    private void getStationery() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrievalApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        RetrievalApi retrievalApi = retrofit.create(RetrievalApi.class);

        Call<List<StationeryRetrievalModel>> call = retrievalApi.getStationery();

        call.enqueue(new Callback<List<StationeryRetrievalModel>>() {
            @Override
            public void onResponse(Call<List<StationeryRetrievalModel>> call, Response<List<StationeryRetrievalModel>> response) {
                List<StationeryRetrievalModel> stationeryRetrievalModelList = response.body();

                //Creating an String array for the ListView
                String[] stationeries = new String[stationeryRetrievalModelList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < stationeryRetrievalModelList.size(); i++) {
                    stationeries[i] = stationeryRetrievalModelList.get(i).getItemDescription();
                }

                //displaying the string array into listview
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stationeries));

            }

            @Override
            public void onFailure(Call<List<StationeryRetrievalModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

