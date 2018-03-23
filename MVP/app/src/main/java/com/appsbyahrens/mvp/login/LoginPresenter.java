package com.appsbyahrens.mvp.login;

import android.support.annotation.Nullable;

/**
 * Created by adamahrens on 3/22/18.
 */

public class LoginPresenter implements LoginMVP.Presenter {

    // Helps to check for null references
    @Nullable
    private LoginMVP.View view;
    private LoginMVP.Model model;

    public LoginPresenter(LoginMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClicked() {
        if (view != null) {
            if ()
        }
    }

    @Override
    public void getCurrentUser() {

    }
}
