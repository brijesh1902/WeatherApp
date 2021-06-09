package com.bpal.weatherapp.Model;

import android.text.TextUtils;

public class User implements IUser{

    private  String username, password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int isValid() {
        if(TextUtils.isEmpty(getUsername()))
            return  0;
        else if(TextUtils.isEmpty(getPassword()))
            return 1;
        else if(getPassword().length()<=6)
            return 2;
        else if(!getUsername().equals("test@1"))
            return 3;
        else if(!getPassword().equals("password"))
            return 4;
        else
            return -1;
    }
}
