package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.Delegation;
import com.team11.ssisandroid.models.UserRole;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DelegationApi {

    @POST("api/department/retrievedelegation")
    Call<Delegation[]> getDelegationUsers(@Header("Authorization") String authToken, @Body UserRole user);
}
