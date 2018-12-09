package controllers;

import models.User;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import javax.inject.Inject;
import java.util.List;

public class UserController extends Controller {
    @Inject
    FormFactory formFactory;

    public Result index(){
        List<User> users = User.find.all();
        return ok(index.render(users))
    }
}
