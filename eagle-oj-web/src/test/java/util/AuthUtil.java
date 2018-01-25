package util;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.crypto.hash.Md5Hash;
import com.eagleoj.web.WebApplication;
import com.eagleoj.web.util.JWTUtil;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Smith
 **/
public class AuthUtil {

    private static final int UID = 14;

    private static final int ROLE = 0;

    private static final Set<String> PERMISSION = new HashSet<>();

    private static final String EMAIL = "test@test.com";

    private static final String PASSWORD = "123456";

    private static String token = null;

    static {
        token = JWTUtil.sign(UID, ROLE, PERMISSION, new Md5Hash(PASSWORD).toString());
    }

    public static String getToken() {
        return token;
    }
}
