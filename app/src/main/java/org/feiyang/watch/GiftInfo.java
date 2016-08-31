package org.feiyang.watch;

/**
 * Created by momo on 8/29/16.
 */
public class GiftInfo {

    static private String DEFAULT_NAME = "Gift";
    static private String DEFAULT_SCORE = "10";
    static private String DEFAULT_ID = "0";

    public String mName;
    public String mScore;
    public String mId;

    public GiftInfo() {
        mName = DEFAULT_NAME;
        mScore = DEFAULT_SCORE;
        mId = DEFAULT_ID;
    }

    public GiftInfo(String id, String name, String score) {
        mId = id;
        mName = name;
        mScore = score;

    }
}
