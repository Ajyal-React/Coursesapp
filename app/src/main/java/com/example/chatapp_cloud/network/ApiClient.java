package com.example.chatapp_cloud.network;


import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

   private static Retrofit retrofit = null;
   public static Retrofit getClient(){
       if (retrofit == null){
           retrofit = new Retrofit.Builder()
                //   .baseUrl( "https://www.googleapis.com/auth/cloud-platform" )
                   .addConverterFactory( ScalarsConverterFactory.create() )
                   .build();

       }
       return retrofit;
   }


}
