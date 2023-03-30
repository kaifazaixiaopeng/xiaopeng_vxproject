package com.xiaopeng.vx.module.controller;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.xiaopeng.vx.module.dto.InMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @ClassName: TestController
 * @Author: Bugpeng
 * @Since: 2023/3/30
 * @Remark:
 */
@RequestMapping("/test")
@RestController
@Slf4j
public class TestController {
    @PostMapping("/helloPeng")
    public String test1(){
        return "helloPeng";
    }
    // 微信页面填写的token，必须保密
    private static final String TOKEN = "143148@qq";

    @GetMapping("/validate")
    public String validate(String signature,String timestamp,String nonce,String echostr){
        // 1. 将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = {timestamp, nonce, TOKEN};
        log.info("收到的TOKEN=====>{}"+TOKEN);
        Arrays.sort(arr);
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder sb = new StringBuilder();
        for (String temp : arr) {
            sb.append(temp);
        }
        // 这里利用了hutool的加密工具类
        String sha1 = SecureUtil.sha1(sb.toString());
        // 3. 加密后的字符串与signature对比，如果相同则该请求来源于微信，原样返回echostr
        if (sha1.equals(signature)){
            return echostr;
        }
        // 接入失败
        return null;
    }

    @PostMapping("/testXml")
    public String testXml(@RequestBody InMessage message){
        return JSONObject.toJSONString(message);
    }
}
