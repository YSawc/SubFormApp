package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Tweet extends Model {
    @Constraints.Max(140)
    public String tweet;

    @ManyToOne
    public User user;
}
