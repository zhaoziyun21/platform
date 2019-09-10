package com.platform.task;

import com.platform.bean.UserClientBean;
import com.platform.entity.SysUserEntity;
import com.platform.entity.TblClient;
import com.platform.service.SysUserService;
import com.platform.service.TblClientService;
import com.platform.service.TblClientTelRecordService;
import com.platform.utils.DateUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.util.*;

/**
 * <p>
 * testTask为spring bean的名称
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年11月30日 下午1:34:24
 */
@Component("clientTask")
public class ClientTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TblClientService tblClientService;
    @Autowired
    private SysUserService sysUserService;

    public void updatePublishClient() {
        logger.info("定时分配员工给员工，当前时间为：" + DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
        Map<Long,UserClientBean> map = new HashMap<>();
        //查询所有员工放入list ，随机排序
        List<SysUserEntity> userList = sysUserService.queryAllOnJobUser();
        Collections.shuffle(userList);
        //查询所有的新对接客户
        List<TblClient> clientList = tblClientService.queryClientByStatus("0");
        if(clientList != null && clientList.size() > 0){
            for(int i=0; i<= clientList.size() -1;i++){
                int index = i % userList.size();
                Long userID = userList.get(index).getUserId();
                String userName = userList.get(index).getRealName();

                Long clientID = clientList.get(i).getId();
                if(map.get(userID) != null){
                    UserClientBean userClientBean = map.get(userID);
                    List<Long> list = userClientBean.getClientIds();
                    list.add(clientID);
                    map.put(userID,userClientBean);
                }else{
                    UserClientBean userClientBean = new UserClientBean();
                    List<Long> list = new ArrayList<>();
                    list.add(clientID);
                    userClientBean.setClientIds(list);
                    userClientBean.setUserRealName(userName);
                    map.put(userID,userClientBean);
                }
            }
        }
        //批量更新给员工
        if(map.size() > 0){
            int rows = 0;
            for(Map.Entry<Long,UserClientBean> entry : map.entrySet()){
                rows += tblClientService.updatePublishClient(entry.getKey(),entry.getValue().getClientIds(),entry.getValue().getUserRealName());
            }
            logger.info("定时分配员工给员工，受影响更新条数："+ rows +",当前时间为：" + DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
        }
    }
}
