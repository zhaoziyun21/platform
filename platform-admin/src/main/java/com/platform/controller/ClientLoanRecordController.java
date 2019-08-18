package com.platform.controller;

import com.platform.annotation.SysLog;
import com.platform.entity.SysUserEntity;
import com.platform.entity.TblClient;
import com.platform.entity.TblClientLoanRecord;
import com.platform.service.TblClientLoanRecordService;
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
@RequestMapping("/clientLoanRecord")
public class ClientLoanRecordController {
    @Autowired
    private TblClientLoanRecordService tblClientLoanRecordService;

    /**
     * 客户已贷款管理列表
     *
     * @param params 请求参数
     * @return R
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据查询列表数据
        PageUtilsPlus pageUtil = tblClientLoanRecordService.queryPage(params);
        R page = R.ok().put("page", pageUtil);
        return page;
    }
    /**
     * 获取客户信息
     *
     * @return
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") long id) {
        TblClientLoanRecord tblClientLoanRecord = tblClientLoanRecordService.queryObject(id);

        return R.ok().put("tblClientLoanRecord", tblClientLoanRecord);
    }


    /**
     * 保存客户贷款记录
     */
    @SysLog("保存客户贷款记录")
    @RequestMapping("/save")
    @RequiresPermissions("client:save")
    public R save(@RequestBody TblClientLoanRecord tblClientLoanRecord) {
        ValidatorUtils.validateEntity(tblClientLoanRecord);
        SysUserEntity user = UserUtil.getCurUser();
        tblClientLoanRecord.setClientManagerId(user.getUserId());
        tblClientLoanRecord.setClientManagerName(user.getRealName());
        tblClientLoanRecord.setCreateTime(new Date());
        tblClientLoanRecord.setCreateUser(user.getUsername());
        tblClientLoanRecord.setUpdateUser(user.getUsername());
        tblClientLoanRecordService.saveClientLoanRecord(tblClientLoanRecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("client:update")
    public R update(@RequestBody TblClientLoanRecord tblClientLoanRecord) {
        ValidatorUtils.validateEntity(tblClientLoanRecord);
        SysUserEntity user = UserUtil.getCurUser();
        tblClientLoanRecord.setClientManagerId(user.getUserId());
        tblClientLoanRecord.setClientManagerName(user.getRealName());
        tblClientLoanRecord.setUpdateUser(user.getUsername());
        tblClientLoanRecordService.updateClientLoanRecord(tblClientLoanRecord);
        return R.ok();
    }
}
