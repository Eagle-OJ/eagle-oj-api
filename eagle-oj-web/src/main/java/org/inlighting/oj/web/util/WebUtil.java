package org.inlighting.oj.web.util;

import com.github.pagehelper.PageRowBounds;
import org.inlighting.oj.web.controller.exception.WebErrorException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
public class WebUtil {
    public static Map<String, Object> generatePageData(PageRowBounds pager, Object data) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("total", pager.getTotal());
        map.put("data", data);
        return map;
    }

    public static void assertNotNull(Object obj, String message) {
        if (obj == null) {
            throw new WebErrorException(message);
        }
    }
}
