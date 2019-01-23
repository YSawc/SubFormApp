package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import models.Follow;
import models.Tweet;
import models.User;
import org.joda.convert.ToString;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.follows.follow;
import views.html.follows.show;
import views.html.follows.show2;
import views.html.users.done_serch;

import javax.inject.Singleton;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Singleton
public class FollowsController extends Controller {

    public Result follow(Integer id){

        User user_beFollowed = User.find.byId(id);
        User user_doneFollow = User.find.byId(Integer.parseInt(session("id")));
        //SQL処理用の文字列
        String sql = "SELECT DISTINCT follow_id FROM follow WHERE follow_id="
                + user_doneFollow.id + " AND be_followed_id=" + user_beFollowed.id ;

        //フォローされているかどうかを判別し、されていればフォローを外すメソッド
        //カラムが存在しているかどうか判別
        if (Ebean.createSqlQuery(sql).findList().size() == 1){

            String sql_ver2 = "SELECT id FROM follow WHERE follow_id="
                    + user_doneFollow.id + " AND be_followed_id=" + user_beFollowed.id ;
            //カーリー
            SqlQuery sqlQuery = Ebean.createSqlQuery(sql_ver2);
            sqlQuery.setParameter("follow_id",user_doneFollow.id)
                    .setParameter("be_followed_id", user_beFollowed)
                    .findUnique();
            List<SqlRow> result = sqlQuery.findList();
            String allowed_id = "";

            //見つからなければ
            if (result.size() > 0) {
                allowed_id = result.get(0).getString("id");
            }

            Integer allowed_id_int;
            allowed_id_int = Integer.parseInt(allowed_id);
            System.out.println(sqlQuery + "sqlQueryの出力");
            System.out.println(result + "resultの出力");
            System.out.println(allowed_id + "allowed_idの出力");
            System.out.println(allowed_id_int + "alllowed_id_intの出力");
            //--------------------------
            Follow delete_follow = Follow.find.byId(allowed_id_int);
            delete_follow.delete();
            System.out.println("削除テスト");

            return redirect(routes.UsersController.show(id));
        }

        Follow new_follow = new Follow();
        new_follow.follow_id = user_doneFollow.id;
        new_follow.beFollowed_id = user_beFollowed.id;
        new_follow.save();

//        return redirect(routes.TweetsController.index());
        return redirect(routes.UsersController.show(id));
    }

    public Result show(Integer id, Integer p){

        System.out.println(id + "  idの出力");
        User user = User.find.byId(id);
        System.out.println(user + "userの出力");
        String sql = "SELECT be_followed_id FROM follow WHERE follow_id="
                + (user.id);
//                + "OR SELECT follow_id FROM follow WHERE be_followed_id="
//                + (user.id);
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> result = sqlQuery.findList();
        List<Integer> tables = new ArrayList<Integer>();
        System.out.println(result + "  resultの出力");

        if(result.size() > 0){
            result.forEach(sqlRow ->{
                tables.add(Integer.parseInt(sqlRow.getString("be_followed_id")));
                    });
            System.out.println(tables + "  tablesの出力");
            for(Integer table : tables){
                System.out.println(table + "  tableの出力");
            }
        }

        List<Tweet> tweetList = new ArrayList<>();
        tweetList = User.find.ref(id).getTweets();
//        System.out.println(tweetList.size() + "ツイートリストのサイズの出力");

        for (int i: tables){
            System.out.println(i + "  i の出力");
        }

        System.out.println(user.get_this_Followed_list(user.id) + "  モデル側設置のsql文のデバッグ確認");
        String sql_2 = "SELECT id FROM tweet WHERE user_id IN( "
                + "SELECT be_followed_id FROM follow WHERE follow_id="
                + (user.id)
                + ") ORDER BY created_date";
        SqlQuery sqlQuery_2 = Ebean.createSqlQuery(sql_2);
        List<SqlRow> result_2 = sqlQuery_2.findList();
        List<Integer> tables_2 = new ArrayList<Integer>();
        System.out.println(result_2 + "  result_2の出力");

        if(result_2.size() > 0){
            result_2.forEach(sqlRow ->{
                System.out.println(sqlRow + "sqlRowの出力");
                tables_2.add(Integer.parseInt((sqlRow.getString("id"))));
            });
            for(Integer i : tables_2){
                System.out.println(i + " table_2の出力");
            }
        }

        //降順にする
        Collections.reverse(tables);

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

        if(user.get_whitch_follow_or(user.id)){
            System.out.println("フォローしてない");
        }

        //ツイート内容は逆順にし、新しいもの順にする
        Collections.reverse(tables_2);
        System.out.println(user.getName() + "ユーザー詳細画面　ユーザー名");
        return ok(show.render(user, p, new_list, tables.size()));
    }

    public Result show_ver2(Integer id){

        System.out.println(id + "  idの出力");
        User user = User.find.byId(id);
        System.out.println(user + "userの出力");
        String sql = "SELECT follow_id FROM follow WHERE be_followed_id="
                + (user.id);
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> result = sqlQuery.findList();
        List<Integer> tables = new ArrayList<Integer>();
        System.out.println(result + "  resultの出力");

        if(result.size() > 0){
            result.forEach(sqlRow ->{
                tables.add(Integer.parseInt(sqlRow.getString("follow_id")));
            });
            System.out.println(tables + "  tablesの出力");
            for(Integer table : tables){
                System.out.println(table + "  tableの出力");
            }
        }

        List<Tweet> tweetList = new ArrayList<>();
        tweetList = User.find.ref(id).getTweets();
        System.out.println(tweetList.size() + "ツイートリストのサイズの出力");

        for (int i: tables){
            System.out.println(i + "  i の出力");
        }

        System.out.println(user.get_this_Followed_list(user.id) + "  モデル側設置のsql文のデバッグ確認");

        if(user.get_whitch_follow_or(user.id)){
            System.out.println("フォローしてない");
        }

        return ok(show2.render(user, tables));
    }
}
