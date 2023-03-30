package com.xiaopeng.vx.module.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Author: Bugpeng
 * @Since: 2023/3/30
 * @Remark:
 */
@RequestMapping("/test")
@RestController
public class TestController {
    @PostMapping("/helloPeng")
    public String test1(){
        return "helloPeng";
    }

}
