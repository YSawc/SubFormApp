package services;

import models.User;

import javax.inject.Singleton;

@Singleton
public class FindUser {
    public User findUser(User user){
        return User.find.where().eq("id",user.id ).findUnique();
    }
}
