package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.entity.TblClientLoanRecord;
import com.platform.dao.TblClientLoanRecordDao;
import com.platform.service.TblClientLoanRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 客户贷款记录 服务实现类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
@Service
public class TblClientLoanRecordServiceImpl  implements TblClientLoanRecordService {
    @Autowired
    private TblClientLoanRecordDao tblClientLoanRecordDao;
    @Override
    public PageUtilsPlus queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        Page<TblClientLoanRecord> page = new QueryPlus<TblClientLoanRecord>(params).getPage();
        return new PageUtilsPlus(page.setRecords(tblClientLoanRecordDao.selectTblClientLoanRecordPage(page, params)));
    }

    @Override
    public TblClientLoanRecord queryObject(long id) {
        return tblClientLoanRecordDao.queryObject(id);
    }

    @Override
    public void saveClientLoanRecord(TblClientLoanRecord tblClientLoanRecord) {
        tblClientLoanRecordDao.save(tblClientLoanRecord);
    }
    @Override
    public void updateClientLoanRecord(TblClientLoanRecord tblClientLoanRecord) {
        tblClientLoanRecordDao.update(tblClientLoanRecord);
    }
}
