package cn.eduxueyuan.eduservice.client;

import cn.eduxueyuan.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("edu-eduvod")
@Component
public interface VodClient {

    //细节：  如果使用服务调用过程，@PathVariable注解里面必须添加参数
    @DeleteMapping("/eduvod/vid/deleteVideoAli/{videoId}")
    public R deleteVideoAli(@PathVariable("videoId") String videoId);

    //删除多个视频的方法
    @DeleteMapping("/eduvod/vid/removeMoreVideo")
    public R removeMoreVideo(@RequestParam("videoIdList") List videoIdList) ;
}
