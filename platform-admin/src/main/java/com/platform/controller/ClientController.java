package com.platform.controller;

import com.platform.annotation.SysLog;
import com.platform.entity.SysConfigEntity;
import com.platform.entity.SysDeptEntity;
import com.platform.entity.SysUserEntity;
import com.platform.entity.TblClient;
import com.platform.service.TblClientService;
import com.platform.util.UserUtil;
import com.platform.utils.*;
import com.platform.validator.ValidatorUtils;
import com.platform.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.platform.utils.ShiroUtils.getUserId;

/**
 * 系统日志Controller
 *
 * @author 赵子云
 * @email 939961241@qq.com
 * @date 2017-03-08 10:40:56
 */
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private TblClientService tblClientService;

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

        SysUserEntity curUser = UserUtil.getCurUser();
        params.put("clientManagerId",curUser.getUserId());
        //查询列表数据查询列表数据
        PageUtilsPlus pageUtil = tblClientService.queryPage(params);
        R page = R.ok().put("page", pageUtil);
        return page;
    }
    /**
     * 获取客户信息
     *
     * @return
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("client:update")
    public R info(@PathVariable("id") int id) {
        TblClient tblClient = tblClientService.queryObject(id);

        return R.ok().put("client", tblClient);
    }


    /**
     * 保存客户
     */
    @SysLog("保存客户")
    @RequestMapping("/save")
    @RequiresPermissions("client:save")
    public R save(@RequestBody TblClient client) {
        ValidatorUtils.validateEntity(client);
        SysUserEntity user = UserUtil.getCurUser();
        client.setClientManagerId(user.getUserId());
        client.setClientManagerName(user.getUsername());
        client.setCreateTime(new Date());
        tblClientService.saveClient(client);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("client:update")
    public R update(@RequestBody TblClient client) {
        ValidatorUtils.validateEntity(client);
        tblClientService.update(client);
        return R.ok();
    }
}
