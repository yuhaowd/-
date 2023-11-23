package com.zshs.messagecenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zshs.messagecenter.entity.User_read_sys_outbox;
import com.zshs.messagecenter.mapper.User_read_sys_outboxMapper;
import com.zshs.messagecenter.service.IUser_read_sys_outboxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 余浩
 * @since 2023-11-22
 */
@Service
public class User_read_sys_outboxServiceImpl extends ServiceImpl<User_read_sys_outboxMapper, User_read_sys_outbox> implements IUser_read_sys_outboxService {


    @Override
    public User_read_sys_outbox getUserReadSysOutbox(Long userId) {


        LambdaQueryWrapper<User_read_sys_outbox> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(User_read_sys_outbox::getUserId, userId);

        User_read_sys_outbox one = this.getOne(lambdaQueryWrapper);

        return one;
    }

    @Override
    public Boolean updateLastMessage(Long userId, Long outboxId) {

        User_read_sys_outbox userReadSysOutbox = this.getUserReadSysOutbox(userId);

        userReadSysOutbox.setSysOutboxId(outboxId);

        boolean res = this.updateById(userReadSysOutbox);


        return res;
    }
}
