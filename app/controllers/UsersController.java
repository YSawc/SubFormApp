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

import views.html.NotUse.users.edit;
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

    private Integer pubInt = 0;

    public Result index(Integer i){
        if(session("id") != null){
            return  redirect(routes.TweetsController.page(0));
        }
        User user = User.find.byId(i);
        return ok(index.render(user));
    }

    public Result create(){
        Form<User> userForm = formFactory.form(User.class);
        List<String> str_array = new ArrayList<String>();
        return ok(create.render(userForm));
    }

    public Result save(){

        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        List<String> str_array = new ArrayList<String>();

        if(formFactory.form().bindFromRequest().get("data[userID]").isEmpty()){
            str_array.add("ユーザーIDを入力してください");
        }else if(formFactory.form().bindFromRequest().get("data[userID]").length() < 4 || 20 < formFactory.form().bindFromRequest().get("data[userID]").length() ){
            str_array.add("ユーザー名は4文字〜20文字で入力してください");
        }

        //フォームエラーチェック
        if(userForm.hasErrors()){
            System.out.println(userForm + "ユーザーフォーム失敗時の出力");

            //確認用パスワードの不一致
            if(!(formFactory.form().bindFromRequest().get("password_confirm").equals
                    (formFactory.form().bindFromRequest().get("password")))){
                flash("danger", "確認用パスワードが一致しません");
                return badRequest(create.render(userForm));
            }else{
                flash("danger", "正しい値を入力し直してください");

            }
            return badRequest(create.render(userForm));
        }

        System.out.println(formFactory.form().bindFromRequest().get("password_confirm").equals(
                (formFactory.form().bindFromRequest().get("password"))) + "同値かチェック");



        String sql = "SELECT user_id FROM user WHERE user_id= \'"
                + userForm.get().userID + "\'";


        if(Ebean.createSqlQuery(sql).findList().size() !=0){

            flash("danger", "ユーザーIDはすでに利用されています");
            return badRequest(create.render(userForm));
        }
        String user_name = formFactory.form().bindFromRequest().get("data[name]");
        User user = userForm.get();

        user.save();
        flash("info", "ユーザーを作成しました");
        return redirect(routes.UsersController.index(user.id));
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
//        return redirect(routes.UsersController.index());
        return TODO;
    }

    public Result show(Integer id, Integer p) {

        User user = User.find.byId(id);
        String user_name = user.getUserName();

//        パターン1
        if (user == null) {
            return notFound("ユーザーが見つかりません");
        }

        ///このユーザーのツイートの検索
        List<Tweet> tweetList = new ArrayList<Tweet>();
        tweetList = User.find.ref(user.id).getTweets();
        Collections.reverse(tweetList);
//        System.out.println(relationshipList + "　このユーザーをフォローするユーザーの総計");

        final Integer pre_num = 10;
        List<Tweet> new_list = new ArrayList<>();
        //        要素数が足りる場合と、足りない場合がある。
        try {
            new_list = tweetList.subList(pre_num * p, pre_num * p + 10);
            //要素数が10未満の場合次のエラーになるのでキャッチ
        }catch (IndexOutOfBoundsException e) {
            System.out.println("例外のキャッチ");
            new_list = tweetList.subList(pre_num * p, tweetList.size());
        }

        return ok(show.render(new_list, p, user, tweetList.size()));
    }

//    public Result destroy(Integer id){
//
//        User user = User.find.byId(id);
//        if(user == null){
//            return notFound("ユーザーが見つかりません");
//        }
//
//        user.delete();
//        return redirect(routes.UsersController.index());
//    }

    public Result search(){
        Form<User> userForm = formFactory.form(User.class);
        session("searched_name", "");
        return ok(search.render(userForm));
    }

    //ユーザー検索アクション
    public Result do_search(Integer p,Integer out_or){
        Form<User> userForm = formFactory.form(User.class);
        String user_name;

        //セッション管理している検索結果のユーザーを、外部のビューから飛んできたときにセッション削除する
        if(out_or == 1){
            session("searched_name", "");
        }

        //2ページ目以降ならば、strに渡した値をフォームの値の代わりにする
        if(! (session("searched_name").equals("")) ){
            user_name = session("searched_name");
            System.out.println(user_name + " セッションからの検索ユーザー名");
            System.out.println(session("searched_name") + "セッションにハマってるユーザー名の出力");
        }else{
            user_name = formFactory.form().bindFromRequest().get("name_search");
            session("searched_name", user_name);
        }

        if(user_name.length() == 0){
            flash("danger", "対象のユーザーは見つかりません");
            return ok(done_serch.render(userForm, 0, null, null, 0));
        }else if(user_name.matches("^[\\s_]*?$")){
            System.out.println("nullのテスト");
            flash("danger", "対象のユーザーは見つかりません");
            return ok(done_serch.render(userForm, 0, user_name, null, 0));
        }

        System.out.println(user_name.length() + " user_name の出力（検索欄　あいまい検索）");
        String sql = "SELECT id FROM user WHERE name LIKE  '%"
                + user_name
                + "%' OR user_id LIKE '%"
                + user_name
                + "%'";
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
            final Integer pre_num = 10;
            List<Integer> new_list = new ArrayList<>();
            //        要素数が足りる場合と、足りない場合がある。
            try {
                new_list = tables.subList(pre_num * p, pre_num * p + 10);
                //要素数が10未満の場合次のエラーになるのでキャッチ
            }catch (IndexOutOfBoundsException e) {
                System.out.println("例外のキャッチ");
                new_list = tables.subList(pre_num * p, tables.size());
            }
            return ok(done_serch.render(userForm, p, user_name, new_list, tables.size()));
        }

        flash("danger", "対象のユーザーは見つかりません");
//        return redirect(routes.UsersController.search());
        return ok(done_serch.render(userForm, 0, user_name, null, 0));
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

        return redirect(routes.TweetsController.page(0));
    }

    public Result redirect(){
        if(session("id") == null){
            return redirect(routes.LoginController.login());
        }
        return redirect(routes.TweetsController.page(0));
    }

}
