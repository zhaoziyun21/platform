package com.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户电话记录跟踪表
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TblClientTelRecord extends Model<TblClientTelRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新时间 （有客户经理id以此时间判断是否进入公海）
     */
    @TableField("actionTime")
    private Date actionTime;

    /**
     * 创建者
     */
    @TableField("createUser")
    private String createUser;

    /**
     * 更新者
     */
    @TableField("updateUser")
    private String updateUser;

    /**
     * 关联客户id
     */
    @TableField("clientId")
    private Long clientId;

    /**
     * 关联客户经理id
     */
    @TableField("clientManagerId")
    private Long clientManagerId;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 客户名称
     */
    @TableField("clientName")
    private String clientName;

    /**
     * 客户状态  0：未分配   1：已分配  2：公海
     */
    private String status;
    /**
     * 客户经理姓名
     */
    private String clientManagerName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
