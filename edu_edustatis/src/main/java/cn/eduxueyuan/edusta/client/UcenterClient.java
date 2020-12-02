package cn.eduxueyuan.edusta.client;

import cn.eduxueyuan.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("edu-ucenter")  //写调用的服务名称
public interface UcenterClient {

    //定义的方法，调用ucenter方法controller的路径
    //统计某一天注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegisterNum(@PathVariable("day") String day);
}
