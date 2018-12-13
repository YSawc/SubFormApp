package controllers;

import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.logins.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoginController extends Controller {

    @Inject
    private FormFactory formFactory;

    public Result login(){
        Form<User> userForm = formFactory.form(User.class);
        return ok(login.render(userForm));
    }

    public Result doLogin(){
        return TODO;
    }

    public  Result logout(){
        return TODO;
    }
}
