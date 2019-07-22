package com.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.entity.SysUserEntity;
import com.platform.entity.TblClientTelRecord;
import com.platform.utils.PageUtilsPlus;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户电话记录跟踪表 服务类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
public interface TblClientTelRecordService {
    /**
     * queryPage
     *
     * @param params
     * @return
     */
    PageUtilsPlus queryPage(Map<String, Object> params);

    void divideTelRecord(Map<String, Object> params);

    void batchSave(List<TblClientTelRecord> tblClientTelRecords);

    int updatePublishClient();

    void secondKill(TblClientTelRecord tblClientTelRecord);

    TblClientTelRecord queryObject(long id);
}
