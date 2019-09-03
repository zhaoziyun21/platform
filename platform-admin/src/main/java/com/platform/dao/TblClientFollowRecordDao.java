package com.platform.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.entity.TblClientFollowRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户跟进记录表 Mapper 接口
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
public interface TblClientFollowRecordDao extends  BaseDao<TblClientFollowRecord> {

    /**
     * 自定义分页查询
     *
     * @param page
     * @param params
     * @return
     */
    List<TblClientFollowRecord> selectTblClientFollowRecordPage(IPage page, @Param("params")Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param params
     * @return
     */
    List<TblClientFollowRecord> selectTblClientFollowRecordPageLimit(@Param("params")Map<String, Object> params);
}
