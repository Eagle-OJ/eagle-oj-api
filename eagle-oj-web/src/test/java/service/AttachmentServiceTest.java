package service;

import org.inlighting.oj.web.WebApplication;
import org.inlighting.oj.web.dao.AttachmentDao;
import org.inlighting.oj.web.service.AttachmentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author = ygj
 **/
@SpringBootTest(classes = WebApplication.class)
@RunWith(SpringRunner.class)
public class AttachmentServiceTest {

    private AttachmentService  attachmentService;

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @Test
    public void addTest() throws Exception {
       int insertNum = attachmentService.add(1,"testurl");
        Assert.assertEquals(true,insertNum>0);
    }
}
