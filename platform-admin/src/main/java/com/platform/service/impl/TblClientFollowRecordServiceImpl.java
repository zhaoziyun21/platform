package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.dao.TblClientDao;
import com.platform.dao.TblClientFollowRecordDao;
import com.platform.dao.TblClientTelRecordDao;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientFollowRecord;
import com.platform.entity.TblClientTelRecord;
import com.platform.service.TblClientFollowRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户跟进记录表 服务实现类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
@Service
public class TblClientFollowRecordServiceImpl implements TblClientFollowRecordService {
    @Autowired
    private TblClientFollowRecordDao tblClientFollowRecordDao;
    @Autowired
    private TblClientTelRecordDao tblClientTelRecordDao;
    @Autowired
    private TblClientDao tblClientDao;
    @Override
    public PageUtilsPlus queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        Page<TblClientFollowRecord> page = new QueryPlus<TblClientFollowRecord>(params).getPage();
        return new PageUtilsPlus(page.setRecords(tblClientFollowRecordDao.selectTblClientFollowRecordPage(page, params)));
    }

    @Override
    @Transactional
    public void saveFollowRecord(TblClientFollowRecord tblClientFollowRecord) {
        Map<String, Object> params = new HashMap<>();
        params.put("clientId",tblClientFollowRecord.getClientId());
        TblClient tblClient = tblClientDao.queryObject(tblClientFollowRecord.getClientId());
        //有客户记录   更新更新时间
        if(tblClient != null){
            tblClient.setClientManagerId(tblClientFollowRecord.getClientManagerId());
            tblClient.setUpdateUser(tblClientFollowRecord.getClientManagerName());
            tblClient.setFollowTime(new Date());
            tblClientDao.update(tblClient);
            tblClientFollowRecordDao.save(tblClientFollowRecord);
        }
    }

    @Override
    @Transactional
    public void updateFollowRecord(TblClientFollowRecord tblClientFollowRecord) {
        TblClient tblClient = tblClientDao.queryObject(tblClientFollowRecord.getClientId());
        tblClient.setClientManagerId(tblClientFollowRecord.getClientManagerId());
        tblClient.setUpdateUser(tblClientFollowRecord.getClientManagerName());
        tblClient.setFollowTime(new Date());
        tblClientDao.update(tblClient);
        tblClientFollowRecordDao.update(tblClientFollowRecord);
    }

    @Override
    public TblClientFollowRecord queryObject(long id) {
        return tblClientFollowRecordDao.queryObject(id);
    }
}
