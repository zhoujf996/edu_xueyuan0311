package cn.eduxueyuan.edusta.controller;


import cn.eduxueyuan.common.R;
import cn.eduxueyuan.edusta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/edusta/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    //返回进行统计的数据，两个数组
    @GetMapping("getCountData/{begin}/{end}/{type}")
    public R getDataCount(@PathVariable String begin,
                          @PathVariable String end,
                          @PathVariable String type) {
        //返回数据包含两部分，使用map进行封装，返回
        Map<String,Object> map = staService.getCountData(begin,end,type);
        return R.ok().data(map);
    }

    //获取某一天注册人数，把注册人数添加统计分析表
    @PostMapping("createStaData/{day}")
    public R createData(@PathVariable String day) {
        //获取统计数据，添加统计分析表里面
        staService.getDataAdd(day);
        return R.ok();
    }
}

