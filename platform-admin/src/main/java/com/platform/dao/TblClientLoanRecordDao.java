package com.platform.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.entity.TblClientLoanRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户贷款记录 Mapper 接口
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
public interface TblClientLoanRecordDao extends BaseDao<TblClientLoanRecord> {

    List<TblClientLoanRecord> selectTblClientLoanRecordPage(IPage page, @Param("params") Map<String, Object> params);

}
