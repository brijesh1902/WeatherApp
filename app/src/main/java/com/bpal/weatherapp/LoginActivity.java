package com.bpal.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bpal.weatherapp.Controller.ILoginController;
import com.bpal.weatherapp.Controller.LoginController;
import com.bpal.weatherapp.Viewer.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    ILoginController iLoginController;
    EditText username, password;
    Button btnlogin;
    ImageView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btn);
        show = findViewById(R.id.show);

        iLoginController = new LoginController(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = username.getText().toString();
                String Password = password.getText().toString();

                iLoginController.OnLogin(Username, Password);

            }
        });

    }

    @Override
    public void OnLoginSuccess(String message) {
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void OnLoginError(String message) {
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }

    public void showpassword(View view) {
        if (view.getId()==R.id.show) {
            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                show.setImageResource(R.drawable.vispass);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                show.setImageResource(R.drawable.showpass);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}