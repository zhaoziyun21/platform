package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.dao.TblClientDao;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientLoanRecord;
import com.platform.dao.TblClientLoanRecordDao;
import com.platform.service.TblClientLoanRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    private TblClientDao tblClientDao;
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
    @Transactional
    public void saveClientLoanRecord(TblClientLoanRecord tblClientLoanRecord) {
        TblClient tblClient = tblClientDao.queryObject(tblClientLoanRecord.getClientId());
        tblClient.setClientManagerId(tblClientLoanRecord.getClientManagerId());
        tblClient.setUpdateUser(tblClientLoanRecord.getClientManagerName());
        tblClient.setFollowTime(new Date());
        tblClientDao.update(tblClient);
        tblClientLoanRecordDao.save(tblClientLoanRecord);
    }
    @Override
    @Transactional
    public void updateClientLoanRecord(TblClientLoanRecord tblClientLoanRecord) {
        TblClient tblClient = tblClientDao.queryObject(tblClientLoanRecord.getClientId());
        //有客户记录   更新跟单时间
        if(tblClient != null){
            tblClient.setClientManagerId(tblClientLoanRecord.getClientManagerId());
            tblClient.setUpdateUser(tblClientLoanRecord.getClientManagerName());
            tblClient.setFollowTime(new Date());
            tblClientDao.update(tblClient);
            tblClientLoanRecordDao.update(tblClientLoanRecord);
        }

    }
}
