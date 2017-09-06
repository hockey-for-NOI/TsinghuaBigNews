package thucst53.usermanager;

import java.util.ArrayList;
import java.util.List;

import com.orm.SugarRecord;

import org.json.JSONObject;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class Newsdata extends SugarRecord<Newsdata> {
    User owner;
    String jsonstr;

    public Newsdata() {}

    public Newsdata(User owner, String jsonstr) {
        this.owner = owner;
        this.jsonstr = jsonstr;
    }

    public  static  Newsdata    userSave(User owner, String detail) {
        try {
            JSONObject obj = new JSONObject(detail);
            Newsdata data = new Newsdata(owner, detail);
            data.save();
            return data;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static   ArrayList<Newsdata> getSavedDetail(User owner) {
        List<Newsdata> lis = Newsdata.find(Newsdata.class, "owner = ?", owner.getId().toString());
        ArrayList<Newsdata> alis = new ArrayList<Newsdata>(lis);
        return alis;
    }
}