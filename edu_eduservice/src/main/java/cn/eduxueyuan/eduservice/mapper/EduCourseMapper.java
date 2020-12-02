package cn.eduxueyuan.eduservice.mapper;

import cn.eduxueyuan.eduservice.entity.EduCourse;
import cn.eduxueyuan.eduservice.entity.query.CourseInfo;
import cn.eduxueyuan.eduservice.entity.query.PublishCourseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-08-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程信息
    public PublishCourseInfo getPublishCourseInfo(String courseId);

    //2 编写sql语句根据课程id查询课程信息
    CourseInfo getCourseBaseInfo(String id);
}
