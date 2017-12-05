package org.inlighting.oj.web.util;

import com.github.pagehelper.PageRowBounds;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
public class WebUtil {
    public static Map<String, Object> generatePageData(PageRowBounds pager, Object data) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("page", pager.getOffset());
        map.put("page_size", pager.getLimit());
        map.put("page_count", pager.getTotal());
        map.put("data", data);
        return map;
    }
}