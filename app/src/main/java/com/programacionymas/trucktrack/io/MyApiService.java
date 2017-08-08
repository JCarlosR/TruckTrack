package com.programacionymas.trucktrack.io;

import com.programacionymas.trucktrack.io.response.LoginResponse;
import com.programacionymas.trucktrack.io.response.SimpleResponse;
import com.programacionymas.trucktrack.model.Travel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApiService {

    @GET("login")
    Call<LoginResponse> getLogin(
            @Query("email") String email,
            @Query("password") String password
    );

    @GET("travels/today")
    Call<ArrayList<Travel>> getTravelsToday(
            @Query("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("travels/{id}/status")
    Call<SimpleResponse> postTravelStatus(
            @Path("id") int id,
            @Field("status") int status
    );

/*
$call->user_id = $request->input('user_id');
$call->travel_id = $request->input('travel_id');
$call->lat = $request->input('lat');
$call->lng = $request->input('lng');
*/
    @FormUrlEncoded
    @POST("distress-call")
    Call<SimpleResponse> postDistressCall(
            @Field("user_id") int user_id,
            @Field("travel_id") int travel_id,
            @Field("lat") String lat,
            @Field("lng") String lng
    );

}
