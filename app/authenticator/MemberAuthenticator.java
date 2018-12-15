package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Content;

import javax.naming.Context;

public class MemberAuthenticator extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx){
        return ctx.session().get("userName");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx){
        return redirect(routes.UsersController.index());
    }
}
