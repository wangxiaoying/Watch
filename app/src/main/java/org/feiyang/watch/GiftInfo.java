package org.feiyang.watch;

/**
 * Created by momo on 8/29/16.
 */
public class GiftInfo {

    static private String DEFAULT_NAME = "Gift";
    static private String DEFAULT_SCORE = "10";

    public String mName;
    public String mScore;

    public GiftInfo() {
        mName = DEFAULT_NAME;
        mScore = DEFAULT_SCORE;
    }

    public GiftInfo(String name, String score) {
        mName = name;
        mScore = score;
    }
}
