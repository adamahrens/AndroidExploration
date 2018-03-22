package com.appsbyahrens.mvp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by adamahrens on 3/22/18.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MainActivity target);
}
