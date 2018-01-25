package com.eagleoj.web.postman;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Smith
 **/
public class MessageTemplate {
    private final static String CLOSE_CONTEST = "<a href=\"/#/contest/[%cid%]\">[%name%]</a>比赛已经结束，你获得了第[%rank%]名，查看<a href=\"/#/contest/[%cid%]/leaderboard\">排行榜</a>";

    private final static String USER_PULLED_INTO_CONTEST = "你被<a href=\"/#/group/[%gid%]\">[%group%]</a>小组拉入了<a href=\"/#/contest/[%cid%]\">[%contest%]</a>比赛";

    private final static String SEND_GROUP_USER_MESSAGE = "<a href=\"/#/group/[%gid%]\">[%group%]</a>小组发送通知：[%message%]";

    private final static String SEND_PROBLEM_ACCEPTED_MESSAGE = "您的<a href=\"/#/problem/[%pid%]\">[%title%]</a>题目已经审核通过";

    private final static String SEND_PROBLEM_REFUSED_MESSAGE = "您的<a href=\"/#/problem/[%pid%]\">[%title%]</a>题目审核不通过，请修改后再次申请审核";

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

    public static String generateUserPulledIntoContestMessage(String contestName, int cid, String groupName, int gid) {
        return USER_PULLED_INTO_CONTEST
                .replace("[%gid%]", String.valueOf(gid))
                .replace("[%group%]", groupName)
                .replace("[%cid%]", String.valueOf(cid))
                .replace("[%contest%]", contestName);
    }

    public static String generateSendGroupUserMessage(int gid, String groupName, String message) {
        return SEND_GROUP_USER_MESSAGE
                .replace("[%gid%]", String.valueOf(gid))
                .replace("[%group%]", groupName)
                .replace("[%message%]", message);
    }

    public static String generateSendProblemAcceptedMessage(String title, int pid) {
        return SEND_PROBLEM_ACCEPTED_MESSAGE
                .replace("[%pid%]", String.valueOf(pid))
                .replace("[%title%]", title);
    }

    public static String generateSendProblemRefusedMessage(String title, int pid) {
        return SEND_PROBLEM_REFUSED_MESSAGE
                .replace("[%pid%]", String.valueOf(pid))
                .replace("[%title%]", title);
    }
}
