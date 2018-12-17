package models;
import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Constraint;

@Entity
public class Tweet extends Model {
    @Id
//    idは自動生成するので必須にしない
    public Integer id;
    @Constraints.Required
    @Constraints.MaxLength(140)
    public String mutter;

    @ManyToOne
    public User user;

    //いいね機能
    public Integer good;

    public static Finder<Integer, Tweet> find = new Finder<>(Tweet.class);

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getPostUser(){
        return this.user;
    }
}
