package services;


import dto.LoginRequest;
import models.User;

import javax.inject.Singleton;

@Singleton
public class Authenticator {
    public User login(LoginRequest req){
       return User.find.where().eq("username", req.username)
               .eq("password", req.password).findUnique();
    }
}
