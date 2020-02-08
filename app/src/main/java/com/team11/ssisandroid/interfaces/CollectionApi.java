package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.DepartmentCollection;
import com.team11.ssisandroid.models.StoreCollection;
import com.team11.ssisandroid.models.UserRole;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CollectionApi {

    @POST("api/department/collection")
    Call<DepartmentCollection> getDepartmentCollection(@Header("Authorization") String authToken, @Body UserRole user);

    @POST("api/department/confirmcollection")
    Call<ResponseBody> confirmDepartmentCollection(@Header("Authorization") String authToken, @Body UserRole user);

    @POST("api/disbursement/collection")
    Call<StoreCollection> getStoreCollection(@Header("Authorization") String authToken);

}
