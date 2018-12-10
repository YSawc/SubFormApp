package controllers;

import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.users.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserController extends Controller {
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
        User user = userForm.get();
        user.save();
        return redirect(routes.UserController.index());
    }

    public Result edit(Integer id){
        User user = User.find.byId(id);
        if(user == null){
            return notFound("ユーザーが見つかりません");
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
        oldUser.update();

        flash("ユーザー情報を更新しました");
        return redirect(routes.UserController.index());
    }

    public Result show(Integer id){

        User user = User.find.byId(id);
        if(user == null){
            return notFound("ユーザーが見つかりません");
        }
        return ok(show.render(user));
    }

    public Result destroy(Integer id){

        User user = User.find.byId(id);
        if(user == null){
            return notFound("ユーザーが見つかりません");
        }
        return redirect(routes.UserController.index());
    }
}
