package com.platform.service;

import com.platform.entity.TblClientFollowRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.utils.PageUtilsPlus;

import java.util.Map;

/**
 * <p>
 * 客户跟进记录表 服务类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
public interface TblClientFollowRecordService  {
    /**
     * queryPage
     *
     * @param params
     * @return
     */
    PageUtilsPlus queryPage(Map<String, Object> params);


    void saveFollowRecord(TblClientFollowRecord tblClientFollowRecord);

    void updateFollowRecord(TblClientFollowRecord tblClientFollowRecord);

    TblClientFollowRecord queryObject(int id);
}
