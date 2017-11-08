package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.inlighting.oj.web.WebApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class IndexControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void registerTest() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("email", System.currentTimeMillis()+"@126.com");
        data.put("password", "123456");
        data.put("nickname", System.currentTimeMillis()+"-TESTER");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(data))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void loginTest() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("email", "test@test.com");
        data.put("password", "123456");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(data))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
