package com.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientTelRecord;
import com.platform.utils.PageUtilsPlus;

import java.util.List;
import java.util.Map;


import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author zhaoziyun
 * @since 2019-07-12
 */
public interface TblClientService {
    /**
     * 团队客户
     *
     * @param params
     * @return
     */
    PageUtilsPlus queryPage(Map<String, Object> params);

    /**
     * 我的客户
     * @param params
     * @return
     */
    PageUtilsPlus queryOwnerPage(Map<String, Object> params);
    PageUtilsPlus publishClientPage(Map<String, Object> params);


    TblClient queryObject(long clientId);

    /**
     * 保存客戶
     */
    void save(TblClient client);

    void saveClient(TblClient client);

    /**
     * 更新客戶
     */
    void update(TblClient client);

    int updatePublishClient(Long userID,List clientIDs,String realName);

    void secondKill(TblClient client);


    public void divideTelRecord(Map<String, Object> params);

    void batchSave(List<TblClient> tblClients);

    List<TblClient> queryClientByStatus(String status);

    PageUtilsPlus queryClientByManageID(Map<String, Object> params);

    void deleteClient(Long clientID);
}
