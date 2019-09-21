package com.platform.controller;

import com.platform.annotation.SysLog;
import com.platform.entity.SysUserEntity;
import com.platform.entity.TblClient;
import com.platform.service.TblClientService;
import com.platform.util.UserUtil;
import com.platform.utils.*;
import com.platform.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


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
    private Logger logger = LoggerFactory.getLogger(getClass());
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
    public R list(@RequestParam Map<String, Object> params) {

        SysUserEntity curUser = UserUtil.getCurUser();
        params.put("clientManagerId",curUser.getUserId());
        //查询列表数据查询列表数据
        PageUtilsPlus pageUtil = tblClientService.queryPage(params);
        R page = R.ok().put("page", pageUtil);
        return page;
    }
    @ResponseBody
    @RequestMapping("/ownerlist")
    public R ownerlist(@RequestParam Map<String, Object> params) {

        SysUserEntity curUser = UserUtil.getCurUser();
        params.put("clientManagerId",curUser.getUserId());
        //查询列表数据查询列表数据
        PageUtilsPlus pageUtil = tblClientService.queryOwnerPage(params);
        R page = R.ok().put("page", pageUtil);
        return page;
    }

    @ResponseBody
    @RequestMapping("/getCurUser")
    public R getCurUser(@RequestParam Map<String, Object> params) {
        SysUserEntity curUser = UserUtil.getCurUser();
        R page = R.ok().put("user", curUser);
        return page;
    }
    @ResponseBody
    @RequestMapping("/publishClientList")
    public R publishClientList(@RequestParam Map<String, Object> params) {

        SysUserEntity curUser = UserUtil.getCurUser();
        params.put("clientManagerId",curUser.getUserId());
        //查询列表数据查询列表数据
        PageUtilsPlus pageUtil = tblClientService.publishClientPage(params);
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
        TblClient tblClient = tblClientService.queryObject(id);
        SysUserEntity user = UserUtil.getCurUser();
        return R.ok().put("client", tblClient).put("user",user);
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
        client.setClientManagerName(user.getRealName());
        client.setCreateTime(new Date());
        client.setStatus("1");
        client.setFollowTime(new Date());
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
        SysUserEntity user = UserUtil.getCurUser();
        client.setClientManagerId(user.getUserId());
        client.setClientManagerName(user.getRealName());
        client.setUpdateUser(user.getUsername());
        client.setFollowTime(new Date());
        tblClientService.update(client);
        return R.ok();
    }
    /**
     * 放弃客户
     */
    @RequestMapping("/giveUpClient")
    public R giveUpClient(@RequestParam long clientId) {
        SysUserEntity user = UserUtil.getCurUser();
        TblClient client = tblClientService.queryObject(clientId);
        client.setClientManagerId(user.getUserId());
        client.setClientManagerName(user.getRealName());
        client.setUpdateUser(user.getUsername());
        client.setFollowTime(null);
        client.setClientType("2");
        tblClientService.update(client);
        return R.ok();
    }
    /**
     * 重点客户/取消
     */
    @RequestMapping("/majorClient")
    public R majorClient(@RequestParam long clientId,String clientType) {
        SysUserEntity user = UserUtil.getCurUser();
        TblClient client = tblClientService.queryObject(clientId);
        client.setId(clientId);
        client.setClientManagerId(user.getUserId());
        client.setClientManagerName(user.getRealName());
        client.setUpdateUser(user.getUsername());
        client.setFollowTime(new Date());
        client.setClientType(clientType);
        tblClientService.update(client);
        return R.ok();
    }


    /**
     * 抢夺原始客户
     */
    @SysLog("抢夺客户记录")
    @RequestMapping("/secondKill")
    public R secondKill(@RequestBody TblClient tblClient) {
        ValidatorUtils.validateEntity(tblClient);
        SysUserEntity user = UserUtil.getCurUser();
        TblClient client = tblClientService.queryObject(tblClient.getId());
        ReentrantLock lock = new ReentrantLock();
        try {
            if(lock.tryLock(2, TimeUnit.SECONDS)){
                try {
                    if(!"2".equals(client.getClientType())){
                        return R.error();
                    }
                    client.setUpdateUser(user.getUsername());
                    client.setClientManagerId(user.getUserId());
                    client.setClientManagerName(user.getRealName());
                    client.setClientType("0");
                    tblClientService.secondKill(client);
                }catch (Exception e){
                    logger.error(e.getMessage(), e);
                    return R.error();
                }finally {
                    lock.unlock();
                }
            }else{
                return R.error("抢夺客户记录失败，请重试");
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            return R.error("抢夺客户记录失败，请重试");
        }
        return R.ok();
    }

    /**
     * （原始客户）记录分配
     *
     * @return R
     */
    @ResponseBody
    @RequestMapping("/divide")
//    @RequiresPermissions("client:divide")
    public R divide(@RequestBody  Map<String, Object> params) {
        SysUserEntity user = UserUtil.getCurUser();
        params.put("updateUser",user.getUsername());
        params.put("createUser",user.getUsername());
        params.put("status","1");
        params.put("createTime",new Date());
        params.put("followTime",new Date());
        tblClientService.divideTelRecord(params);
        return R.ok();
    }


    /**
     * 批量保存原始客户
     */
    @SysLog("批量保存原始客户记录")
    @RequestMapping(value="/batchSave", method=RequestMethod.POST)
    public R batchSave(String clientTels) {
        if(StringUtils.isNotEmpty(clientTels)){
            SysUserEntity user = UserUtil.getCurUser();
            String[] clientTelArray = clientTels.split(",");
            List<TblClient>  tblClients = new ArrayList<>();
            for (String tblClientTel : clientTelArray){
                TblClient tblClient = new TblClient();
                tblClient.setCreateTime(new Date());
                tblClient.setCreateUser(user.getUsername());
                tblClient.setUpdateUser(user.getUsername());
                tblClient.setStatus("0");
                tblClient.setClientTel(tblClientTel);
                tblClients.add(tblClient);
            }
            tblClientService.batchSave(tblClients);
        }else{
            return R.error("导入手机号不能为空");
        }
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/queryClientByManageID")
    public R queryClientByManageID(@RequestParam Map<String, Object> params) {

        SysUserEntity curUser = UserUtil.getCurUser();
        params.put("clientManagerId",curUser.getUserId());
        //查询列表数据查询列表数据
        PageUtilsPlus pageUtil = tblClientService.queryClientByManageID(params);
        R page = R.ok().put("page", pageUtil);
        return page;
    }

    /**
     * 删除
     */
    @SysLog("删除客户记录")
    @RequestMapping("/del")
    public R delete(@RequestBody long id) {
        tblClientService.deleteClient(id);
        return R.ok();
    }

}
