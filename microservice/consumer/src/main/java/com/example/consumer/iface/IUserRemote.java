package com.example.consumer.iface;

import com.example.core.entity.RespBean;
import com.example.core.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-1
 * @version 1.0
 */
@FeignClient(name="demo-provider")
public interface IUserRemote {


    @PostMapping("/user/v1/info")
    RespBean<UserInfo> getUserInfo(String name);

}
