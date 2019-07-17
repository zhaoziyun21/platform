package com.platform.controller;

import com.platform.annotation.SysLog;
import com.platform.entity.SysUserEntity;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientFollowRecord;
import com.platform.service.TblClientFollowRecordService;
import com.platform.service.TblClientService;
import com.platform.util.UserUtil;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.R;
import com.platform.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 系统日志Controller
 *
 * @author 赵子云
 * @email 939961241@qq.com
 * @date 2017-03-08 10:40:56
 */
@RestController
@RequestMapping("/clientFollowRecord")
public class ClientFollowRecordController {
    @Autowired
    private TblClientFollowRecordService tblClientFollowRecordService;

    /**
     * 客户管理列表
     *
     * @param params 请求参数
     * @return R
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("client:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据查询列表数据
        PageUtilsPlus pageUtil = tblClientFollowRecordService.queryPage(params);
        R page = R.ok().put("page", pageUtil);
        return page;
    }

    /**
     * 保存客户跟踪记录
     */
    @SysLog("保存客户跟踪记录")
    @RequestMapping("/save")
    @RequiresPermissions("client:save")
    public R save(@RequestBody TblClientFollowRecord tblClientFollowRecord) {
        ValidatorUtils.validateEntity(tblClientFollowRecord);
        SysUserEntity user = UserUtil.getCurUser();
        tblClientFollowRecord.setClientManagerId(user.getUserId());
        tblClientFollowRecord.setCreateTime(new Date());
        tblClientFollowRecord.setClientManagerName(user.getUsername());
        tblClientFollowRecordService.saveFollowRecord(tblClientFollowRecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("client:update")
    public R update(@RequestBody TblClientFollowRecord tblClientFollowRecord) {
        ValidatorUtils.validateEntity(tblClientFollowRecord);
        tblClientFollowRecordService.updateFollowRecord(tblClientFollowRecord);
        return R.ok();
    }
    /**
     * 获取客户跟单信息
     *
     * @return
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("client:list")
    public R info(@PathVariable("id") int id) {
        TblClientFollowRecord tblClientFollowRecord = tblClientFollowRecordService.queryObject(id);
        return R.ok().put("tblClientFollowRecord", tblClientFollowRecord);
    }
}
