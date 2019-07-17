package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.dao.TblClientTelRecordDao;
import com.platform.entity.SysUserEntity;
import com.platform.entity.TblClientTelRecord;
import com.platform.service.TblClientTelRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户电话记录跟踪表 服务实现类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-14
 */
@Service
public class TblClientTelRecordServiceImpl implements TblClientTelRecordService {
    @Autowired
    private TblClientTelRecordDao tblClientTelRecordDao;
    @Override
    public PageUtilsPlus queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        Page<TblClientTelRecord> page = new QueryPlus<TblClientTelRecord>(params).getPage();
        return new PageUtilsPlus(page.setRecords(tblClientTelRecordDao.selectTblClientTelRecordPage(page, params)));
    }

    @Override
    public void divideTelRecord(Map<String, Object> params) {

        tblClientTelRecordDao.divide(params);
    }
    @Override
    public void batchSave(List<TblClientTelRecord> tblClientTelRecords) {

        tblClientTelRecordDao.batchSave(tblClientTelRecords);
    }
    @Override
    public int updatePublishClient() {
        return tblClientTelRecordDao.updatePublishClient();
    }
    @Override
    public void secondKill(TblClientTelRecord tblClientTelRecord) {
        tblClientTelRecordDao.update(tblClientTelRecord);
    }
    @Override
    public TblClientTelRecord queryObject(int id) {
        return tblClientTelRecordDao.queryObject(id);
    }
}
