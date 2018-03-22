package com.appsbyahrens.mvp;

import android.app.Application;

import dagger.android.DaggerApplication;

/**
 * Created by adamahrens on 3/22/18.
 */

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
