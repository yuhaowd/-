package com.zshs.messagecenter.service.impl;

import com.zshs.messagecenter.entity.Message;
import com.zshs.messagecenter.enums.TemplateEnum;
import com.zshs.messagecenter.mapper.MessageMapper;
import com.zshs.messagecenter.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zshs.messagecenter.util.SnowflakeIdGenerator;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 余浩
 * @since 2023-11-22
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Resource
    JavaMailSender javaMailSender;

    @Override
    public Boolean sendMessageByMail(Map<String, String> params, TemplateEnum templateEnum) {

        // 获取消息模板并填充消息
        StrSubstitutor sub = new StrSubstitutor(params);
        String messageContent = sub.replace(templateEnum.getContent());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(params.get("from"));
        message.setTo(params.get("to"));
        message.setSubject(params.get("subject"));
        message.setText(messageContent);


        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }

        // 将消息存入数据库
        Message mailMessage = new Message();

        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(5, 3);

        mailMessage.setId(snowflakeIdGenerator.nextId());
        mailMessage.setChannel("mailBox");
        mailMessage.setType("mail");
        mailMessage.setContent(messageContent);
        mailMessage.setSendTime(LocalDateTime.now());
        mailMessage.setSender(params.get("from"));
        mailMessage.setSender(params.get("from"));
        mailMessage.setReceiver(params.get("to"));

        try {
            this.save(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return true;
    }
}
