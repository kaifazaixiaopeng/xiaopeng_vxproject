package com.xiaopeng.vx;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName: XiaopengServer
 * @Author: Bugpeng
 * @Since: 2023/3/30
 * @Remark:
 */
@SpringBootApplication
@Slf4j
@EnableAsync
@Configuration
@MapperScan(basePackages = {"com.xiaopeng.vx.mapper", "com.xiaopeng.vx.module.*.mapper.**"})
public class XiaopengServer {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(XiaopengServer.class, args);
        SpringApplication springApplication = new SpringApplication(XiaopengServer.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        Environment env = run.getEnvironment();
        log.info("\n=======================================================\n" +
                        "Local:  http://localhost:{}\n" +
                        "External: http://{}:{}\n" +
                        "Doc:    http://{}:{}{}/doc.html\n" +
                        "---小鹏的服务启动成功---\n" +
                        "========================================================",
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"));
    }
}
