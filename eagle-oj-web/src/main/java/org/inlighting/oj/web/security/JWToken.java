package org.inlighting.oj.web.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Smith
 **/
public class JWToken implements AuthenticationToken {
    private String token;

    public JWToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
