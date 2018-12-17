package models;

import javax.persistence.*;
import javax.validation.Constraint;
import java.util.*;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

@Entity
public class User extends Model{

    //idは1から自動で渡される
    //ユーザーからは見えない
    @Id
    public Integer id;
    @Constraints.Required
    @Constraints.Pattern(value = "^[a-zA-Z0-9]*$", message = "記号を使わないでください")
    //全角文字入らないバグあり [^\x01-\x7E\xA1-\xDF]
    @Constraints.MinLength(4)
    @Constraints.MaxLength(20)
    public String name;
    @Constraints.Required
    @Constraints.Pattern(value = "^[a-zA-Z0-9_-]*$", message = "記号は(_,-)だけ利用できます")
    @Constraints.MinLength(4)
    @Constraints.MaxLength(20)
    @Column(unique = true)
    public String userID;
    @Constraints.Required
    @Constraints.Pattern(value = "^[a-zA-Z0-9]*$", message = "記号を使わないでください")
    @Constraints.MinLength(4)
    @Constraints.MaxLength(8)
    public String password;
    @Constraints.Required
    @Constraints.MaxLength(100)
    @Constraints.Email
    public String email;

    public Integer follower = 0;

    public Integer follow = 0;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Tweet> tweets;

    public static Finder<Integer, User> find = new Finder<>(User.class);

    public String getName(){
        return this.name;
    }

    public String getUserName(){
        return this.userID;
    }

    public List<Tweet> getTweets(){
        return this.tweets;
    }

}
