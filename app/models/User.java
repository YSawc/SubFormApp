package models;

import javax.persistence.*;
import java.util.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import play.data.validation.Constraints;

import static play.mvc.Controller.session;

@Entity
public class User extends Model{

    @Id
    public Integer id;
    @Constraints.Required
    @Constraints.Pattern(value = "^[a-zA-Z0-9]*$", message = "ローマ字と半角英数字を利用できます")
    @Constraints.MinLength(4)
    @Constraints.MaxLength(20)
    public String name;
    @Constraints.Required
    @Constraints.Pattern(value = "^[a-zA-Z0-9_-]*$", message = "ローマ字を利用してください。記号は(_,-)だけ利用できます")
    @Constraints.MinLength(4)
    @Constraints.MaxLength(20)
    @Column(unique = true)
    public String userID;
    @Constraints.Required
    @Constraints.Pattern(value = "^[a-zA-Z0-9]*$", message = "ローマ字と半角英数字を利用できます\"")
    @Constraints.MinLength(4)
    @Constraints.MaxLength(8)
    public String password;
    @Constraints.Required
    @Constraints.MaxLength(100)
    @Constraints.Email
    public String email;
    @Constraints.Required
    public boolean private_or;

    public static Model.Finder<Integer, User> find = new Model.Finder<>(User.class);

    @OneToMany(cascade = CascadeType.ALL)
    public List<Tweet> tweets = new ArrayList<>();

    @ManyToOne
    private Follow Follow;

    public String getName(){
        return this.name;
    }

    public String getUserName(){
        return this.userID;
    }

    public List<Tweet> getTweets(){
        return this.tweets;
    }

    public Follow getFollow() {
        return Follow;
    }

    public void setFollow(Follow follow) {
        this.Follow = follow;
    }

    public List<Integer> get_this_Followed_list(Integer id){

        User user = User.find.byId(id);

        String sql = "SELECT be_followed_id FROM follow WHERE follow_id="
                + id;
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> result = sqlQuery.findList();
        List<Integer> tables = new ArrayList<Integer>();

//        System.out.println(result + "  resultの出力");

        if(result.size() > 0){
            result.forEach(sqlRow ->{
                tables.add(Integer.parseInt(sqlRow.getString("be_followed_id")));
            });

            //デバッグ用
//            System.out.println(tables + "  tablesの出力");
//            for(Integer table : tables){
//                System.out.println(table + "  tableの出力");
//            }
        }

        return tables;
    }

    public List<Integer> get_this_beFollowed_list(Integer id){

        User user = User.find.byId(id);

        String sql = "SELECT follow_id FROM follow WHERE be_followed_id="
                + id;
        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        List<SqlRow> result = sqlQuery.findList();
        List<Integer> tables = new ArrayList<Integer>();

//        System.out.println(result + "  resultの出力");

        if(result.size() > 0){
            result.forEach(sqlRow ->{
                tables.add(Integer.parseInt(sqlRow.getString("follow_id")));
            });

            //デバッグ用
//            System.out.println(tables + "  tablesの出力");
//            for(Integer table : tables){
//                System.out.println(table + "  tableの出力");
//            }
        }

        return tables;
    }

    public boolean get_whitch_follow_or(Integer id){
        User user_beFollowed = User.find.byId(id);
        User user_doneFollow = User.find.byId(Integer.parseInt(session("id")));

        //SQL処理用の文字列
        String sql = "SELECT DISTINCT follow_id FROM follow WHERE follow_id="
                + user_doneFollow.id + " AND be_followed_id=" + user_beFollowed.id ;

        //フォローされているかどうかを判別し、されていればフォローを外すメソッド
        //カラムが存在しているかどうか判別
        if (Ebean.createSqlQuery(sql).findList().size() == 1){
            return false;
        }

        return true;
    }

    public boolean get_whitch_follow_or(Integer id, Integer myID){
        User user_beFollowed = User.find.byId(id);
        User user_doneFollow = User.find.byId(myID);

        //SQL処理用の文字列
        String sql = "SELECT DISTINCT follow_id FROM follow WHERE follow_id="
                + user_doneFollow.id + " AND be_followed_id=" + user_beFollowed.id ;

        //フォローされているかどうかを判別し、されていればフォローを外すメソッド
        //カラムが存在しているかどうか判別
        if (Ebean.createSqlQuery(sql).findList().size() == 1){
            return false;
        }

        return true;
    }
}
