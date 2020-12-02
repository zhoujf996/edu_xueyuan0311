package cn.eduxueyuan.eduservice.service.impl;

import cn.eduxueyuan.eduservice.entity.EduChapter;
import cn.eduxueyuan.eduservice.entity.EduVideo;
import cn.eduxueyuan.eduservice.entity.dto.ChapterDto;
import cn.eduxueyuan.eduservice.entity.dto.VideoDto;
import cn.eduxueyuan.eduservice.handler.EduException;
import cn.eduxueyuan.eduservice.mapper.EduChapterMapper;
import cn.eduxueyuan.eduservice.service.EduChapterService;
import cn.eduxueyuan.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-08-03
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    //注入小节service
    @Autowired
    private EduVideoService eduVideoService;

    //查询课程里面的所有章节和小节数据，使用dto进行封装
    @Override
    public List<ChapterDto> getAllChapterVideo(String courseId) {
        //1 查询课程里面所有章节
        //根据课程id查询章节数据
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapper);

        //2 查询课程里面所有的小节
        //根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //定义集合，用于存储最终封装的数据
        List<ChapterDto> finalList = new ArrayList<>();

        //封装章节
        //把课程里面所有的章节集合遍历
        for (int i = 0; i < eduChapterList.size(); i++) {
            //得到每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //把eduChapter对象转换成功chapterDto对象
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(eduChapter,chapterDto);
            //chapterDto放到list集合中
            finalList.add(chapterDto);

            //创建集合，用户存储小节数据
            List<VideoDto> videoList = new ArrayList<>();

            //遍历课程里面所有的小节
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断小节里面chapterid  和 章节里面id是否一样
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //把eduVideo对象转换videoDto对象
                    VideoDto videoDto = new VideoDto();
                    BeanUtils.copyProperties(eduVideo,videoDto);
                    //放到list集合
                    videoList.add(videoDto);
                }
            }
            //把封装好的小节放到章节里面
            chapterDto.setChildren(videoList);
        }
        return finalList;
    }

    //删除章节
    @Override
    public void removeChapterId(String id) {
        //查询章节里面是否有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        int count = eduVideoService.count(wrapper);
        if(count > 0) {//章节里面有小节
            throw new EduException(20001,"不能删除");
        } else {
            //删除章节
            baseMapper.deleteById(id);
        }
    }

    //根据课程id删除章节
    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
