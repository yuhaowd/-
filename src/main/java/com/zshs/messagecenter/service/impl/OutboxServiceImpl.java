package com.zshs.messagecenter.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.zshs.messagecenter.entity.Outbox;
import com.zshs.messagecenter.entity.User_read_sys_outbox;
import com.zshs.messagecenter.mapper.OutboxMapper;
import com.zshs.messagecenter.service.IOutboxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zshs.messagecenter.service.IUser_read_sys_outboxService;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 余浩
 * @since 2023-11-22
 */
@Service
public class OutboxServiceImpl extends ServiceImpl<OutboxMapper, Outbox> implements IOutboxService {

    @Resource
    private IUser_read_sys_outboxService userReadSysOutboxService;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public List<Outbox> getNotReadMessage(Long userId) {
        // 获取用户读取的最后一条消息
        User_read_sys_outbox userLastReadMessage = userReadSysOutboxService.getUserReadSysOutbox(userId);

        // 获取此消息的创建时间和之后创建的消息

        Long sysOutboxId = userLastReadMessage.getSysOutboxId();

        Outbox lastReadMessage = this.getById(sysOutboxId);

        List<Outbox> list = this.list();


        List<Outbox> res = list.stream()

                .filter(item -> {
                    return item.getCreateTime().isAfter(lastReadMessage.getCreateTime());
                })
                // 按照从小到大排序
                .sorted(
                        (t1, t2) -> {
                            return t1.getCreateTime().compareTo(t2.getCreateTime());
                        }
                ).collect(Collectors.toList());
        return res;
    }

    @Override
    public List<Outbox> getAllSysMessage(Long userId) {


        // 从redis中获取
        RList<Object> list = redissonClient.getList(String.valueOf(userId));


        if (list.readAll().size() > 0) {
            List<Outbox> res = list.readAll().stream()
                    .map(item -> JSONUtil.toBean(String.valueOf(item), Outbox.class))
                    .collect(Collectors.toList());
            return res;
        }



        List<Outbox> res = this.list().stream().sorted((t1, t2) -> {
            return t1.getCreateTime().compareTo(t2.getCreateTime());
        }).collect(Collectors.toList());

        for (Outbox item : res) {

            String jsonStr = JSONUtil.toJsonStr(item);
            list.add(jsonStr);
        }
        list.expire(30, TimeUnit.MINUTES);


        return res;
    }


}
