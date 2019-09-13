
package com.platform.service.impl;

import com.platform.annotation.DataFilter;
import com.platform.dao.TblClientDao;
import com.platform.dao.TblClientFollowRecordDao;
import com.platform.dao.TblClientTelRecordDao;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientFollowRecord;
import com.platform.entity.TblClientTelRecord;
import com.platform.service.TblClientService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private TblClientFollowRecordDao tblClientFollowRecordDao;
    @Autowired
    private TblClientTelRecordDao tblClientTelRecordDao;
    @Override
    @DataFilter(userAlias = "tbl_client.clientManagerId", deptAlias = "sys_user.dept_id")
    public PageUtilsPlus queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        List<String> order = new ArrayList<>();
        order.add("status");
        Page<TblClient> page = new QueryPlus<TblClient>(params).getPage();
        List<TblClient> tblClientLists = tblClientDao.selectTblClientPage(page, params);
        for (TblClient tblClient : tblClientLists){
            Map<String, Object> m = new HashMap<>();
            m.put("clientId",tblClient.getId());
            List<TblClientFollowRecord> tblClientFollowRecords = tblClientFollowRecordDao.selectTblClientFollowRecordPageLimit(m);
            tblClient.setFollowRecordList(tblClientFollowRecords);
        }
        return new PageUtilsPlus(page.setAscs(order).setRecords(tblClientLists));
    }
    @Override
    @DataFilter(userAlias = "tbl_client.clientManagerId")
    public PageUtilsPlus queryOwnerPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        List<String> order = new ArrayList<>();
        order.add("status");
        Page<TblClient> page = new QueryPlus<TblClient>(params).getPage();
        List<TblClient> tblClientLists = tblClientDao.selectOwnerTblClientPage(page, params);
        for (TblClient tblClient : tblClientLists){
            Map<String, Object> m = new HashMap<>();
            m.put("clientId",tblClient.getId());
            List<TblClientFollowRecord> tblClientFollowRecords = tblClientFollowRecordDao.selectTblClientFollowRecordPageLimit(m);
            tblClient.setFollowRecordList(tblClientFollowRecords);
        }
        return new PageUtilsPlus(page.setAscs(order).setRecords(tblClientLists));
    }
    public PageUtilsPlus publishClientPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        List<String> order = new ArrayList<>();
        order.add("status");
        Page<TblClient> page = new QueryPlus<TblClient>(params).getPage();
        return new PageUtilsPlus(page.setAscs(order).setRecords(tblClientDao.publishClientPage(page, params)));
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
        tblClientDao.save(client);
    }
    @Override
    public void update(TblClient client) {
        tblClientDao.update(client);
    }

    @Override
    public int updatePublishClient(Long userID,List clientIDs,String realName) {
        return tblClientDao.updatePublishClient(userID,clientIDs,realName);
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

    @Override
    public List<TblClient> queryClientByStatus(String status) {
        return tblClientDao.queryClientByStatus(status);
    }

    @Override
    public PageUtilsPlus queryClientByManageID(Map<String, Object> params) {
        //排序
        params.put("sidx", "createTime");
        params.put("asc", false);
        List<String> order = new ArrayList<>();
        order.add("status");
        Page<TblClient> page = new QueryPlus<TblClient>(params).getPage();
        List<TblClient> tblClientLists = tblClientDao.queryClientByManageID(page, params);
        return new PageUtilsPlus(page.setAscs(order).setRecords(tblClientLists));
    }

    @Override
    public void deleteClient(Long clientID) {
        tblClientDao.delete(clientID);
    }

}
