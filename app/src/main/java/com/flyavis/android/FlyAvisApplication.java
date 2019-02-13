package com.flyavis.android;

import com.flyavis.android.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/*
 *For Dagger2(di)
*
*set manifests   ↓
*/
public class FlyAvisApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
