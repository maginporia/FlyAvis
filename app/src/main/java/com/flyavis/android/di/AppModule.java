package com.flyavis.android.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.flyavis.android.Constants;
import com.flyavis.android.FlyAvisApplication;
import com.flyavis.android.data.database.FlyAvisDb;
import com.flyavis.android.data.database.MyTripDao;
import com.flyavis.android.data.database.PlanDao;
import com.flyavis.android.data.network.FlyAvisService;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.flyavis.android.Constants.BASE_URL;

@Module(includes = {ViewModelModule.class})
class AppModule {
    @Provides
    @Singleton
    Context provideContext(FlyAvisApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    FlyAvisDb provideDatabase(Context context) {
        return Room.databaseBuilder(context,
                FlyAvisDb.class, Constants.DB_NAME)
                .build();
    }

    @Provides
    @Singleton
    MyTripDao provideMyTripDao(FlyAvisDb database) {
        return database.myTripDao();
    }

    @Provides
    @Singleton
    PlanDao providePlanningDao(FlyAvisDb database) {
        return database.planningDao();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    FlyAvisService provideFlyAvisService(Retrofit retrofit) {
        return retrofit.create(FlyAvisService.class);
    }

    @Provides
    @Singleton
    Interceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        return client.build();
    }

}
