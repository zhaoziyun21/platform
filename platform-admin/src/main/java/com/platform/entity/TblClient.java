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
 * 客户表
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TblClient extends Model<TblClient> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户姓名
     */
    @TableField("clientName")
    private String clientName;

    /**
     * 手机号
     */
    @TableField("clientTel")
    private String clientTel;

    /**
     * 客户类型
     */
    @TableField("clientType")
    private String clientType;

    /**
     * 客户经理id
     */
    @TableField("clientManagerId")
    private Long clientManagerId;

    /**
     * 客户经理姓名
     */
    @TableField("clientManagerName")
    private String clientManagerName;

    /**
     * 未跟单天数
     */
    @TableField("noTrackOrder")
    private Integer noTrackOrder;

    /**
     * 是否上门
     */
    @TableField("isVisit")
    private String isVisit;

    /**
     * 社保个缴金额
     */
    @TableField("socialSecurityPay")
    private BigDecimal socialSecurityPay;

    /**
     * 已交社保年限
     */
    @TableField("socialSecurityYears")
    private Integer socialSecurityYears;

    /**
     * 公积金个缴金额
     */
    @TableField("gjjPay")
    private BigDecimal gjjPay;

    /**
     * 公积金已交年限
     */
    @TableField("gjjYears")
    private Integer gjjYears;

    /**
     * 工资类型
     */
    @TableField("salaryType")
    private String salaryType;

    /**
     * 工资联缴年限
     */
    @TableField("salaryYears")
    private Integer salaryYears;

    /**
     * 是否记入成本
     */
    @TableField("isRecordCost")
    private String isRecordCost;

    /**
     * 成本金额
     */
    private BigDecimal cost;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("actionTime")
    private Date actionTime;
    /**
     * 创建人
     */
    @TableField("createUser")
    private String createUser;
    /**
     * 更新人
     */
    @TableField("updateUser")
    private String updateUser;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
