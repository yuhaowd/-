package com.zshs.messagecenter.service;

import com.zshs.messagecenter.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zshs.messagecenter.enums.TemplateEnum;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 余浩
 * @since 2023-11-22
 */
public interface IMessageService extends IService<Message> {

    // 给用户发送邮件
    public Boolean sendMessageByMail(Map<String, String> params, TemplateEnum templateEnum);

}
