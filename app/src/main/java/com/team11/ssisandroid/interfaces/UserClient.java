package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.UserRole;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @POST("api/users/role")
    Call<UserRole> getUserRole(@Header("Authorization") String authToken, @Body UserRole user);
}
