package org.inlighting.oj.web.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.ehcache.Cache;
import org.inlighting.oj.web.cache.CacheController;
import org.inlighting.oj.web.util.JWTUtil;

/**
 * @author Smith
 **/
public class Realm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserSession session = SessionHelper.get();
        if (session==null) {
            return null;
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(String.valueOf(session.getRole()));
        info.addStringPermissions(session.getPermission());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        Cache<String, String> authCache = CacheController.getAuthCache();
        if (! authCache.containsKey(token)) {
            throw new AuthenticationException("Can't find in authCache");
        }

        String secret = authCache.get(token);
        if (!JWTUtil.decode(token, secret)) {
            throw new AuthenticationException("Token invalid");
        }

        return new SimpleAuthenticationInfo(token, secret, "jwt_realm");
    }
}
