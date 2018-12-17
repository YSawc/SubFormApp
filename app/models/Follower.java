package models;

import com.avaje.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Follower extends Model {

    @Id
    public Integer id;

    public static Finder<Integer, Follower> find = new Finder<>(Follower.class);

    @OneToMany(cascade = CascadeType.ALL)
    public List<User> users;

}
