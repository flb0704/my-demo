package com.example.consumer.controller;

import com.example.consumer.iface.IUserRemote;
import com.example.core.entity.RespBean;
import com.example.core.entity.UserInfo;
import com.example.core.thread.TraceThread;
import com.example.core.utils.LogUtil;
import com.example.core.utils.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/open/traceId")
    public void traceId(){
        LogUtil.info("enter method traceId");
        for (int i = 0; i < 2; i++) {
            ThreadPoolUtil.EXECUTOR_SERVICE.submit(new TraceThread());
        }
    }


    @GetMapping("/open/userinfo")
    public RespBean<UserInfo> info(String name) {
        return iUserRemote.getUserInfo(name);
    }

    @PostMapping(value = "/open/check", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RespBean<UserInfo> check(@RequestBody UserInfo info) {
        return iUserRemote.check(info);
    }


}
