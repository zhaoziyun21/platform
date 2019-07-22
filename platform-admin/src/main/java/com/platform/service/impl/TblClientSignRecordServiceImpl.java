package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.dao.TblClientSignRecordDao;
import com.platform.entity.TblClientSignRecord;
import com.platform.service.TblClientSignRecordService;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 客户签单记录表 服务实现类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-18
 */
@Service
public class TblClientSignRecordServiceImpl  implements TblClientSignRecordService{
    @Autowired
    private TblClientSignRecordDao tblClientSignRecordDao;
    @Override
    public PageUtilsPlus queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        Page<TblClientSignRecord> page = new QueryPlus<TblClientSignRecord>(params).getPage();
        return new PageUtilsPlus(page.setRecords(tblClientSignRecordDao.selectTblClientSignRecordPage(page, params)));
    }

    @Override
    public void saveSignRecord(TblClientSignRecord tblClientSignRecord) {
        tblClientSignRecordDao.save(tblClientSignRecord);
    }
    @Override
    public void updateSignRecord(TblClientSignRecord tblClientSignRecord) {
        tblClientSignRecordDao.update(tblClientSignRecord);
    }

    @Override
    public TblClientSignRecord queryObject(long id) {
        return tblClientSignRecordDao.queryObject(id);
    }

}
