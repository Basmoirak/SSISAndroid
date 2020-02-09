package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.Retrieval;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrievalApi {

    @POST("api/disbursement/retrieval")
    Call<Retrieval[]> getRetrievals(@Header("Authorization") String authToken);

    @POST("api/disbursement/confirmretrieval")
    Call<ResponseBody> confirmRetrievals(@Header("Authorization") String authToken);
}