package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.Requisition;
import com.team11.ssisandroid.models.RequisitionDetail;
import com.team11.ssisandroid.models.UserRole;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RequisitionApi {

    @POST("api/department/requisition")
    Call<Requisition[]> getDepartmentRequisitions(@Header("Authorization") String authToken, @Body UserRole user);

    @POST("api/department/requisitiondetails")
    Call<RequisitionDetail[]> getRequisitionDetails(@Header("Authorization") String authToken, @Body Requisition requisition);

    @POST("api/department/approverequisition")
    Call<ResponseBody> approveRequisition(@Header("Authorization") String authToken, @Body Requisition requisition);

    @POST("api/department/rejectrequisition")
    Call<ResponseBody> rejectRequisition(@Header("Authorization") String authToken, @Body Requisition requisition);
}
