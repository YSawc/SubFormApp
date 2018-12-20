package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import models.Follow;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

//import views.html.follows.*;
import views.html.users.show;

import javax.inject.Singleton;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FollowsController extends Controller {

    //フォローすると、フォローされる側のフォロワーモデルに名前がセット
    //フォローした側はフォローモデルにフォローされた側の名前をセットする

    public Result follow(Integer id){

        User user_beFollowed = User.find.byId(id);
        User user_doneFollow = User.find.byId(Integer.parseInt(session("id")));

        Follow follow = new Follow();

        String sql = "SELECT DISTINCT follow_id FROM follow WHERE be_followed_id= " + user_beFollowed.id ;

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



//        System.out.println(follow + "リレーションシップのユーザー所有？");

//        user_doneFollow.save();
//        user_doneFollow.setFollow(follow);

        //デバッグ用
//        System.out.println(sqlRows + " sqpRowの出力");

//        System.out.println(list + "  list表示");

        follow.save();

//        return redirect(routes.TweetsController.index());
        return (show.render());
    }

    public Result show(Integer id){

        return TODO;
    }
}
