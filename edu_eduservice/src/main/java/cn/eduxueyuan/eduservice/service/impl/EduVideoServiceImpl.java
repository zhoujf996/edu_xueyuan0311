package cn.eduxueyuan.eduservice.service.impl;

import cn.eduxueyuan.eduservice.client.VodClient;
import cn.eduxueyuan.eduservice.entity.EduVideo;
import cn.eduxueyuan.eduservice.mapper.EduVideoMapper;
import cn.eduxueyuan.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-08-03
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //完善删除小节，同时删除小节里面视频
    @Override
    public void removeVideoId(String id) {
        //id是小节id，根据小节id查询视频id
        EduVideo eduVideo = baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //如果小节里面有视频
        if(!StringUtils.isEmpty(videoSourceId)) {
            //删除视频
            vodClient.deleteVideoAli(videoSourceId);
        }

        //删除小节
        baseMapper.deleteById(id);
    }

    //完善删除小节时候，删除小节里面视频
    @Override
    public void deleteVideoByCourseId(String courseId) {

        //获取课程里面所有的视频id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);

        List<String> list = new ArrayList<>(); //用于存储课程里面所有视频id
        //遍历
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();//每个小节视频id
            if(!StringUtils.isEmpty(videoSourceId)) {
                list.add(videoSourceId);
            }
        }
        //删除小节里面的视频，有多个视频
        vodClient.removeMoreVideo(list);

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        baseMapper.delete(wrapperVideo);
    }
}
