package com.platform.bean;

import lombok.Data;

import java.util.List;

@Data
public class UserClientBean {
    public String userRealName;
    public List<Long> clientIds;
}
