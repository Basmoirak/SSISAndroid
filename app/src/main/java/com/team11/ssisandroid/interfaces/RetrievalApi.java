package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.Retrieval;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrievalApi {

    @POST("api/disbursement/retrieval")
    Call<Retrieval[]> getRetrievals(@Header("Authorization") String authToken);
}