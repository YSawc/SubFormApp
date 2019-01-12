package controllers;

import models.Tweet;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//        System.out.println(tweetList + " ツイートリスト");

//        tweetList.sort(Comparator.comparing(Tweet));
//
//        Collections.sort(tweetList, ((o1, o2) ->{
//            o1.createdDate.after(o2.createdDate)
//        }
//        ));

        System.out.println("nulp前出力");

        List<Tweet> rev_tweetList = new ArrayList<>();

        //リストの逆順化
        Collections.reverse(tweetList);

        //ぬるぽエラー対策
        if(tweetList.isEmpty()){
//            return redirect(routes.TweetsController.empty());
            return ok(index.render(tweetList));
        }else{
            return ok(index.render(tweetList));
        }
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


        //正規表現チェック----------------------
        if(tweet.mutter.length() > 140){
            flash("danger", "140文字以内で入力してください");
            return redirect(routes.TweetsController.create());
        }

        //ツイート内容が文頭スペース、改行、ハイフンのみの連鎖の場合不正エラーを出力
        if(tweet.mutter.matches("^[\\s_]*?$")){
            flash("danger", "不正なツイートです");
            return redirect(routes.TweetsController.create());
        }
        //正規表現チェックの終わり---------------

        //セッションのidからユーザーのidを照らし合わせ、マッチさせる
        User user = User.find.byId(Integer.parseInt(session("id")));
        tweet.setUser(user);

        //------------------------------- URL変換機能 ---------------------------
        String urlStr = tweet.convURLLink(tweet.mutter);
        System.out.println(urlStr + " uslStr");

        System.out.println(tweet.convURLLink(tweet.mutter) + "url変換後");
//
//        tweet.mutter = tweet.convURLLink(tweet.mutter);
        //-----------------------------------------------------------------


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

    public Result page(Integer p) throws Exception{

        //1画面に表示するツイートの数
        final Integer pre_num = 10;

        List<Tweet> tweetList = Tweet.find.all();
        Collections.reverse(tweetList);

        List<Tweet> new_tweetList = new ArrayList<>();


        //要素数が足りる場合と、足りない場合がある。
        try {
            new_tweetList = tweetList.subList(pre_num * p , pre_num * p + 10);

            //要素数が10未満の場合次のエラーになるのでキャッチ
        }catch (IndexOutOfBoundsException e) {
            System.out.println("例外のキャッチ");
            new_tweetList = tweetList.subList(pre_num * p, tweetList.size());
        }

        System.out.println(new_tweetList.size() + " ニューリストのサイズを出力");
        return ok(page.render(p, new_tweetList));
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
