package controllers;

import play.data.FormFactory;
import play.mvc.Controller;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TweetsController extends Controller {
    @Inject
    private FormFactory formFactory;
}
