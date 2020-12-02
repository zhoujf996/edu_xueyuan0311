package cn.eduxueyuan.eduservice.controller;


import cn.eduxueyuan.common.R;
import cn.eduxueyuan.eduservice.entity.EduCourse;
import cn.eduxueyuan.eduservice.entity.query.CourseInfoForm;
import cn.eduxueyuan.eduservice.entity.query.PublishCourseInfo;
import cn.eduxueyuan.eduservice.service.EduCourseService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-08-02
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //根据课程id删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourseId(courseId);
        return R.ok();
    }

    //TODO 完善条件查询带分页
    @GetMapping("getAllCourse")
    public R getAllCourse() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("items",list);
    }

    //课程最终发布
    @PutMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        //课程状态
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
    //做课程确认页面，根据课程id查询课程信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        PublishCourseInfo courseInfoPublish = courseService.getCourseInfoPublish(id);
        return R.ok().data("courseInfoPublish",courseInfoPublish);
    }
    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        courseService.updateCourse(courseInfoForm);
        return R.ok();
    }
    //根据课程id查询课程信息
    @GetMapping("getCourseInfo/{id}")
    public R getInfoCourse(@PathVariable String id) {
        CourseInfoForm courseInfoForm = courseService.getCourseInfoId(id);
        return R.ok().data("courseInfo",courseInfoForm);
    }

    //添加课程信息功能
    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfoForm courseInfoForm) {
        //返回课程id
        String courseId = courseService.saveCourse(courseInfoForm);
        return R.ok().data("courseId",courseId);
    }
}

