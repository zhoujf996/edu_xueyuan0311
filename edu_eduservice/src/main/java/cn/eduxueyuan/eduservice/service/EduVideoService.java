package cn.eduxueyuan.eduservice.service;

import cn.eduxueyuan.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-03
 */
public interface EduVideoService extends IService<EduVideo> {

    //删除小节
    void removeVideoId(String id);

    //根据课程id删除小节
    void deleteVideoByCourseId(String courseId);
}
