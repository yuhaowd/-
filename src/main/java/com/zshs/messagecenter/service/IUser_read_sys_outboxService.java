package com.zshs.messagecenter.service;

import com.zshs.messagecenter.entity.User_read_sys_outbox;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 余浩
 * @since 2023-11-22
 */
public interface IUser_read_sys_outboxService extends IService<User_read_sys_outbox> {


    // 根据用户id查询该用户所查看的最后一条系统消息
    public User_read_sys_outbox getUserReadSysOutbox(Long userId);

    // 更新用户读取的最后一条系统消息
    public Boolean updateLastMessage(Long userId, Long outboxId);
}
