package controllers;

import com.avaje.ebean.Ebean;
import models.Tweet;
import models.User;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.users.*;
import views.html.errors.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class UsersController extends Controller {
    @Inject
    private FormFactory formFactory;

//    private Integer pubInt = 0;

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

        //フォームエラーチェック
        if(userForm.hasErrors()){
//            System.out.println(userForm);
            flash("danger", "正しい値を入力し直してください");
            return badRequest(create.render(userForm));
        }

        System.out.println(formFactory.form().bindFromRequest().get("password_confirm").equals(
                (formFactory.form().bindFromRequest().get("password"))) + "同値かチェック");

        //確認用パスワードの不一致
        if(! (formFactory.form().bindFromRequest().get("password_confirm").equals
                (formFactory.form().bindFromRequest().get("password"))) ){
            flash("danger", "確認用パスワードが一致しません");
            return badRequest(create.render(userForm));
        }

        //ユーザーidが被りあればフラッシュを出力
//        String sql = "SELECT user_id FROM user WHERE user_id="
//                + userForm.get().userID;
//        if(Ebean.createSqlQuery(sql).findList().size() !=0){
//
//            flash("danger", "ユーザーIDはすでに利用されています");
//            return badRequest(create.render(userForm));
//        }

        User user = userForm.get();
//        user.id = pubInt;
//        pubInt += 1;
        user.save();
        flash("info", "ユーザーを作成しました");
        return redirect(routes.UsersController.index());
    }

    //editは実装しない
//    public Result edit(Integer id){
//        User user = User.find.byId(id);
//        if(user == null){
//            return notFound(_404.render());
//        }
//        Form<User> userForm = formFactory.form(User.class).fill(user);
//        return ok(edit.render(userForm));
//    }

//    public Result update(){
//        User user = formFactory.form(User.class).bindFromRequest().get();
//        User oldUser = User.find.byId(user.id);
//        if(oldUser==null){
//            flash("danger", "ユーザーが見つかりません");
//            return badRequest();
//        }
//
//        oldUser.id = user.id;
//        oldUser.name = user.name;
//        oldUser.userID = user.userID;
//        oldUser.password = user.password;
//        oldUser.email = user.email;
//        oldUser.update();
//
//        flash("ユーザー情報を更新しました");
//        return redirect(routes.UsersController.index());
//    }

    public Result show(Integer id) {

        User user = User.find.byId(id);
        String user_name = user.getUserName();

        Tweet tweet = Tweet.find.byId(id);
//        User user = tweet.getUser();

//        パターン1
        if (user == null) {
            return notFound("ユーザーが見つかりません");
        }

        ///このユーザーのツイートの検索
        List<Tweet> tweetList = new ArrayList<Tweet>();
        tweetList = User.find.ref(user.id).getTweets();

        //このユーザーの
//        System.out.println(relationshipList + "　このユーザーをフォローするユーザーの総計");


        return ok(show.render(tweetList));

    }

    public Result destroy(Integer id){

        User user = User.find.byId(id);
        if(user == null){
            return notFound("ユーザーが見つかりません");
        }

        user.delete();
        return redirect(routes.UsersController.index());
    }

    public Result search(){
        Form<User> userForm = formFactory.form(User.class);
        return ok(search.render(userForm));
    }

    //ユーザー検索アクション
    public Result do_search(){

        String user_name;

        user_name = formFactory.form().bindFromRequest().get("name_search");
        //受け取った文字列から、sql分でユーザー検索（曖昧検索が要）

        String sql = "SELECT name FROM user WHERE name LIKE  '% "+ user_name + " %'";

        if(Ebean.createSqlQuery(sql).findList().size() !=0){
            List<User> userList = new ArrayList<User>();
            System.out.println("ユーザーが見つかりました");
            return TODO;
        }
        return TODO;
    }



}
