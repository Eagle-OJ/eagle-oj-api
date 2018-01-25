package controller;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import com.eagleoj.web.WebApplication;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import util.AuthUtil;
import util.ReadUtil;

/**
 * @author Smith
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@WebAppConfiguration
@Deprecated
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static String token;

    @BeforeClass
    public static void setToken() {
        System.out.println("this is before all");
        token = AuthUtil.getToken();
    }

    @Before
    public void setUp() {
        Object o = wac.getBean("securityManager");
        DefaultWebSecurityManager manager = (DefaultWebSecurityManager)o ;
        System.out.println(manager.getRealms().isEmpty());
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void addProblemTest() throws Exception {
        String data = ReadUtil.read("/user/add_problem.json");
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user/problem")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(data)
                    .header("Authorization", token)
        ).andReturn().getResponse().getContentAsString();
        System.out.println("result:"+result);
    }
}
