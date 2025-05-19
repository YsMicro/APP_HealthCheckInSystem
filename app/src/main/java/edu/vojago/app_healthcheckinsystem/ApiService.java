package edu.vojago.app_healthcheckinsystem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/user/login")
    Call<LoginActivity.LoginResponse> login(@Body LoginActivity.LoginRequest loginRequest);
}