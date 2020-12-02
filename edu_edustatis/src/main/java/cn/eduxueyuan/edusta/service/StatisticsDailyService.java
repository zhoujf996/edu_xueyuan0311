package cn.eduxueyuan.edusta.service;

import cn.eduxueyuan.edusta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-28
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //获取某一天注册人数，把注册人数添加统计分析表
    void getDataAdd(String day);

    //返回进行统计的数据，两个数组
    Map<String, Object> getCountData(String begin, String end, String type);
}
