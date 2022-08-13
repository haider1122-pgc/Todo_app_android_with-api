package com.example.first;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private  static final String  BASE_URL= "https://api-nodejs-todolist.herokuapp.com/user/";
    private  static  RetrofitClient retrofitClient;
    private  static Retrofit retrofit;

    private RetrofitClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //checking if the instance of retrofit is not created then create it
    public static  synchronized RetrofitClient getInstance(){
        if(retrofitClient==null){
            retrofitClient=new RetrofitClient();
        }
        return  retrofitClient;
    }

    public  Api getApi(){
        return  retrofit.create(Api.class);
    }
}
