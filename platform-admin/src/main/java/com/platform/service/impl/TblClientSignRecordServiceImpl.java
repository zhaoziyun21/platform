package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.dao.TblClientDao;
import com.platform.dao.TblClientSignRecordDao;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientSignRecord;
import com.platform.service.TblClientSignRecordService;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    private TblClientDao tblClientDao;
    @Override
    public PageUtilsPlus queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        Page<TblClientSignRecord> page = new QueryPlus<TblClientSignRecord>(params).getPage();
        return new PageUtilsPlus(page.setRecords(tblClientSignRecordDao.selectTblClientSignRecordPage(page, params)));
    }

    @Override
    @Transactional
    public void saveSignRecord(TblClientSignRecord tblClientSignRecord) {
        TblClient tblClient = tblClientDao.queryObject(tblClientSignRecord.getClientId());
        tblClient.setClientManagerId(tblClientSignRecord.getClientManagerId());
        tblClient.setUpdateUser(tblClientSignRecord.getClientManagerName());
        tblClient.setFollowTime(new Date());
        tblClientDao.update(tblClient);
        tblClientSignRecordDao.save(tblClientSignRecord);
    }
    @Override
    @Transactional
    public void updateSignRecord(TblClientSignRecord tblClientSignRecord) {
        TblClient tblClient = tblClientDao.queryObject(tblClientSignRecord.getClientId());
        //有客户记录   更新跟单时间
        if(tblClient != null){
            tblClient.setClientManagerId(tblClientSignRecord.getClientManagerId());
            tblClient.setUpdateUser(tblClientSignRecord.getClientManagerName());
            tblClient.setFollowTime(new Date());
            tblClientDao.update(tblClient);
            tblClientSignRecordDao.update(tblClientSignRecord);
        }

    }

    @Override
    public TblClientSignRecord queryObject(long id) {
        return tblClientSignRecordDao.queryObject(id);
    }

}
