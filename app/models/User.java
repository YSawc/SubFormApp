package models;

import javax.persistence.*;
import javax.validation.Constraint;
import java.util.*;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

@Entity
public class User extends Model{

    //gitテストです

    //idは1から自動で渡される
    //ユーザーからは見えない
    @Id
    public Integer id;
    @Constraints.Required
    @Constraints.Pattern(value = "^[a-zA-Z0-9]*$", message = "ローマ字と半角英数字を利用できます")
    @Constraints.MinLength(4)
    @Constraints.MaxLength(20)
    public String name;
    @Constraints.Required
    @Constraints.Pattern(value = "^[a-zA-Z0-9_-]*$", message = "ローマ字を利用してください。記号は(_,-)だけ利用できます")
    @Constraints.MinLength(4)
    @Constraints.MaxLength(20)
    @Column(unique = true)
    public String userID;
    @Constraints.Required
    @Constraints.Pattern(value = "^[a-zA-Z0-9]*$", message = "ローマ字と半角英数字を利用できます\"")
    @Constraints.MinLength(4)
    @Constraints.MaxLength(8)
    public String password;
    @Constraints.Required
    @Constraints.MaxLength(100)
    @Constraints.Email
    public String email;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Tweet> tweets;

    @OneToMany
    public Follow follow;

    @OneToMany
    public Follower follower;

    @ManyToOne
    public Good good;

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

    //フォローモデルの呼び出し
    public Follow getFollow() {
        return this.follow;
    }

}
