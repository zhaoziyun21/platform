package com.platform.controller;

import com.platform.annotation.SysLog;
import com.platform.entity.SysUserEntity;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientTelRecord;
import com.platform.service.TblClientService;
import com.platform.service.TblClientTelRecordService;
import com.platform.util.UserUtil;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.R;
import com.platform.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 系统日志Controller
 *
 * @author 赵子云
 * @email 939961241@qq.com
 * @date 2017-03-08 10:40:56
 */
@RestController
@RequestMapping("/clientTelRecord")
public class ClientTelRecordController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TblClientTelRecordService tblClientTelRecordService;

    /**
     * 客户电话跟踪列表
     *
     * @param params 请求参数
     * @return R
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据查询列表数据
        PageUtilsPlus pageUtil = tblClientTelRecordService.queryPage(params);
        R page = R.ok().put("page", pageUtil);
        return page;
    }
    /**
     * （原始客户）记录分配
     *
     * @return R
     */
    @ResponseBody
    @RequestMapping("/divide")
    @RequiresPermissions("clientTelRecord:divide")
    public R divide(@RequestParam Map<String, Object> params) {
        SysUserEntity user = UserUtil.getCurUser();
        params.put("updateUser",user.getUsername());
        params.put("createUser",user.getUsername());
        params.put("status","1");
        params.put("createTime",new Date());
        tblClientTelRecordService.divideTelRecord(params);
        return R.ok();
    }


    /**
     * 批量保存原始客户
     */
    @SysLog("批量保存原始客户记录")
    @RequestMapping("/save")
    @RequiresPermissions("clientTelRecord:save")
    public R save(@RequestBody List<TblClientTelRecord> tblClientTelRecords) {
        ValidatorUtils.validateEntity(tblClientTelRecords);
        SysUserEntity user = UserUtil.getCurUser();
        tblClientTelRecords.forEach(tblClientTelRecord -> {
            tblClientTelRecord.setCreateTime(new Date());
            tblClientTelRecord.setCreateUser(user.getUsername());
            tblClientTelRecord.setUpdateUser(user.getUsername());
            tblClientTelRecord.setStatus("0");
        });
        tblClientTelRecordService.batchSave(tblClientTelRecords);
        return R.ok();
    }

    /**
     * 抢夺原始客户
     */
    @SysLog("抢夺原始客户记录")
    @RequestMapping("/secondKill")
    public R secondKill(@RequestBody TblClientTelRecord tblClientTelRecord) {
        ValidatorUtils.validateEntity(tblClientTelRecord);
        SysUserEntity user = UserUtil.getCurUser();
        tblClientTelRecord.setStatus("1");
        tblClientTelRecord.setUpdateUser(user.getUsername());
        tblClientTelRecord.setClientManagerId(user.getUserId());
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            TblClientTelRecord tblClientTelRecordNew = tblClientTelRecordService.queryObject(tblClientTelRecord.getId());
            if(!"2".equals(tblClientTelRecordNew.getStatus())){
                return R.error();
            }
            tblClientTelRecordService.secondKill(tblClientTelRecord);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return R.error();
        }finally {
            lock.unlock();
        }

        return R.ok();
    }
}
