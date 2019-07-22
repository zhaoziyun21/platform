package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.dao.TblClientLoanRecordDao;
import com.platform.dao.TblClientPropertyRecordDao;
import com.platform.entity.TblClientLoanRecord;
import com.platform.entity.TblClientPropertyRecord;
import com.platform.service.TblClientPropertyRecordService;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 客户资产记录表 服务实现类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-18
 */
@Service
public class TblClientPropertyRecordServiceImpl implements TblClientPropertyRecordService {
    @Autowired
    private TblClientPropertyRecordDao tblClientPropertyRecordDao;
    @Override
    public PageUtilsPlus queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        Page<TblClientPropertyRecord> page = new QueryPlus<TblClientPropertyRecord>(params).getPage();
        return new PageUtilsPlus(page.setRecords(tblClientPropertyRecordDao.selectTblClientPropertyRecordPage(page, params)));
    }

    @Override
    public TblClientPropertyRecord queryObject(long id) {
        return tblClientPropertyRecordDao.queryObject(id);
    }

    @Override
    public void saveClientPropertyRecord(TblClientPropertyRecord tblClientPropertyRecord) {
        tblClientPropertyRecordDao.save(tblClientPropertyRecord);
    }

    @Override
    public void updateClientPropertyRecord(TblClientPropertyRecord tblClientPropertyRecord) {
        tblClientPropertyRecordDao.update(tblClientPropertyRecord);
    }
}
