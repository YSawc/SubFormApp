package controllers;

import dto.LoginRequest;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.Authenticator;
import views.html.index;
import views.html.logins.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoginController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    Authenticator auth;

    public Result login(){
        Form<User> userForm = formFactory.form(User.class);
        return ok(login.render(userForm));
    }

    public Result doLogin(){
        LoginRequest req = formFactory.form(LoginRequest.class).bindFromRequest().get();
        User user = auth.login(req);

        if(user == null){
            return redirect(routes.LoginController.login());
        }

        session("uesrname", user.username);

        //デバグ用
        System.out.println(user.username + "  バインドリクエストの情報 user.username");
        System.out.println(user.password + "  バインドリクエストの情報 user.passoword");
        System.out.println(user.id + "  バインドリクエストの情報 user.id");

        setSession(user);
        return redirect(routes.UsersController.index());
    }

    public  Result logout(){
        clearSession();
        return redirect(routes.UsersController.index());
    }

    private void setSession(User user){
        session("uesrname", user.username);
        session("password", user.password);
    }

    private void clearSession(){
        session().clear();
    }
}