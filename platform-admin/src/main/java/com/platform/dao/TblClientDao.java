package com.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientTelRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 文件上传Dao
 *
 * @author 赵子云
 * @email 939961241@qq.com
 * @date 2017-03-25 12:13:26
 */
public interface TblClientDao extends BaseDao<TblClient> {

    /**
     * 自定义分页查询
     *
     * @param page
     * @param params
     * @return
     */
    List<TblClient> selectTblClientPage(IPage page, @Param("params")Map<String, Object> params);

    int updatePublishClient();

    void divide(@Param("params") Map<String, Object> params);

    void batchSave(@Param("records")List<TblClient> tblClients);
}
