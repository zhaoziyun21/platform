package com.platform.controller;

import com.platform.annotation.SysLog;
import com.platform.entity.SysUserEntity;
import com.platform.entity.TblClientPropertyRecord;
import com.platform.entity.TblClientSignRecord;
import com.platform.service.TblClientPropertyRecordService;
import com.platform.service.TblClientSignRecordService;
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
@RequestMapping("/clientPropertyRecord")
public class ClientPropertyRecordController {
    @Autowired
    private TblClientPropertyRecordService tblClientPropertyRecordService;

    /**
     * 客户管理列表
     *
     * @param params 请求参数
     * @return R
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据查询列表数据
        PageUtilsPlus pageUtil = tblClientPropertyRecordService.queryPage(params);
        R page = R.ok().put("page", pageUtil);
        return page;
    }

    /**
     * 保存客户签单记录
     */
    @SysLog("保存客户资产记录")
    @RequestMapping("/save")
    public R save(@RequestBody TblClientPropertyRecord tblClientPropertyRecord) {
        ValidatorUtils.validateEntity(tblClientPropertyRecord);
        SysUserEntity user = UserUtil.getCurUser();
        tblClientPropertyRecord.setClientManagerId(user.getUserId());
        tblClientPropertyRecord.setCreateTime(new Date());
        tblClientPropertyRecord.setClientManagerName(user.getRealName());
        tblClientPropertyRecordService.saveClientPropertyRecord(tblClientPropertyRecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TblClientPropertyRecord tblClientPropertyRecord) {
        ValidatorUtils.validateEntity(tblClientPropertyRecord);
        tblClientPropertyRecordService.updateClientPropertyRecord(tblClientPropertyRecord);
        return R.ok();
    }
    /**
     * 获取客户资产信息
     *
     * @return
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") long id) {
        TblClientPropertyRecord tblClientPropertyRecord = tblClientPropertyRecordService.queryObject(id);
        return R.ok().put("tblClientPropertyRecord", tblClientPropertyRecord);
    }
}
