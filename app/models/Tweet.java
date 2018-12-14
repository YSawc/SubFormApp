package models;
import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Tweet extends Model {
    @Id
//    IDは自動生成するので必須にしない
    public Integer id;
    @Constraints.Required
    @Constraints.MaxLength(140)
    public String mutter;
//    @CreatedTimestamp
//    public Integer createDate;

    @ManyToOne
    public User PostUser;

    public static Finder<Integer, Tweet> find = new Finder<>(Tweet.class);

    public String getPostUserName(){
        return this.PostUser.name;
    }

    public void setPostUserName(String setName){
        this.PostUser.name = setName;
    }
}
