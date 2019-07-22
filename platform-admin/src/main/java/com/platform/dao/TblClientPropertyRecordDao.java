package com.platform.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.entity.TblClientPropertyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户签单记录表 Mapper 接口
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-18
 */
public interface TblClientPropertyRecordDao extends  BaseDao<TblClientPropertyRecord>{
    /**
     * 自定义分页查询
     *
     * @param page
     * @param params
     * @return
     */
    List<TblClientPropertyRecord> selectTblClientPropertyRecordPage(IPage page, @Param("params") Map<String, Object> params);
}
