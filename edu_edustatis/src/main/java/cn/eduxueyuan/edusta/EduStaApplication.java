package cn.eduxueyuan.edusta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients //服务调用
public class EduStaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduStaApplication.class, args);
    }
}
