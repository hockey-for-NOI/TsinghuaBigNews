package thucst53.usermanager;

import com.orm.SugarRecord;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class Newsabs extends SugarRecord<Newsabs> {
    User owner;
    String jsonstr;

    public Newsabs() {
    }

    public Newsabs(User owner, String jsonstr) {
        this.owner = owner;
        this.jsonstr = jsonstr;
    }

    public static ArrayList<Newsabs> userGrab(User owner, String grabRes) {
        ArrayList<Newsabs> result = new ArrayList<Newsabs>();
        try {
            JSONObject obj = new JSONObject(grabRes);
            JSONArray arr = obj.optJSONArray("list");
            for (int i=0; i<arr.length(); i++) {
                JSONObject subObj = arr.getJSONObject(i);
                Newsabs convAbs = new Newsabs(owner, subObj.toString());
                result.add(convAbs);
            }
       }
        catch (Exception e) {
            result = new ArrayList<Newsabs>();
        }
        finally {
            for (Newsabs i: result) i.save();
            return result;
        }
    }

    public static   ArrayList<Newsabs> getCachedAbstract(User owner) {
        List<Newsabs> lis = Newsabs.find(Newsabs.class, "owner = ?", owner.getId().toString());
        ArrayList<Newsabs> alis = new ArrayList<Newsabs>(lis);
        return alis;
    }

    public static   int clearCachedAbstract(User owner) {
        List<Newsabs> lis = Newsabs.find(Newsabs.class, "owner = ?", owner.getId().toString());
        for (Newsabs i: lis) i.delete();
        return lis.size();
    }

}
