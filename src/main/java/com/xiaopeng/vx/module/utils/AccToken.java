package com.xiaopeng.vx.module.utils;

import com.alibaba.fastjson.JSONObject;
import com.xiaopeng.vx.module.mapper.ModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auto:BUGPeng
 * @Date:2023/3/30 22:00
 * @ClassName:AccToken
 * @Remark:
 */
@Slf4j
@Component
public class AccToken {

    @Autowired
    private ModuleMapper moduleMapper;
    @Value("${wechatConfig.appId}")
    private String appId;
    @Value("${wechatConfig.appId}")
    private String appSecret;
    /**
     * 获取AccessToken
     * @return
     */
    public String getAccessTokenMethod() {
        try {
            //todo token 入库，进方法先查验数据库token有没有超过两个小时
            moduleMapper.getAcctoken();
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
                    appId + "&secret=" + appSecret;
            StringBuilder json = new StringBuilder();
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), StandardCharsets.UTF_8));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
            JSONObject object = (JSONObject) JSONObject.parse(String.valueOf(json));
            log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "获取access_token:{}", object.getString("access_token"));
            if (object.getString("access_token") != null) {
                return object.getString("access_token");
            }
            return null;
        } catch (Exception e) {
            log.error("获取accessToken异常" + e.getMessage());
            return null;
        }
    }

}
