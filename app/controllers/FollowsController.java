package controllers;

import models.Follow;
import models.Tweet;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

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
//
//        List<User> beFollowedtUserList = new ArrayList<>();
//        beFollowedtUserList = User.find.ref(user_beFollowed.id).getFollow().getBeFollowed();
//
//        beFollowedtUserList.save();

        //コピー用
//        List<Tweet> tweetList = new ArrayList<Tweet>();
//        tweetList = User.find.ref(user.id).getTweets();


//        beFollowedtUserList = user_beFollowed.getFollow().getBeFollowed();

//        //フォロー済みかどうか判定する
//        if(beFollowedtUserList.contains(user_doneFollowed)){
//            beFollowedtUserList.remove(user_doneFollowed);
//            System.out.println("削除した");
//        }else{
//            beFollowedtUserList.add(user_doneFollowed);
//            System.out.println("登録した");
//            System.out.println(beFollowedtUserList + "登録後の確認");
//            beFollowedtUserList.remove(user_doneFollowed);
//            System.out.println("削除した");
//            System.out.println(beFollowedtUserList + "削除後の確認");
//        }

        //user_followed.se

        //フォローするユーザーが、されるユーザのリストにaddする

        return redirect(routes.TweetsController.index());
    }
}
