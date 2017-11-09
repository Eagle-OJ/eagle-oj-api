package org.inlighting.oj.web.security;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Smith
 **/
public class ShiroFilter  extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        if (authorization == null) {
            sendRedirect(request, response);
            return false;
        }

        try {
            JWToken token = new JWToken(authorization);
            getSubject(request, response).login(token);
            return true;
        } catch (Exception e) {
            sendRedirect(request, response);
            return false;
        }
    }

    private void sendRedirect(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.sendRedirect("/401");
    }
}
