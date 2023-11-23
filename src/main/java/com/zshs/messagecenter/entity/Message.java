package com.zshs.messagecenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author 余浩
 * @since 2023-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String type;

    private String channel;

    private String content;

    private Integer templateId;

    private String sender;

    private String receiver;

    private LocalDateTime sendTime;

    private LocalDateTime receiveTime;


}
