package com.xiaopeng.vx.module.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

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
public class AccToken {
    /**
     * 获取AccessToken
     *
     * @param appId 填写公众号的appid
     * @param appSecret 填写公众号的开发者密码(AppSecret)
     * @return
     */
    public String getAccessTokenMethod(String appId, String appSecret) {
        try {
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
                //todo token 入库，进方法先查验数据库token有没有超过两个小时
            }
            return null;
        } catch (Exception e) {
            log.error("获取accessToken异常" + e.getMessage());
            return null;
        }
    }

}
