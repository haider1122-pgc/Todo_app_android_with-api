package com.example.first;

import com.example.first.ModelResponse.LogoutResponse;
import com.example.first.ModelResponse.RegisterResponse;
import com.example.first.model.personModel;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @POST("register")
    Call<RegisterResponse> register(
            @Header ("Content-Type")String header,
            @Body personModel person
    );

    @POST("login")
    Call<RegisterResponse> login(
            @Header ("Content-Type")String header,
            @Body personModel person
    );

    @POST("logout")
    Call<LogoutResponse> logout(
            @Header ("Authorization")String header

    );
}
