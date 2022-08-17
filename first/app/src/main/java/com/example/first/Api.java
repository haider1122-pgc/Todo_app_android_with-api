package com.example.first;

import com.example.first.ModelResponse.DeleteTaskResponse;
import com.example.first.ModelResponse.LogoutResponse;
import com.example.first.ModelResponse.RegisterResponse;
import com.example.first.ModelResponse.TaskResponse;
import com.example.first.model.personModel;
import com.example.first.model.todoModel;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @POST("user/register")
    Call<RegisterResponse> register(
            @Header ("Content-Type")String header,
            @Body personModel person
    );

    @POST("user/login")
    Call<RegisterResponse> login(
            @Header ("Content-Type")String header,
            @Body personModel person
    );

    @POST("user/logout")
    Call<LogoutResponse> logout(
            @Header ("Authorization")String header

    );

    @POST("task")
    Call<TaskResponse> addTask(
            @HeaderMap Map<String,String> headers,
          //  @Header ("Authorization")String header,
            //@Header ("Content-Type")String header1,
            @Body todoModel model

    );
    @PUT("task/{id}")
    Call<TaskResponse> updateTask(
            @Path("id") String id,
            @HeaderMap Map<String,String> headers,
            @Body todoModel model

    );
    @DELETE("task/{id}")
    Call<DeleteTaskResponse> deleteTask(
            @Path("id") String id,
            @HeaderMap Map<String,String> headers

    );


}
