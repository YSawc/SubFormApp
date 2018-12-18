package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Good extends Model {

    @Id
    public Integer id;

    public static Finder<Integer, Good> find = new Finder<>(Good.class);

    @OneToOne(cascade = CascadeType.ALL)
    public User users;

}
