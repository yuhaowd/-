package com.zshs.messagecenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    public class User_read_sys_outbox implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.ASSIGN_ID)
      private Long id;

    private Long sysOutboxId;

    private Long userId;


}
