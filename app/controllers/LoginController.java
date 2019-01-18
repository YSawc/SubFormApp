package controllers;

import dto.LoginRequest;
import models.Tweet;
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
import java.util.ArrayList;
import java.util.List;

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
        flash("info", "ログインに成功しました");
        return redirect(routes.TweetsController.page(0));
    }

    public  Result logout(){
        clearSession();
        flash("info", "ログアウトしました");
        return redirect(routes.LoginController.login());
    }

    private void setSession(User user){
        //ログインと同時にセッションをセット
        session("userID", user.userID);
        session("password", user.password);
        session("id", user.id.toString());
        session("name", user.name);
        if(user.private_or){
            session("private_or", "1");
        }else{
            session("private_or", "0");
        }


    }

    private void clearSession(){
        session().clear();
    }
}