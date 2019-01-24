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
        if(session("id") != null){
            return  redirect(routes.TweetsController.page(0));
        }
        Form<User> userForm = formFactory.form(User.class);
        ArrayList<String> str_array = new ArrayList<String>();
        return ok(login.render(str_array, userForm));
    }

    public Result doLogin(){
        LoginRequest req = formFactory.form(LoginRequest.class).bindFromRequest().get();
        User user = auth.login(req);
        List<String> str_array = new ArrayList<String>();

        if(formFactory.form().bindFromRequest().get("data[userID]").isEmpty()){
            str_array.add("ユーザーIDを入力してください");
        }else if(formFactory.form().bindFromRequest().get("data[userID]").length() < 4 || 20 < formFactory.form().bindFromRequest().get("data[userID]").length() ){
            str_array.add("ユーザー名は4文字〜20文字で入力してください");
        }

        if(! (formFactory.form().bindFromRequest().get("data[userID]").matches("^[a-zA-Z0-9_-]*?$")) ){
            str_array.add("ユーザー名は a-zA-Z0-9_- で入力してください");
        }

        if(formFactory.form().bindFromRequest().get("data[password]").isEmpty()){
            str_array.add("パスワードを入力してください");
        }else if(formFactory.form().bindFromRequest().get("data[password]").length() < 4 || 8 < formFactory.form().bindFromRequest().get("data[password]").length() ){
            str_array.add("パズワードは4文字〜8文字で入力してください");
        }

        if(! (formFactory.form().bindFromRequest().get("data[password]").matches("^[a-zA-Z0-9]*?$")) ){
            str_array.add("パスワードは a-zA-Z0-9 で入力してください");
        }

        Form<User> userForm = formFactory.form(User.class);

        if(user == null){
            flash("danger", "ユーザー名、パスワードの組み合わせが違います");
            return ok(login.render(str_array, userForm));
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