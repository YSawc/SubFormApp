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

//        String sql = "SELECT DISTINCT follow_id, be_followed_id FROM follow WHERE follow_id='2' AND be_followed_id='1'";
        String sql = "SELECT DISTINCT be_followed_id FROM follow WHERE follow_id= "
                + user_doneFollow.id + " AND be_followed_id=" +user_beFollowed.id ;

        System.out.println(sql + "sql出力");

        //sqlをif分制御

//        if(List<SqlRow>.Contain){
//
//        }

        follow.setFollow_id(user_doneFollow.id);
        follow.setBeFollowed_id(user_beFollowed.id);



        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();

        List<String> tables = new ArrayList<String>();

//        System.out.println(follow + "リレーションシップのユーザー所有？");

//        user_doneFollow.save();
//        user_doneFollow.setFollow(follow);
        follow.save();

        System.out.println(sqlRows + " sqpRowの出力");

//        System.out.println(list + "  list表示");

        return redirect(routes.TweetsController.index());
    }

    public Result show(Integer id){

        return TODO;
    }
}
