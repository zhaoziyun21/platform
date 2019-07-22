package com.platform.task;

import com.platform.entity.SysUserEntity;
import com.platform.service.SysUserService;
import com.platform.service.TblClientService;
import com.platform.service.TblClientTelRecordService;
import com.platform.utils.DateUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 测试定时任务(演示Demo，可删除)
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

    public void updatePublishClient() {
        logger.info("定时更新未跟进客户到公海，当前时间为：" + DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
        int rows = tblClientService.updatePublishClient();
        logger.info("定时更新未跟进客户到公海，受影响更新条数："+ rows +",当前时间为：" + DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
    }
}
