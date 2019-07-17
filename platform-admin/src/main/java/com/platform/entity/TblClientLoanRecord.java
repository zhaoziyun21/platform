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
 * 客户贷款记录
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TblClientLoanRecord extends Model<TblClientLoanRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户id
     */
    @TableField("clientId")
    private Integer clientId;

    /**
     * 客户姓名
     */
    @TableField("clientName")
    private String clientName;

    /**
     * 贷款名称
     */
    @TableField("loanName")
    private String loanName;

    /**
     * 贷款类型
     */
    @TableField("loanType")
    private String loanType;

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
     *
     */
    @TableField("clientManagerId")
    private Long clientManagerId;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
