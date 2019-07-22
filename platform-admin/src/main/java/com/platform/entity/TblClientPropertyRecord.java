package com.platform.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * 客户资产记录表
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TblClientPropertyRecord extends Model<TblClientPropertyRecord> {

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
    private Integer clientId;

    /**
     * 关联客户经理id
     */
    @TableField("clientManagerId")
    private Long clientManagerId;

    /**
     * 资产类型
     */
    @TableField("propertyType")
    private String propertyType;

    /**
     * 资产名
     */
    @TableField("propertyName")
    private String propertyName;

    /**
     * 购买日期
     */
    @TableField("buyTime")
    private Date buyTime;

    /**
     * 资产金额
     */
    @TableField("propertyAmount")
    private BigDecimal propertyAmount;
    /**
     *
     */
    @TableField("clientManagerName")
    private String clientManagerName;
    /**
     *
     */
    @TableField("remark")
    private String remark;
    @TableField("clientName")
    private String clientName;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
