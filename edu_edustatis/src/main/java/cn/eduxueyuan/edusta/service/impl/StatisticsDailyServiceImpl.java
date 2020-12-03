package cn.eduxueyuan.edusta.service.impl;

import cn.eduxueyuan.common.R;
import cn.eduxueyuan.edusta.client.UcenterClient;
import cn.eduxueyuan.edusta.entity.StatisticsDaily;
import cn.eduxueyuan.edusta.mapper.StatisticsDailyMapper;
import cn.eduxueyuan.edusta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-08-28
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //获取某一天注册人数，把注册人数添加统计分析表
    //调用ucenter模块的方法获取某天统计数据
    @Override
    public void getDataAdd(String day) {
        R result = ucenterClient.countRegisterNum(day);
        //获取返回R对象里面数据
//        Map<String, Object> map = result.getData();
//        map.get(registerNum);

        Integer registerNum = (Integer)result.getData().get("registerNum");
        System.out.println("*******"+registerNum);

        //删除表里面相同日期数据，再进行添加
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        //把统计数据添加表
        StatisticsDaily daily = new StatisticsDaily();
        daily.setDateCalculated(day);//统计日期
        daily.setRegisterNum(registerNum); //注册人数

        daily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        daily.setCourseNum(RandomUtils.nextInt(100, 200));
        daily.setLoginNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(daily);
    }
    
    

    //返回进行统计的数据，两个数组
    @Override
    public Map<String, Object> getCountData(String begin, String end, String type) {
        //1 根据时间范围进行数据查询
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        //指定查询的字段
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> statisticsList = baseMapper.selectList(wrapper);

        //把数据构建成想要的结构，最终变成两个json数组形式
        //创建两个list集合
        //日期集合
        List<String> calculatedList = new ArrayList<>();
        //数据集合
        List<Integer> dataList = new ArrayList<>();

        //向两个list集合中封装数据
        //遍历查询集合
        for (int i = 0; i < statisticsList.size(); i++) {
            //集合每个对象
            StatisticsDaily sta = statisticsList.get(i);
            //封装日期集合数据
            String dateCalculated = sta.getDateCalculated();
            calculatedList.add(dateCalculated);

            //封装数据部分
            //判断获取哪个统计因子
            switch (type) {
                case "register_num":
                    Integer registerNum = sta.getRegisterNum();
                    dataList.add(registerNum);
                    break;
                case "login_num":
                    Integer loginNum = sta.getLoginNum();
                    dataList.add(loginNum);
                    break;
                case "video_view_num":
                    Integer videoViewNum = sta.getVideoViewNum();
                    dataList.add(videoViewNum);
                    break;
                case "course_num":
                    Integer courseNum = sta.getCourseNum();
                    dataList.add(courseNum);
                    break;
                default:
                    break;
            }
        }

        //创建map集合，把封装之后两个list集合放到map集合，返回
        Map<String,Object> map = new HashMap<>();
        map.put("calculatedList",calculatedList);
        map.put("dataList",dataList);

        return map;
    }
}
