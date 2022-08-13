package com.example.first;

import com.example.first.ModelResponse.RegisterResponse;
import com.example.first.model.personModel;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @POST("register")
    Call<RegisterResponse> register(
          /*  @Field("name") String personName,
            @Field("email") String personEmail,
            @Field("password") String personPassword,
            @Field("age") String personAge*/
            @Header ("Content-Type")String header,
            @Body personModel person
    );
    @POST("login")
    Call<RegisterResponse> login(
            @Header ("Content-Type")String header,
            @Body personModel person
    );
}
