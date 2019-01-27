package com.flyavis.android.di;

import com.flyavis.android.FlyAvisApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ViewBuilder.class,
        AppModule.class})

public interface AppComponent extends AndroidInjector<FlyAvisApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<FlyAvisApplication> { }
}
