package com.platform.service;

import com.platform.entity.TblClientSignRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.utils.PageUtilsPlus;

import java.util.Map;

/**
 * <p>
 * 客户签单记录表 服务类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-18
 */
public interface TblClientSignRecordService  {
    /**
     * queryPage
     *
     * @param params
     * @return
     */
    PageUtilsPlus queryPage(Map<String, Object> params);

    void saveSignRecord(TblClientSignRecord tblClientSignRecord);

    void updateSignRecord(TblClientSignRecord tblClientSignRecord);

    TblClientSignRecord queryObject(long id);
}
