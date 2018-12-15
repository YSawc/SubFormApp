package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.*;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

@Entity
public class User extends Model{

    //ユーザーがidを指定するのはおかしいので、変更が必要
    @Id
    public Integer id;
    @Constraints.Required
    public String name;
    @Constraints.Required
    public String userName;
    @Constraints.Required
    @Constraints.MinLength(6)
    public String password;
    @Constraints.Required
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
        return this.userName;
    }

    public List<Tweet> getTweets(){
        return this.tweets;
    }

}
