package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.UserRole;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserClient {

    @GET("users/role")
    Call<UserRole> getUserRole(@Header("Authorization") String authToken);
}
