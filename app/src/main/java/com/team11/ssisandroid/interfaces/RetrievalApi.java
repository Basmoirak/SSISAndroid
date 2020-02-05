package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.activities.StationeryRetrievalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface RetrievalApi {

    String BASE_URL = "https://team11ssis.azurewebsites.net/";

    @POST("api/disbursement/retrieval")
    Call<List<StationeryRetrievalModel>> getStationery();
}