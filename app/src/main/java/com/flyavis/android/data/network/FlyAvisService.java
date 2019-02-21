package com.flyavis.android.data.network;

import com.flyavis.android.data.database.MyTrip;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FlyAvisService {
    @POST("mytrip")
    Single<Response<MyTrip>> fetchMyTrips(@Query("user") String query);

    @POST("FlyAvis/editData/createTravel")
    Completable insertMyTrips(@Body MyTrip body);

//    @POST("plan")
//    Single<Response<Plan>> fetchPlan(@Query("user") String query);
}
