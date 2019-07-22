package com.platform.service;

import com.platform.entity.TblClientLoanRecord;
import com.platform.entity.TblClientPropertyRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.utils.PageUtilsPlus;

import java.util.Map;

/**
 * <p>
 * 客户资产记录表 服务类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-18
 */
public interface TblClientPropertyRecordService {
    PageUtilsPlus queryPage(Map<String, Object> params);

    TblClientPropertyRecord queryObject(long id);

    void saveClientPropertyRecord(TblClientPropertyRecord tblClientPropertyRecord);

    void updateClientPropertyRecord(TblClientPropertyRecord tblClientPropertyRecord);
}
