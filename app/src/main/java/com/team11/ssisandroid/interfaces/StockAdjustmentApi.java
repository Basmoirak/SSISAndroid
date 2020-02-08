package com.team11.ssisandroid.interfaces;

import com.team11.ssisandroid.models.StockAdjustment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StockAdjustmentApi {

    @POST("api/stockadjustments/create")
    Call<ResponseBody> createStockAdjustment(@Header("Authorization") String authToken, @Body StockAdjustment stockAdjustment);
}
