package com.example.consumer.controller;

import com.example.consumer.iface.IUserRemote;
import com.example.core.entity.RespBean;
import com.example.core.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-1
 * @version 1.0
 */
@RestController
public class OpenUserController {

    @Autowired
    private IUserRemote iUserRemote;


    @GetMapping("/open/userinfo")
    public RespBean<UserInfo> info(String name){
        return iUserRemote.getUserInfo(name);
    }

}
