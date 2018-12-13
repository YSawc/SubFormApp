package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Tweet extends Model {
    @Id
    @Constraints.Required
    public Integer id;
    @Constraints.Required
    @Constraints.Max(140)
    public String mutter;

    @ManyToOne
    public User PostUser;

    public static Finder<Integer, Tweet> find = new Finder<>(Tweet.class);
}
