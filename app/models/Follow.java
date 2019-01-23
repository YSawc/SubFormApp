package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

//いいね機能で利用する



//@Table(
//        uniqueConstraints =
//        @UniqueConstraint(columnNames = {"follower_id", "followed_id"})
//)
@Entity
public class Follow extends Model {
    @Id
    public Integer id;
    @Column
    public Integer follow_id;
    @Column
    public Integer beFollowed_id;

    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    public List<User> users = new ArrayList<>();

    public static Finder<Integer, Follow> find = new Finder<>(Follow.class);

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(Integer follow_id) {
        this.follow_id = follow_id;
    }

    public Integer getBeFollowed_id() {
        return beFollowed_id;
    }

    public void setBeFollowed_id(Integer beFollowed_id) {
        this.beFollowed_id = beFollowed_id;
    }

    public boolean follow_this_user_or(Integer user1_id, Integer user2_id){
        String sql = "SELECT be_followed_id="
                + (user1_id)
                + " FROM follow WHERE follow_id="
                + (user2_id);
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> result = sqlQuery.findList();
        System.out.println(result.size() + "result.size()の出力");
        if (result.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
