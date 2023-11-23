package com.zshs.messagecenter.service;

import com.zshs.messagecenter.entity.Outbox;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 余浩
 * @since 2023-11-22
 */
public interface IOutboxService extends IService<Outbox> {



    // 获取用户未读的系统消息
    public List<Outbox> getNotReadMessage(Long userId);

    // 获取所有的系统消息
    public List<Outbox> getAllSysMessage(Long userId);








}
