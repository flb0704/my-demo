package com.example.provider.controller;

import com.example.core.entity.RespBean;
import com.example.core.entity.UserInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-1
 * @version 1.0
 */
@RestController
public class UserController {


    @PostMapping("/user/v1/info")
    public RespBean<UserInfo> getUserInfo(String name){
        UserInfo info = new UserInfo();
        info.setName(name);
        info.setAddress("xxxxxx");
        return RespBean.ok(info);
    }

}
