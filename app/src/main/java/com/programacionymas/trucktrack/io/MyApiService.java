package com.programacionymas.trucktrack.io;

import com.programacionymas.trucktrack.io.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApiService {

    @GET("login")
    Call<LoginResponse> getLogin(
            @Query("email") String email,
            @Query("password") String password
    );

    /*@FormUrlEncoded
    @POST("upload/photo")
    Call<SimpleResponse> postPhoto(
            @Field("image") String base64,
            @Field("extension") String extension,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("product")
    Call<SimpleResponse> postNewProduct(
            @Field("code") String code,
            @Field("name") String name,
            @Field("description") String description
    );*/

}
