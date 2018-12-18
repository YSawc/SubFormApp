package controllers;

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

        User user_beFollowed = User.find.byId(id);
        User user_followed = User.find.byId(Integer.parseInt(session("id")));
        System.out.println(user_beFollowed.getName() + "フォローされたユーザーの名前");
        System.out.println(user_followed.getName() + "フォローをしたユーザーの名前");

        //作成中
        List<User> beFollowedtUserList = new ArrayList<User>();
//        beFollowedtUserList = User.find.ref(user_beFollowed.id).getTweets();

        //フォロー済みかどうか判定する
        if(beFollowedtUserList.contains(user_followed)){
            beFollowedtUserList.remove(user_followed);
            System.out.println("削除した");
        }else{
            beFollowedtUserList.add(user_followed);
            System.out.println("登録した");
        }


        System.out.println(beFollowedtUserList + "フォローしたユーザーのモデルを出力");

        System.out.println(user_beFollowed.getTweets().size() + "ツイート数");

        for(User userModel : beFollowedtUserList){
            System.out.println(userModel.getName() + "フォローしたユーザーの名前をfor分で出力");
        }



        //user_followed.se

        //フォローするユーザーが、されるユーザのリストにaddする

        return redirect(routes.TweetsController.index());
    }
}
