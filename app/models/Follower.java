package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Follower extends Model {

    @Id
    public Integer id;

    public List<User> followedUser;

    public static Finder<Integer, Follower> find = new Finder<>(Follower.class);

    @OneToOne(cascade = CascadeType.ALL)
    public User user;

    public List<User> getFollowedUser() {
        return followedUser;
    }
}
