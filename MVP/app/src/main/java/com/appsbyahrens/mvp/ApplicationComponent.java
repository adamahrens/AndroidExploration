package com.appsbyahrens.mvp;

import com.appsbyahrens.mvp.login.Login;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by adamahrens on 3/22/18.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(Login target);
}
