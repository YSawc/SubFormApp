package controllers;

import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.users.*;
import views.html.errors.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UsersController extends Controller {
    @Inject
    private FormFactory formFactory;

    public Result index(){
        List<User> users = User.find.all();
        return ok(index.render(users));
    }

    public Result create(){
        Form<User> userForm = formFactory.form(User.class);
        return ok(create.render(userForm));
    }

    public Result save(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if(userForm.hasErrors()){
            System.out.println(userForm);
            flash("danger", "正しい値を入力し直してください");
            return badRequest(create.render(userForm));
        }
        User user = userForm.get();
        user.save();
        return redirect(routes.UsersController.index());
    }

    public Result edit(Integer id){
        User user = User.find.byId(id);
        if(user == null){
            return notFound(_404.render());
        }
        Form<User> userForm = formFactory.form(User.class).fill(user);
        return ok(edit.render(userForm));
    }

    public Result update(){
        User user = formFactory.form(User.class).bindFromRequest().get();
        User oldUser = User.find.byId(user.id);
        if(oldUser==null){
            flash("danger", "ユーザーが見つかりません");
            return badRequest();
        }

        oldUser.name = user.name;
        oldUser.username = user.username;
        oldUser.password = user.password;
        oldUser.email = user.email;
        oldUser.update();

        flash("ユーザー情報を更新しました");
        return redirect(routes.UsersController.index());
    }

    public Result show(Integer id){

        User user = User.find.byId(id);
        if(user == null){
            return notFound("ユーザーが見つかりません");
        }
//        return ok(show.render(user));

        System.out.println(user.id.getClass() + "クリックしたユーザーのIDのクラス");
        System.out.println(session("id").getClass() + "セッションIDのクラス" );

        //ユーザーのidと、ログインのidが同じかどうかでアクションを変える
        if(session("id").equals(user.id.toString())){
            return ok(show.render(user));
        }else{
            return TODO;
        }
    }

    public Result destroy(Integer id){

        User user = User.find.byId(id);
        if(user == null){
            return notFound("ユーザーが見つかりません");
        }
        return redirect(routes.UsersController.index());
    }
}
