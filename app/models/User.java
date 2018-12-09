package models;

import com.avaje.ebean.Finder;

import java.util.*;
import javax.persistence.*;

@Entity
public class User {
    @Id
    public Long id;
    public String Name;

    public static Finder<Integer, User> find = new Finder<>(User.class);
}
