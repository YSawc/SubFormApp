package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Constraint;
import java.util.List;

@Entity
public class Follow extends Model {

    @Id
    public Integer id;

    //フォローモデルを持つユーザーを指定する必要がある
//    @Constraints.Required
    public User haveFollow;

    public List<User> beFollowed;

    public static Finder<Integer, Follow> find = new Finder<>(Follow.class);

    @OneToOne(cascade = CascadeType.ALL)
    public User user;
//    public List<User> users;
//    public List<User> users;


    public List<User> getBeFollowed() {
        return beFollowed;
    }
}
