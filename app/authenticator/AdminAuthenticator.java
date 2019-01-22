package authenticator;

import controllers.routes;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class AdminAuthenticator extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx){
        String userName = ctx.session().get("userName");
        return("admin".equals(userName)) ? userName: null;
    }

//    @Override
//    public Result onUnauthorized(Http.Context ctx){
//        return redirect(routes.UsersController.index());
//    }
}
