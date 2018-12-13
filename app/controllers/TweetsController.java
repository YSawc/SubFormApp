package controllers;

import models.Tweet;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TweetsController extends Controller {
    @Inject
    private FormFactory formFactory;

    public Result index(){
        return TODO;
    }

    public Result create(){
        Form<Tweet> tweetForm = formFactory.form(Tweet.class);
        return TODO;
    }

    public Result show(Integer id){
        Tweet tweet = Tweet.find.byId(id);
        if(tweet == null){
            return TODO;
        }
        return TODO;
    }

    public Result save(){
        return  TODO;
    }

    public Result destroy(Integer id){
        return TODO;
    }
}
