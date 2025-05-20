package edu.vojago.app_healthcheckinsystem.data.api;

import edu.vojago.app_healthcheckinsystem.ui.auth.LoginActivity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/user/login")
    Call<LoginActivity.LoginResponse> login(@Body LoginActivity.LoginRequest loginRequest);
}