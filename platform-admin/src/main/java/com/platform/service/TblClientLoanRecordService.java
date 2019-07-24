package com.platform.service;

import com.platform.entity.TblClientLoanRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.utils.PageUtilsPlus;

import java.util.Map;

/**
 * <p>
 * 客户贷款记录 服务类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
public interface TblClientLoanRecordService {

    PageUtilsPlus queryPage(Map<String, Object> params);

    TblClientLoanRecord queryObject(long id);

    void saveClientLoanRecord(TblClientLoanRecord tblClientLoanRecord);

    void updateClientLoanRecord(TblClientLoanRecord tblClientLoanRecord);
}
