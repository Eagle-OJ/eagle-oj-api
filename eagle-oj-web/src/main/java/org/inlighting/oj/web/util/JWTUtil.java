package org.inlighting.oj.web.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.security.UserSession;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Smith
 **/
public class JWTUtil {

    private static final long EXPIRE = 7*24*60*60*1000;

    public static boolean decode(String token, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            int uid = decodedJWT.getClaim("uid").asInt();
            int role = decodedJWT.getClaim("role").asInt();
            Set<String> permission = new HashSet<>(decodedJWT.getClaim("permission").asList(String.class));
            SessionHelper.init(uid, role, permission);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String sign(int uid, int role, Set<String> permission, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis()+EXPIRE);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("uid", uid)
                    .withClaim("role", role)
                    .withArrayClaim("permission", permission.toArray(new String[1]))
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getUid(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim("uid").asInt();
        } catch (JWTDecodeException e) {
            return 0;
        }
    }

    /*public static void main(String[] args) {
        Set<String> per = new HashSet<>();
        per.add("teacher");
        per.add("student");
        String token = sign(1, 1, per, "123");
        System.out.println(token);
        System.out.println(decode(token, "123"));
        UserSession session = SessionHelper.get();
        System.out.println(session.getUid()+";"+session.getRole()+";"+session.getPermission());
    }*/
}
