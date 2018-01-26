package com.eagleoj.web.util;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import com.eagleoj.web.controller.exception.WebErrorException;

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

    public static Map<String, Object> generatePageData(Page pager, Object data) {
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
