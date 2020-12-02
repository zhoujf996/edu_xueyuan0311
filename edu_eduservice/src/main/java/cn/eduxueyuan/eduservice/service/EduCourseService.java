package cn.eduxueyuan.eduservice.service;

import cn.eduxueyuan.eduservice.entity.EduCourse;
import cn.eduxueyuan.eduservice.entity.EduTeacher;
import cn.eduxueyuan.eduservice.entity.query.CourseInfo;
import cn.eduxueyuan.eduservice.entity.query.CourseInfoForm;
import cn.eduxueyuan.eduservice.entity.query.PublishCourseInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-02
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程信息功能
    String saveCourse(CourseInfoForm courseInfoForm);

    //根据课程id查询课程信息
    CourseInfoForm getCourseInfoId(String id);

    //修改课程信息
    void updateCourse(CourseInfoForm courseInfoForm);

    //根据课程id查询课程信息
    public PublishCourseInfo getCourseInfoPublish(String courseId);

    //根据课程id删除课程
    void removeCourseId(String courseId);

    //2 根据讲师id查询讲师所讲的课程
    List<EduCourse> getCourseListByTeacherId(String id);

    //前台分页查询课程列表功能
    Map<String, Object> getTeacherFrontPage(Page<EduCourse> pageCourse);

    //2 编写sql语句根据课程id查询课程信息
    CourseInfo getCourseBaseInfo(String id);
}
