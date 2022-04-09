package com.iguojie.rbac.controller;

import com.google.code.kaptcha.Producer;
import com.iguojie.rbac.common.entity.JsonResult;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping(produces = {"application/json;charset=utf-8"})
public class VerificationCodeController {

    @Autowired
    private Producer producer;

    @GetMapping(value = "getVerificationCode.do")
    public JsonResult getVerifyCode(HttpSession session) throws IOException {
        String text = producer.createText();
        session.setAttribute("kaptcha", text);
        BufferedImage bi = producer.createImage(text);
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        ImageIO.write(bi, "jpg", fastByteArrayOutputStream);
        return JsonResult.success("data:image/jpg;base64,"+ Base64.encodeBase64String(fastByteArrayOutputStream.toByteArray()));
    }
}
