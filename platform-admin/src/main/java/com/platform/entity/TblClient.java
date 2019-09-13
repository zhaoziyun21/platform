package com.platform.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private Long id;

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
     * 客户类型0:默认  1：重点客户  2：放弃客户
     */
    @TableField("clientType")
    private String clientType = "0";

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
     * 是否上门
     */
    @TableField("isVisit")
    private String isVisit="0";

    /**
     * 是否有社保   0：没有， 1：有
     */
    @TableField("isSocialSecurity")
    private BigDecimal isSocialSecurity=BigDecimal.ZERO;

    /**
     * 社保个缴金额
     */
    @TableField("socialSecurityPay")
    private BigDecimal socialSecurityPay=BigDecimal.ZERO;

    /**
     * 已交社保年限
     */
    @TableField("socialSecurityYears")
    private String socialSecurityYears ="0";

    /**
     * 公积金个缴金额
     */
    @TableField("gjjPay")
    private BigDecimal gjjPay=BigDecimal.ZERO;

    /**
     * 公积金已交年限
     */
    @TableField("gjjYears")
    private String gjjYears="0";

    /**
     * 工资类型
     */
    @TableField("salaryType")
    private String salaryType="0";

    /**
     * 工资类型
     */
    @TableField("salaryMoney")
    private BigDecimal salaryMoney=BigDecimal.ZERO;

    /**
     * 工资联缴年限
     */
    @TableField("salaryYears")
    private Integer salaryYears=0;


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
    /**
     * 状态
     */
    @TableField("status")
    private String status="0";
    /**
     * 跟进时间
     */
    @TableField("followTime")
    private Date followTime;

    /**
     * 签约状态
     */
    @TableField("signStatus")
    private String signStatus="0";
    /**
     * 申请平台
     */
    @TableField("applyPlatform")
    private String applyPlatform;
    /**
     * 申请金额
     */
    @TableField("applyAmount")
    private BigDecimal applyAmount=BigDecimal.ZERO;
    /**
     * 申请时间
     */
    @TableField("applyTime")
    private Date applyTime;
    /**
     * 微粒贷  默认没有0      1有
     */
    @TableField("isParticleLoan")
    private int isParticleLoan=0;
    /**
     * 微粒贷金额
     */
    @TableField("particleLoanAmount")
    private BigDecimal particleLoanAmount=BigDecimal.ZERO;
    /**
     * 默认0  1*  ~  5*
     */
    @TableField("clientStar")
    private String clientStar="0";
    /**
     * 默认0  是否有公积金
     */
    @TableField("isGjj")
    private int isGjj=0;
    /**
     * 默认0  房子类型 0无  1：全款本地房  2：全款外地方  3：按揭本地房  4：按揭外地房
     */
    @TableField("houseType")
    private int houseType=0;
    /**
     * 月供
     */
    @TableField("houseMonthPay")
    private int houseMonthPay=0;
    /**
     * 月供多少月
     */
    @TableField("houseYears")
    private int houseYears=0;
    /**
     * 是否北京车牌   0 默认不是
     */
    @TableField("isBjCarNo")
    private int isBjCarNo=0;
    /**
     * 车的价值
     */
    @TableField("carAmount")
    private BigDecimal carAmount=BigDecimal.ZERO;
    /**
     * 保单年缴
     */
    @TableField("insureBillYearPay")
    private BigDecimal insureBillYearPay=BigDecimal.ZERO;
    /**
     * 保单年缴次数
     */
    @TableField("insureBillYearCount")
    private int insureBillYearCount=0;
    /**
     * 保单月缴
     */
    @TableField("insureBillMonthPay")
    private BigDecimal insureBillMonthPay=BigDecimal.ZERO;
    /**
     * 保单月缴次数
     */
    @TableField("insureBillMonthCount")
    private int insureBillMonthCount=0;
    /**
     * 工作单位性质
     */
    @TableField("workPlace")
    private String workPlace="0";
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 身份证号
     */
    @TableField("idCard")
    private String idCard;
    /**
     * 生日
     */
    @TableField("birthdate")
    private String birthdate;
    /**
     * 性别
     */
    @TableField("sex")
    private String sex="-1";
    /**
     * 省份
     */
    @TableField("province")
    private String province;
    /**
     * 城市
     */
    @TableField("city")
    private String city;
    /**
     * 年龄
     */
    @TableField("age")
    private int age;
    /**
     * 信用卡
     */
    @TableField("creditCard")
    private String creditCard;
    /**
     * 信用卡额度
     */
    @TableField("creditCardAmount")
    private String creditCardAmount;
    /**
     * 是否有房 0无,1、有房无贷款，2有房有贷、
     */
    @TableField("haveHouse")
    private String haveHouse="0";
    /**
     * 是否有车 0：无车产，1、有车无贷，2、有车有贷
     */
    @TableField("haveCar")
    private String haveCar="0";
    /**
     * 是否有保单  默认0没有  1有
     */
    @TableField("haveInsure")
    private String haveInsure="0";
    /**
     * 是否异地  默认0本地  1异地
     */
    @TableField("isOtherPlace")
    private int isOtherPlace = 0;
    /**
     * 客户职业 0：上班 1：企业法人 2：个体户 3：自由职业
     */
    @TableField("clientCareer")
    private int clientCareer;

    private List<TblClientFollowRecord> followRecordList;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
