package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import models.Follow;
import models.Tweet;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.follows.follow;
import views.html.follows.show;
import views.html.users.show;
//import views.html.users.show;

import javax.inject.Singleton;
import java.sql.ResultSet;
import java.util.ArrayList;
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

            return redirect(routes.TweetsController.index());
        }

        Follow new_follow = new Follow();

        new_follow.follow_id = user_doneFollow.id;
        new_follow.beFollowed_id = user_beFollowed.id;

        new_follow.save();

        return TODO;

    }

    public Result show(Integer id){


        User user = User.find.byId(id);

        String sql = "SELECT be_followed_id FROM follow WHERE follow_id="
                + user.id;

        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> result = sqlQuery.findList();
        List<Integer> tables = new ArrayList<Integer>();

        System.out.println(result + "  resultの出力");

        if(result.size() > 0){
            result.forEach(sqlRow ->{
                tables.add(Integer.parseInt(sqlRow.getString("be_followed_id")));
                    });


//            allowed_id = result.get(0).getString("be_followed_id");
//            allowed_id_int = Integer.parseInt(allowed_id);
            System.out.println(tables + "  tablesの出力");
        }
//

        return TODO;
        return ok(show.render(user.getTweets(), tables));
    }
}
