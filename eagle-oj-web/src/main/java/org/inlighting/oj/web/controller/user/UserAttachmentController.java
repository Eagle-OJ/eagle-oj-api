package org.inlighting.oj.web.controller.user;

import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.controller.format.user.UploadAttachmentFormat;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user/attachment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserAttachmentController {

    private AttachmentService attachmentService;

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @ApiOperation("上传附件")
    @PostMapping
    public ResponseEntity uploadAttachment(@RequestBody @Valid UploadAttachmentFormat format) {
        int uid = SessionHelper.get().getUid();
        int aid = attachmentService.add(uid, format.getUrl(), System.currentTimeMillis());
        if (aid == 0) {
            throw new RuntimeException("上传失败");
        }
        return new ResponseEntity("上传成功");
    }
}
