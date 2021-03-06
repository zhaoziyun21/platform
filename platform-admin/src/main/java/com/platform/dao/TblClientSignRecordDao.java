package com.platform.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.entity.TblClientFollowRecord;
import com.platform.entity.TblClientSignRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface TblClientSignRecordDao extends  BaseDao<TblClientSignRecord>{
    /**
     * 自定义分页查询
     *
     * @param page
     * @param params
     * @return
     */
    List<TblClientSignRecord> selectTblClientSignRecordPage(IPage page, @Param("params")Map<String, Object> params);
}
