package models;

import com.avaje.ebean.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.util.*;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

@Entity
public class User extends Model{
    @Id
    @Constraints.Required
    public Integer id;
    public String name;

    public static Finder<Integer, User> find = new Finder<>(User.class);

}
