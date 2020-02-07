package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.Collection;
import com.team11.ssisandroid.models.UserRole;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CollectionApi {

    @POST("api/department/collection")
    Call<Collection> getDepartmentCollection(@Header("Authorization") String authToken, @Body UserRole user);
}
