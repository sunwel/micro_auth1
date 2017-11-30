package com.enlink.authcenter.auth.controller;

import com.enlink.authcenter.AuthCenterApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by someone on 2017/11/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthCenterApplication.class)
public class SecretControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(SecretController.class);

    //@Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        //MockMvcBuilders使用构建MockMvc对象   （项目拦截器有效）
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        //单个类  拦截器无效
        // mockMvc = MockMvcBuilders.standaloneSteup(userController).build();
    }


    @Test
    public void getSecretList() throws Exception {
        String url = "/auth/secret";
        RequestBuilder request = MockMvcRequestBuilders.get(url)
                .header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0X3NlcnZlciIsImV4cCI6MTUxMDczMzE2OCwidG9rZW4iOiJ7XCJjbGllbnRJZFwiOlwidGVzdF9zZXJ2ZXJcIixcImNsaWVudElwXCI6XCIxMjcuMC4wLjFcIixcImNsaWVudFR5cGVcIjoyLFwiZXhwaXJlVGltZVwiOjE1MTA3MzMxNjg5NDksXCJwcml2aWxlZ2VNYXBcIjp7XCJtaWNyb19hdXRoXCI6W1wic2VjcmV0XCJdfX0ifQ.P6_6h_EzZSBx8ELjNq-3ZORBvqpy7ke_pKI6ouoQBj8EaXo72gXZ69NM0mm72TbG67fWCrIkBsAxE0ApenAMlA")
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals("返回成功", 200, status);
        logger.info("content=" + content);
    }
}