package thucst53.usermanager;

import java.util.ArrayList;
import com.orm.SugarRecord;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class Newsdata extends SugarRecord<Newsdata> {
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

    String newscategory;
    String inbornkeywords;
    String newscontent;
    String crawlsource;
    String newsjournal;
    String crawltime;
    String repeatid;
    ArrayList<String> seggedtitle;
    ArrayList<String> seggedcontent;
    ArrayList<String> persons;
    ArrayList<Integer> personscnt;
    ArrayList<String> locations;
    ArrayList<Integer> locationscnt;
    ArrayList<String> organizations;
    ArrayList<String> keywords;
    ArrayList<Double> keywordsscore;
    ArrayList<String> bagofwords;
    ArrayList<Double> bagofwordsscore;
}