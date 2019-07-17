package com.platform.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientTelRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文件上传Dao
 *
 * @author 赵子云
 * @email 939961241@qq.com
 * @date 2017-03-25 12:13:26
 */
public interface TblClientTelRecordDao extends BaseDao<TblClientTelRecord> {

    /**
     * 自定义分页查询
     *
     * @param page
     * @param params
     * @return
     */
    List<TblClientTelRecord> selectTblClientTelRecordPage(IPage page, @Param("params") Map<String, Object> params);


    /**
     * 通过客户id去查询原始电话的记录表
     *
     * @param params
     * @return
     */
    TblClientTelRecord selectTblClientTelRecordByClientId(@Param("params") Map<String, Object> params);


    void divide(@Param("params") Map<String, Object> params);

    void updateTelRecord(TblClientTelRecord tblClientTelRecord);

    void batchSave(@Param("records")List<TblClientTelRecord> tblClientTelRecords);

    int updatePublishClient();
}
