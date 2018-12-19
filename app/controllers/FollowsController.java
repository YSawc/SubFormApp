package controllers;

import models.Follow;
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
        User user_doneFollow = User.find.byId(Integer.parseInt(session("id")));
        System.out.println(user_beFollowed.getName() + "フォローされたユーザーの名前");
        System.out.println(user_doneFollow.getName() + "フォローをしたユーザーの名前");

        Follow follow = new Follow();

        follow.setFollow_id(user_doneFollow.id);
        follow.setBeFollowed_id(user_beFollowed.id);

        //実験
        follow = Follow.find.ref(user_doneFollow.id);

        List<Follow> list = Follow.find.where()
                .eq("follow_id", user_doneFollow.id)
                .setDistinct(true).select("followed_id")
                .findList();

        System.out.println(list + "  list表示");


//        follow.setFollower_id(user_doneFollowed.id);
//        follow.setFollowed_id(user_beFollowed.id);

//        follow.follow_id = user_doneFollow.id;
//        follow.relationships_id = user_beFollowed.id;

//        System.out.println(follow + "リレーションシップのユーザー所有？");

        user_doneFollow.save();
        user_doneFollow.setFollow(follow);
        follow.save();

//        System.out.println(list + "  list表示");

        return redirect(routes.TweetsController.index());
    }

    public Result show(Integer id){

        return TODO;
    }
}
