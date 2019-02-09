package com.flyavis.android.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.flyavis.android.Constants;
import com.flyavis.android.FlyAvisApplication;
import com.flyavis.android.data.database.FlyAvisDb;
import com.flyavis.android.data.database.MyTripDao;
import com.flyavis.android.data.database.PlanDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ViewModelModule.class})
class AppModule {
    @Provides
    @Singleton
    Context provideContext(FlyAvisApplication application) {
        return application.getApplicationContext();
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

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
