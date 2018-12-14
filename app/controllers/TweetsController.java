package controllers;

import models.Tweet;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import views.html.tweets.*;

@Singleton
public class TweetsController extends Controller {
    @Inject
    private FormFactory formFactory;

    //idは0から始める。publicにし昇順に自動付与していく
    private Integer pubInt = 0;

    public Result index(Integer focs){

        List<Tweet> tweetList = Tweet.find.all();

        //場合によって表示するツイートを変更する
        if (focs == 0){
            return ok(index.render(tweetList));
        }else{
            return ok(index.render(tweetList));
        }
    }

    public Result create(){
        System.out.println("テストです");
        Form<Tweet> tweetForm = formFactory.form(Tweet.class);
        return ok(create.render(tweetForm));
//        return redirect(routes.UsersController.index());
    }

    public Result show(Integer id){
        Tweet tweet = Tweet.find.byId(id);

        if(tweet == null){
            return redirect(routes.UsersController.index());
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
        tweet.id = pubInt;

        //セッションのidからユーザーのidを照らし合わせ、マッチさせる
        User user = User.find.byId(Integer.parseInt(session("id")));
        tweet.setPostUserName(user.name);
        System.out.println(tweet.getPostUserName() + "<<tweet.PostUser.name");

        pubInt += 1;
        tweet.save();
        return  redirect(routes.TweetsController.index( 0));
    }

    public Result destroy(Integer id){
        return TODO;
    }
}
