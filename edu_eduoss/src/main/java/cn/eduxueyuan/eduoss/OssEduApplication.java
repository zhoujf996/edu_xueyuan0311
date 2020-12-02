package cn.eduxueyuan.eduoss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class OssEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssEduApplication.class, args);
    }
}
