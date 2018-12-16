package controllers;

import dto.LoginRequest;
import models.User;
import play.api.mvc.Session;
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
    private Authenticator auth;

    public Result login(){
        Form<User> userForm = formFactory.form(User.class);
        return ok(login.render(userForm));
    }

    public Result doLogin(){
        LoginRequest req = formFactory.form(LoginRequest.class).bindFromRequest().get();
        User user = auth.login(req);

        if(user == null){
            flash("danger", "ログインに失敗しました");
            return redirect(routes.LoginController.login());
        }

        setSession(user);
        flash("success", "ログインに成功しました");
        return redirect(routes.UsersController.index());
    }

    public  Result logout(){
        clearSession();
        return redirect(routes.UsersController.index());
    }

    private void setSession(User user){
        session("userID", user.userID);
        session("password", user.password);
        session("id", user.id.toString());
    }

    private void clearSession(){
        session().clear();
    }
}