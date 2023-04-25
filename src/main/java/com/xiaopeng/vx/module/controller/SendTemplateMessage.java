package com.xiaopeng.vx.module.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SendTemplateMessage
 * @Author: Bugpeng
 * @Since: 2023/4/24
 * @Remark:
 */
@RequestMapping("/msg")
@RestController
public class SendTemplateMessage {

    private static final String ACCESS_TOKEN = "your_access_token";
    private static final String OPENID = "your_openid";
    @PostMapping("/sendTemplate")
    public void sendTemplate(){
        String templateId = "your_template_id";
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + ACCESS_TOKEN;
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> first = new HashMap<>();
        first.put("value", "您好，感谢关注我们的公众号！");
        first.put("color", "#173177");
        data.put("first", first);
        Map<String, String> keyword1 = new HashMap<>();
        keyword1.put("value", "张三");
        keyword1.put("color", "#173177");
        data.put("keyword1", keyword1);
        Map<String, String> keyword2 = new HashMap<>();
        keyword2.put("value", "2023年4月25日");
        keyword2.put("color", "#173177");
        data.put("keyword2", keyword2);
        Map<String, String> remark = new HashMap<>();
        remark.put("value", "感谢您的关注！");
        remark.put("color", "#173177");
        data.put("remark", remark);
        WechatTemplateMessage message = new WechatTemplateMessage(OPENID, templateId, data);
        try {
            sendTemplateMessage(url, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class WechatTemplateMessage {
        private String touser;
        private String template_id;
        private Map<String, Map<String, String>> data;

        public WechatTemplateMessage(String touser, String template_id, Map<String, Map<String, String>> data) {
            this.touser = touser;
            this.template_id = template_id;
            this.data = data;
        }

        public String getTouser() {
            return touser;
        }

        public void setTouser(String touser) {
            this.touser = touser;
        }

        public String getTemplate_id() {
            return template_id;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public Map<String, Map<String, String>> getData() {
            return data;
        }

        public void setData(Map<String, Map<String, String>> data) {
            this.data = data;
        }
    }

    private static void sendTemplateMessage(String url, WechatTemplateMessage message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(message);
        URL urlObject = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) urlObject.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.connect();
        connection.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = connection.getInputStream();
        JsonNode responseNode = mapper.readTree(inputStream);
        inputStream.close();
        connection.disconnect();
        int errorCode = responseNode.get("errcode").asInt();
        if (errorCode != 0) {
            String errorMsg = responseNode.get("errmsg").asText();
            throw new IOException("发送模板消息失败: " + errorCode + ", " + errorMsg);
        }
    }
}
