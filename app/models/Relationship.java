package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//いいね機能で利用する


@Entity
//@Table(
//        uniqueConstraints =
//        @UniqueConstraint(columnNames = {"follower_id", "followed_id"})
//)
public class Relationship extends Model {
    @Id
    public Integer id;
    @Column
    public Integer follower_id;
    @Column
    public Integer relationships_id;



    @OneToMany(cascade = CascadeType.ALL)
    public List<User> users = new ArrayList<>();

    public static Finder<Integer, Relationship> find = new Finder<>(Relationship.class);

    public List<User> getUsers() {
        return users;
    }

    public Integer getFollowed_id(){
        return this.relationships_id;
    }

    public void setFollowed_id(Integer relationships_id){
        this.relationships_id = relationships_id;
    }

    public Integer getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(Integer follower_id) {
        this.follower_id = follower_id;
    }

//    public List<Relationship> getFollowerList(){
//        return this.find.where()
//                .eq("followed_id", this.getFollowed_id())
//                .setDistinct(true).select("followed_id")
//                .findList();
//    }

}
