package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Relationship extends Model {
    @Id
    public Integer id;
    @Column(unique = true)
    public Integer follower_id;
    @Column(unique = true)
    public Integer followed_id;

    @OneToMany(cascade = CascadeType.ALL)
    public List<User> users = new ArrayList<>();;

    public static Finder<Integer, Relationship> find = new Finder<>(Relationship.class);

}
