package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import models.Follow;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.follows.*;
import views.html.users.show;

import javax.inject.Singleton;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FollowsController extends Controller {

    public Result follow(Integer id){

        User user_beFollowed = User.find.byId(id);
        User user_doneFollow = User.find.byId(Integer.parseInt(session("id")));

        Follow follow = new Follow();

        //SQL処理用の文字列
        String sql = "SELECT DISTINCT follow_id FROM follow WHERE follow_id="
                + user_doneFollow.id + " AND be_followed_id=" + user_beFollowed.id ;

        System.out.println(Ebean.createSqlQuery(sql).findList().size() + "sql実行結果(デバッグ)");

        //フォローされているかどうかを判別し、されていればフォローを外すメソッド
        if (Ebean.createSqlQuery(sql).findList().size() == 1){
            //フォローを外す処理
            follow.setFollow_id(null);
            follow.setBeFollowed_id(null);
            follow.save();
            System.out.println("削除テスト");
            return redirect(routes.TweetsController.index());
        }

        System.out.println(sql + "  sql出力");

        //sqlをif分制御

        follow.setFollow_id(user_doneFollow.id);
        follow.setBeFollowed_id(user_beFollowed.id);

        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();

        int ary[] = new int[sqlRows.size()];

        //sql分から、数値を抜き出す
        //今回抜き出すのはフォローされた側のid番号
        for(int i=0; i<sqlRows.size(); ++i){
            System.out.println(sqlRows.get(i) + "  リスト" + i +"番目の要素のクラス");
            ary[i] = Integer.parseInt((sqlRows.get(i)
                    .toString().split("=",0)[1].toString().split("}",0)[0]));
            System.out.println(ary[i] + " ary[i]の中身");

        }

        follow.save();

        return redirect(routes.TweetsController.index());
//        return (follow.render());
    }

    public Result show(Integer id){

//        Follow follow = new Follow();
//
//        //SQL処理用の文字列
//        String sql = "SELECT DISTINCT follow_id FROM follow WHERE be_followed_id= " + user_beFollowed.id ;

        //SQLの実行部分
//        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();

        return TODO;
    }
}
