package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//いいね機能で利用する
@Table(
        uniqueConstraints =
            @UniqueConstraint(columnNames = {"follower_id", "followed_id"})
)

@Entity
public class Relationship extends Model {
    @Id
    public Integer id;
    @Column
    public Integer follower_id;
    @Column
    public Integer followed_id;



    @OneToMany(cascade = CascadeType.ALL)
    public List<User> users = new ArrayList<>();;

    public static Finder<Integer, Relationship> find = new Finder<>(Relationship.class);

    public Integer getFollowed_id(){
        return this.followed_id;
    }

    public void setFollowed_id(Integer followed_id){
        this.followed_id = followed_id;
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
