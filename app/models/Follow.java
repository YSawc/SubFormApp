package models;

import com.avaje.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Follow extends Model {

    @Id
    public Integer id;

    //フォローモデルを持つユーザーを指定する必要がある
    public User haveFollow;

    public static Finder<Integer, Follow> find = new Finder<>(Follow.class);

    @OneToMany(cascade = CascadeType.ALL)
    public List<User> users;

}
