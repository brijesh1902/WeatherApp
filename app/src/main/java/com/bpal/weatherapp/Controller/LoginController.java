package com.bpal.weatherapp.Controller;

import com.bpal.weatherapp.Model.User;
import com.bpal.weatherapp.Viewer.ILoginView;

public class LoginController implements ILoginController {

    ILoginView loginView;

    public LoginController(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void OnLogin(String username, String password) {
        User user = new User(username, password);

        int code = user.isValid();

        if(code == 0) {
            loginView.OnLoginError("Please enter Username!");
        }
        else  if (code == 1)
        {
            loginView.OnLoginError("Please enter Password!");
        }
        else  if(code == 2){
            loginView.OnLoginError("Please enter Password greater the 6 char.!");
        }
        else  if (code == 3)
        {
            loginView.OnLoginError("Username is invalid!");
        }
        else  if(code == 4){
            loginView.OnLoginError("Password is invalid!");
        }
        else {
            loginView.OnLoginSuccess("Login Successful.");
        }
    }

}
