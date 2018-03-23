package com.appsbyahrens.mvp.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appsbyahrens.mvp.App;
import com.appsbyahrens.mvp.R;

import javax.inject.Inject;

public class Login extends AppCompatActivity implements LoginMVP.View {

    @Inject
    LoginMVP.Presenter presenter;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((App)getApplication()).getComponent().inject(this);

        firstNameEditText = findViewById(R.id.loginActivity_firstName_editText);
        lastNameEditText = findViewById(R.id.loginActivity_lastName_editText);
        loginButton = findViewById(R.id.loginActivity_login_button);

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @Override
    public String getFirstName() {
        return firstNameEditText.getText().toString();
    }

    @Override
    public String getLastName() {
        return lastNameEditText.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this, "User not available", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "First & Last Cant Be Empty", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserSavedMessage() {
        Toast.makeText(this, "User saved!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setFirstName(String firstName) {
        firstNameEditText.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        lastNameEditText.setText(lastName);

    }
}
