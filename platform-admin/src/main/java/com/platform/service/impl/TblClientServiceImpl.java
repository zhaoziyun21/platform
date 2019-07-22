
package com.platform.service.impl;

import com.platform.annotation.DataFilter;
import com.platform.dao.TblClientDao;
import com.platform.dao.TblClientTelRecordDao;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientTelRecord;
import com.platform.service.TblClientService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-12
 */
@Service("tblClientService")
public class TblClientServiceImpl implements TblClientService {
    @Autowired
    private TblClientDao tblClientDao;
    @Autowired
    private TblClientTelRecordDao tblClientTelRecordDao;
    @Override
    @DataFilter(userAlias = "tbl_client.clientManagerId", deptAlias = "sys_user.dept_id")
    public PageUtilsPlus queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        Page<TblClient> page = new QueryPlus<TblClient>(params).getPage();
        return new PageUtilsPlus(page.setRecords(tblClientDao.selectTblClientPage(page, params)));
    }

    @Override
    public TblClient queryObject(long id) {
        return tblClientDao.queryObject(id);
    }

    @Override
    public void save(TblClient client) {
         tblClientDao.save(client);
    }
    @Override
    public void saveClient(TblClient client) {
        Map<String, Object> params = new HashMap<>();
        params.put("clientId",client.getId());
        TblClientTelRecord tblClientTelRecord = tblClientTelRecordDao.selectTblClientTelRecordByClientId(params);
        tblClientDao.save(client);
        //有客户原始记录   更新更新时间
        if(tblClientTelRecord == null){
            tblClientTelRecord = new TblClientTelRecord();
            //没有就插入一条
            tblClientTelRecord.setUpdateUser(client.getClientManagerName());
            tblClientTelRecord.setCreateUser(client.getClientManagerName());
            tblClientTelRecord.setClientManagerId(client.getClientManagerId());
            tblClientTelRecord.setClientId(client.getId());
            tblClientTelRecord.setClientName(client.getClientName());
            tblClientTelRecord.setTel(client.getClientTel());
            tblClientTelRecord.setCreateTime(new Date());
            tblClientTelRecord.setStatus("1");//已分配
            tblClientTelRecordDao.save(tblClientTelRecord);
        }
    }
    @Override
    public void update(TblClient client) {
        tblClientDao.update(client);
    }

    @Override
    public int updatePublishClient() {
        return tblClientDao.updatePublishClient();
    }

    @Override
    public void secondKill(TblClient client) {
        tblClientDao.update(client);
    }

    @Override
    public void divideTelRecord(Map<String, Object> params) {
        tblClientDao.divide(params);
    }

    @Override
    public void batchSave(List<TblClient> tblClients) {
        tblClientDao.batchSave(tblClients);
    }
}
