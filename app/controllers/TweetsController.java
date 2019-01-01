package controllers;

import models.Tweet;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import views.html.tweets.*;

@Singleton
public class TweetsController extends Controller {
    @Inject
    private FormFactory formFactory;

    //idは0から始める。publicにし昇順に自動付与していく
//    private Integer pubInt = 0;

    public Result index(){

        //userListは定義済み
        List<Tweet> tweetList = new ArrayList<Tweet>();
        List<User> userList = User.find.query().findList();
        tweetList = Tweet.find.all();

        return ok(index.render(tweetList));
    }

    public Result create(){
        Form<Tweet> tweetForm = formFactory.form(Tweet.class);
        return ok(create.render(tweetForm));
//        return redirect(routes.UsersController.index());
    }

    public Result show(Integer id){
        Tweet tweet = Tweet.find.byId(id);
        User user = tweet.getUser();

        System.out.println(user.userID + "ユーザーID");

        if(tweet == null){
            return redirect(routes.TweetsController.index());
        }
        return TODO;
    }

    public Result save(){
        Form<Tweet> tweetForm = formFactory.form(Tweet.class).bindFromRequest();

        if(tweetForm.hasErrors()){
            flash("danger", "ツイート内容に誤りがあります");
            return redirect(routes.TweetsController.create());
        }

        Tweet tweet = tweetForm.get();

        //ツイート内容が文頭スペース、改行のみの連鎖の場合不正エラーを出力
        if(tweet.mutter.matches("^[\\s]*?$")){
            flash("danger", "不正なツイートです");
            return redirect(routes.TweetsController.create());
        }
        //正規表現チェックの終わり--------

//        tweet.id = pubInt;

        //セッションのidからユーザーのidを照らし合わせ、マッチさせる
        User user = User.find.byId(Integer.parseInt(session("id")));
        tweet.setUser(user);
//        pubInt += 1;
        tweet.save();
        return  redirect(routes.TweetsController.index());
    }

    public Result destroy(Integer id){

        Tweet tweet = Tweet.find.byId(id);
        if(tweet == null){
            flash("dandger", "ツイートが見つかりません");
            return redirect(routes.TweetsController.index());
        }

        tweet.delete();
        return redirect(routes.TweetsController.index());
    }

    //いいね機能
//    public void addGood(Integer id){
//        Tweet tweet = Tweet.find.byId(id);
//
//        //不具合対策
//        if(tweet == null){
//            flash("dandger", "ツイートが見つかりません");
//        }
//
//        Boolean swt = false;
//        if(swt == false){
//            tweet.good += 1;
//            swt = true;
//        }else if(swt == true){
//            tweet.good -= 1;
//            swt = false;
//        }
//
//    }
}
