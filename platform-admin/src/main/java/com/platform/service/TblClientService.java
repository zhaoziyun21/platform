package com.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.entity.TblClient;
import com.platform.utils.PageUtilsPlus;
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
     * queryPage
     *
     * @param params
     * @return
     */
    PageUtilsPlus queryPage(Map<String, Object> params);


    TblClient queryObject(int clientId);

    /**
     * 保存客戶
     */
    void save(TblClient client);

    void saveClient(TblClient client);

    /**
     * 更新客戶
     */
    void update(TblClient client);
}
