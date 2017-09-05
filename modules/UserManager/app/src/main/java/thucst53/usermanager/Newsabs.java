package thucst53.usermanager;

import com.orm.SugarRecord;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class Newsabs extends SugarRecord<Newsabs> {
    User owner;
    String newsclasstag;
    String newsid;
    String newssource;
    String newstitle;
    String newstime;
    String newsurl;
    String newsauthor;
    String langtype;
    String newspictures;
    String newsvideos;
    String newsintro;
}
