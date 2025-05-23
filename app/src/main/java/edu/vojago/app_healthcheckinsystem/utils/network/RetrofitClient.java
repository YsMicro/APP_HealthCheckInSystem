package edu.vojago.app_healthcheckinsystem.utils.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // 修改为实际后端地址（示例）
    private static final String BASE_URL = "http://8.140.238.228:8080";
    //    private static final String BASE_URL = "http://192.168.38.238:8080";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}