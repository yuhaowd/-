package com.zshs.messagecenter;

import com.zshs.messagecenter.enums.TemplateEnum;
import com.zshs.messagecenter.service.IMessageService;
import com.zshs.messagecenter.service.IOutboxService;
import com.zshs.messagecenter.service.IUser_read_sys_outboxService;
import org.apache.commons.lang.text.StrSubstitutor;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MessageCenterApplicationTests {


    @Resource
    private IUser_read_sys_outboxService userReadSysOutboxService;

    @Resource
    private IOutboxService outboxService;

    @Resource
    private RedissonClient redissonClient;

    @Autowired
    JavaMailSender javaMailSender;

    @Resource
    private IMessageService messageService;
    @Test
    void contextLoads() {
    }

    @Test
    void testgetUserReadSysOutbox() {

        long userId = 12345;
        System.out.println(userReadSysOutboxService.getUserReadSysOutbox(userId));

    }
    @Test
    void testgetNotReadMessage() {
        long userId = 12345;
        outboxService.getNotReadMessage(userId);
    }

    @Test
    void testupdateLastMessage() {
        long userId = 12345;
        long outboxId = 5;
        System.out.println(userReadSysOutboxService.updateLastMessage(userId, outboxId));

    }
    @Test
    void tsetgetAllSysMessage() {


        long userId = 12345;
        System.out.println(outboxService.getAllSysMessage(userId));
    }
    @Test
    void testRedisClient() {
        RBucket<Object> bucket = redissonClient.getBucket("yh1");

        System.out.println(bucket.get());

    }


    @Test
    void testSendMessageByMail() {

        Map<String, String> params = new HashMap<>();

        params.put("from", "3406138837@qq.com");
        params.put("to", "2226001563@qq.com");
        params.put("subject", "消息提醒");
        params.put("userName","余浩");
        params.put("cardNumber", "23456777");
        params.put("buildingName","临沂山庄456");

        messageService.sendMessageByMail(params, TemplateEnum.mail_1);
    }

    @Test
    void testMessageTemplate() {

        /*
        您好${userName}，您卡号为${cardNumber} ，地址为${buildingName} ，请注意查收！
         */
        Map<String, Object> params = new HashMap<>();
        params.put("userName","余浩");
        params.put("cardNumber", "23456777");
        params.put("buildingName","临沂山庄456");
        params.put("age","20");
        params.put("sex","男");


        StrSubstitutor sub = new StrSubstitutor(params);
        TemplateEnum mail1 = TemplateEnum.mail_1;
        String template = mail1.getContent();
        String message = sub.replace(template);
        System.out.println(message);


    }


}
