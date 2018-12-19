package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Singleton;

@Singleton
public class FollowsController extends Controller {

    //フォローすると、フォローされる側のフォロワーモデルに名前がセット
    //フォローした側はフォローモデルにフォローされた側の名前をセットする

    public Result follow(Integer id){

        System.out.println("てすと");

        User user_beFollowed = User.find.byId(id);
        User user_doneFollowed = User.find.byId(Integer.parseInt(session("id")));
        System.out.println(user_beFollowed.getName() + "フォローされたユーザーの名前");
        System.out.println(user_doneFollowed.getName() + "フォローをしたユーザーの名前");

        System.out.println();

//        List<User> list = User.find.where().eq("", "")

        return redirect(routes.TweetsController.index());
    }
}
