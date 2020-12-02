package cn.eduxueyuan.eduservice.service;

import cn.eduxueyuan.eduservice.entity.EduChapter;
import cn.eduxueyuan.eduservice.entity.dto.ChapterDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-03
 */
public interface EduChapterService extends IService<EduChapter> {

    //查询课程里面的所有章节和小节数据，使用dto进行封装
    List<ChapterDto> getAllChapterVideo(String courseId);

    //删除章节
    void removeChapterId(String id);

    //根据课程id删除章节
    void deleteChapterByCourseId(String courseId);
}
