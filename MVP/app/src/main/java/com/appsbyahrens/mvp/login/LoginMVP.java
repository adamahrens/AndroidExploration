package com.appsbyahrens.mvp.login;

/**
 * Created by adamahrens on 3/22/18.
 */

public interface LoginMVP {

    interface View {

        String getFirstName();
        String getLastName();

        void showUserNotAvailable();
        void showInputError();
        void showUserSavedMessage();

        void setFirstName(String firstName);
        void setLastName(String lastName);
    }

    interface Presenter {
        void setView(LoginMVP.View view);
        void loginButtonClicked();
        void getCurrentUser();
    }

    interface Model {

        void createUser(String firstName, String lastName);
        User getUser();
    }
}
