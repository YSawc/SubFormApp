package controllers;

import play.data.FormFactory;
import play.mvc.Controller;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TweetController extends Controller {
    @Inject
    private FormFactory formFactory;


}
