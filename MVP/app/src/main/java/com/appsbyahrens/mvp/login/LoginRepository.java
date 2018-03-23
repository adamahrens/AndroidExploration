package com.appsbyahrens.mvp.login;

/**
 * Created by adamahrens on 3/22/18.
 */

public interface LoginRepository {
    User getUser();
    void saveUser();
}
