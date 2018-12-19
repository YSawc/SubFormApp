package controllers;

import com.avaje.ebean.Ebean;
import models.Relationship;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class FollowsController extends Controller {

    //フォローすると、フォローされる側のフォロワーモデルに名前がセット
    //フォローした側はフォローモデルにフォローされた側の名前をセットする

    public Result follow(Integer id){

        User user_beFollowed = User.find.byId(id);
        User user_doneFollowed = User.find.byId(Integer.parseInt(session("id")));
        System.out.println(user_beFollowed.getName() + "フォローされたユーザーの名前");
        System.out.println(user_doneFollowed.getName() + "フォローをしたユーザーの名前");

        Relationship relationship = new Relationship();

        relationship = Relationship.find.ref(user_beFollowed.id);

        user_beFollowed.setRelationships(relationship);
//        relationship.setFollowed_id(user_beFollowed.id);
//
//        List<Relationship> list = Relationship.find.where()
//                .eq("follower_id", user_beFollowed.id)
//                .setDistinct(true).select("followed_id")
//                .findList();

//        System.out.println(list + "  list表示");

//        relationship.setFollower_id(user_doneFollowed.id);
//        relationship.setFollowed_id(user_beFollowed.id);

        relationship.follower_id = user_doneFollowed.id;
//        relationship.relationships_id = user_beFollowed.id;

        System.out.println(relationship + "リレーションシップのユーザー所有？");

        relationship.save();

//        System.out.println(list + "  list表示");

        return redirect(routes.TweetsController.index());
    }

    public Result show(Integer id){

        return TODO;
    }
}
