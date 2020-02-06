package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.Requisition;
import com.team11.ssisandroid.models.UserRole;
import com.team11.ssisandroid.util.RequisitionArrayResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RequisitionApi {

    @POST("api/department/requisition")
    Call<Requisition[]> getDepartmentRequisitions(@Header("Authorization") String authToken, @Body UserRole user);
}
