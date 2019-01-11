package models;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Constraint;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Tweet extends Model {
    @Id
//    idは自動生成するので必須にしない
    public Integer id;
    @Constraints.Required
    @Constraints.MaxLength(140)
    public String mutter;

    @CreatedTimestamp
    public Date createdDate;

    @ManyToOne
    public User user;

//    //いいね機能
//    public Integer good;

    public static Finder<Integer, Tweet> find = new Finder<>(Tweet.class);

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    //変換可能ならurlに変更するメソッド
    public static final Pattern convURLLinkPtn =
            Pattern.compile
                    ("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+",
                            Pattern.CASE_INSENSITIVE);

    /**
     * 指定された文字列内のURLを、正規表現を使用し、
     * リンク（a href=...）に変換する。
     * @param str 指定の文字列。
     * @return リンクに変換された文字列。
     */
    public static String convURLLink(String str) {
        Matcher matcher = convURLLinkPtn.matcher(str);
        System.out.println("マッチしました");
        return matcher.replaceAll("<a href=\"$0\">$0</a>");
    }

}
