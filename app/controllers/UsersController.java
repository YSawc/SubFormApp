package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
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
import java.util.Collections;
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
        if(!(formFactory.form().bindFromRequest().get("password_confirm").equals
                (formFactory.form().bindFromRequest().get("password")))){
            flash("danger", "確認用パスワードが一致しません");
            return badRequest(create.render(userForm));
        }

        String sql = "SELECT user_id FROM user WHERE user_id= \'"
                + userForm.get().userID + "\'";


        if(Ebean.createSqlQuery(sql).findList().size() !=0){

            flash("danger", "ユーザーIDはすでに利用されています");
            return badRequest(create.render(userForm));
        }

        User user = userForm.get();

        user.save();
        flash("info", "ユーザーを作成しました");
        return redirect(routes.UsersController.index());
    }

    //editは実装しない
    public Result edit(Integer id){
        User user = User.find.byId(id);
        if(user == null){
            return notFound(_404.render());
        }
        Form<User> userForm = formFactory.form(User.class).fill(user);
        return ok(edit.render(userForm));
    }

    public Result update(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        User user = formFactory.form(User.class).bindFromRequest().get();
        System.out.println("通過テスト");
        User oldUser = User.find.byId(Integer.parseInt(session("id")));
        if(oldUser==null){
            flash("danger", "ユーザーが見つかりません");
            return badRequest();
        }

        System.out.println(oldUser.name);

        if(formFactory.form().bindFromRequest().get("old_password") != oldUser.password){
            flash("danger", "元のパスワードと一致しません");
            return badRequest(edit.render(userForm));
        }
        user.id = oldUser.id;
        oldUser.id = user.id;
        oldUser.name = user.name;
        oldUser.userID = user.userID;
        oldUser.password = user.password;
        oldUser.email = user.email;
        oldUser.update();

        flash("ユーザー情報を更新しました");
        return redirect(routes.UsersController.index());
    }

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
        Collections.reverse(tweetList);

//        System.out.println(relationshipList + "　このユーザーをフォローするユーザーの総計");

        return ok(show.render(tweetList, user));
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

        System.out.println(user_name + " user_name の出力（検索欄　あいまい検索）");

        if(user_name.matches("^[\\s_]*?$")){
            System.out.println("nullのテスト");
            flash("danger", "ユーザーは見つかりませんでした");
            return redirect(routes.UsersController.search());
        }

        String sql = "SELECT id FROM user WHERE name LIKE  '%"+ user_name + "%'";

        System.out.println(Ebean.createSqlQuery(sql).findList() + "検索結果");

        if(Ebean.createSqlQuery(sql).findList().size() !=0){
            System.out.println("ユーザーが見つかりました");
            SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
            List<SqlRow> result = sqlQuery.findList();
            List<Integer> tables = new ArrayList<Integer>();
            result.forEach(sqlRow ->{
                tables.add(Integer.parseInt(sqlRow.getString("id")));
//                userList.add(sqlRow.getString("name"));
            } );

            for (Integer i : tables) {
                System.out.println(i + " iの出力");
            }

            return ok(done_serch.render(tables));
        }

        flash("danger", "ユーザーは見つかりませんでした");
        return redirect(routes.UsersController.search());
    }

    public Result switch_pub_or(){
//        return redirect(routes.UsersController.index());
        User user = User.find.byId(Integer.parseInt(session("id")));
        System.out.println(user.private_or);

        if(user.private_or){
//            System.out.println("false");
            user.private_or = false;
            session("private_or","1");
            flash("info", "ツイートを「公開する」にしました。");
        }else{
//            System.out.println("true");
            session("private_or","0");
            user.private_or = true;
            flash("info", "ツイートを「非公開」にしました。");
        }

        user.save();

        return redirect(routes.TweetsController.index());
    }

}
