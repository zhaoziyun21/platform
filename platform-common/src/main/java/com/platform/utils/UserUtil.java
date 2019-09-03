package com.platform.utils;

import com.platform.entity.SysUserEntity;
import org.apache.shiro.subject.Subject;

/**
 * Created by zhaoziyun on 2019/7/13.
 */
public class UserUtil {

    public static SysUserEntity getCurUser(){
        Subject subject = ShiroUtils.getSubject();
        return (SysUserEntity)subject.getSession().getAttribute("curUser");

    }
}
