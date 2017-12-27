package org.inlighting.oj.web.postman;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Smith
 **/
public class MessageTemplate {
    private final static String CLOSE_CONTEST = "<a href=\"/#/contest/[%cid%]\">[%name%]</a>已经结束，你获得了第[%rank%]名，查看<a href=\"/#/contest/[%cid%]/leaderboard\">排行榜</a>";

    public static String generateCloseContestMessage(int cid, String name, int rank) {
        return CLOSE_CONTEST
                .replace("[%cid%]", String.valueOf(cid))
                .replace("[%name%]", name)
                .replace("[%rank%]", String.valueOf(rank));
    }

    public static JSONObject generateOfficialContestClosedMessage(int cid, String name, JSONArray rankList) {
        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put("cid", cid);
        jsonObject.put("name", name);
        jsonObject.put("rank", rankList);
        return jsonObject;
    }
}
