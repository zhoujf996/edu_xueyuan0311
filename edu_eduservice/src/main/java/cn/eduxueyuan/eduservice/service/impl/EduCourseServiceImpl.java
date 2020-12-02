package cn.eduxueyuan.eduservice.service.impl;

import cn.eduxueyuan.eduservice.entity.EduCourse;
import cn.eduxueyuan.eduservice.entity.EduCourseDescription;
import cn.eduxueyuan.eduservice.entity.EduTeacher;
import cn.eduxueyuan.eduservice.entity.query.CourseInfo;
import cn.eduxueyuan.eduservice.entity.query.CourseInfoForm;
import cn.eduxueyuan.eduservice.entity.query.PublishCourseInfo;
import cn.eduxueyuan.eduservice.handler.EduException;
import cn.eduxueyuan.eduservice.mapper.EduCourseMapper;
import cn.eduxueyuan.eduservice.service.EduChapterService;
import cn.eduxueyuan.eduservice.service.EduCourseDescriptionService;
import cn.eduxueyuan.eduservice.service.EduCourseService;
import cn.eduxueyuan.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-08-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //注入小节service
    @Autowired
    private EduVideoService eduVideoService;

    //注入章节service
    @Autowired
    private EduChapterService eduChapterService;

    //注入描述service
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //添加课程信息功能
    @Override
    public String saveCourse(CourseInfoForm courseInfoForm) {
        //1 把课程基本信息添加课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        //如果课程添加失败，不需要添加描述
        if(insert <= 0) { //添加课程失败
            throw new EduException(20001,"添加课程失败");
        }

        String coursedId = eduCourse.getId();

        //2 把课程描述添加描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        String description = courseInfoForm.getDescription();
        if(!StringUtils.isEmpty(description)) {
            //因为课程和描述是一对一关系，id相同
            //添加之后的课程id设置到描述对象里面
            courseDescription.setId(coursedId);
            //设置到描述对象里面
            courseDescription.setDescription(description);
            courseDescriptionService.save(courseDescription);
        }

        return coursedId;
    }

    //根据课程id查询课程信息
    @Override
    public CourseInfoForm getCourseInfoId(String id) {
        //1 根据id查询课程表
        EduCourse eduCourse = baseMapper.selectById(id);
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        //2 根据id查询描述表
        EduCourseDescription eduCourseDescription = courseDescriptionService.getById(id);
        String description = eduCourseDescription.getDescription();
        if(!StringUtils.isEmpty(description)) {
            courseInfoForm.setDescription(description);
        }
        return courseInfoForm;
    }

    //修改课程信息
    @Override
    public void updateCourse(CourseInfoForm courseInfoForm) {
             //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if(result <= 0) {
            throw  new EduException(20001,"修改失败");
        }

        //修改课程描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(courseInfoForm.getId()); //设置id值
        courseDescriptionService.updateById(courseDescription);
    }

    //根据课程id查询课程信息
    @Override
    public PublishCourseInfo getCourseInfoPublish(String courseId) {
        PublishCourseInfo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    //根据课程id删除课程
    @Override
    public void removeCourseId(String courseId) {
        //根据课程id删除小节
        eduVideoService.deleteVideoByCourseId(courseId);

        //根据课程id删除章节
        eduChapterService.deleteChapterByCourseId(courseId);

        //删除描述
        courseDescriptionService.removeById(courseId);

        //删除课程
        int result = baseMapper.deleteById(courseId);
        if(result<=0) {
            throw new EduException(20001,"删除失败");
        }
    }

    //2 根据讲师id查询讲师所讲的课程
    @Override
    public List<EduCourse> getCourseListByTeacherId(String id) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> eduCourses = baseMapper.selectList(wrapper);
        return eduCourses;
    }

    //分页查询课程列表功能
    @Override
    public Map<String, Object> getTeacherFrontPage(Page<EduCourse> pageCourse) {
        //调用方法实现分页查询，查询之后分页所有数据封装到pageTeacher对象里面
        baseMapper.selectPage(pageCourse,null);
        //把pageTeacher里面分页数据封装到map集合
        List<EduCourse> records = pageCourse.getRecords();//每页数据list集合
        long total = pageCourse.getTotal();//总记录数
        long size = pageCourse.getSize();//每页记录数
        long pages = pageCourse.getPages();//总页数
        long current = pageCourse.getCurrent();//当前页
        boolean hasPrevious = pageCourse.hasPrevious();//是否有上一页
        boolean hasNext = pageCourse.hasNext();//是否有下一页

        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //2 编写sql语句根据课程id查询课程信息
    @Override
    public CourseInfo getCourseBaseInfo(String id) {
        return baseMapper.getCourseBaseInfo(id);
    }

}
