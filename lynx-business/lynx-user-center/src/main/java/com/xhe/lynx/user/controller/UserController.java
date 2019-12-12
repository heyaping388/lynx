package com.xhe.lynx.user.controller;

import com.xhe.lynx.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: xhe
 * @Date: 2019/12/11 17:34
 * @Description:
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    @GetMapping("/list")
    public R list(){
       log.info("查询用户列表");
       return R.ok("查询用户列表成功");
    }
}
