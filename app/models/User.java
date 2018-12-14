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
    public String username;
    @Constraints.Required
    @Constraints.MinLength(6)
    public String password;
    @Constraints.Required
    @Constraints.Email
    public String email;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Tweet> tweets;

    public static Finder<Integer, User> find = new Finder<>(User.class);

}
