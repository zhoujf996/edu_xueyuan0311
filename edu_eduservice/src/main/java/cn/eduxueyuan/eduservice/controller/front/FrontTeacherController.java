package cn.eduxueyuan.eduservice.controller.front;

import cn.eduxueyuan.common.R;
import cn.eduxueyuan.eduservice.entity.EduCourse;
import cn.eduxueyuan.eduservice.entity.EduTeacher;
import cn.eduxueyuan.eduservice.service.EduCourseService;
import cn.eduxueyuan.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/frontTeacher")
@CrossOrigin
public class FrontTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //根据讲师id查询讲师详情信息
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        //1 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(id);

        //2 根据讲师id查询讲师所讲的课程
        List<EduCourse> list = courseService.getCourseListByTeacherId(id);

        return R.ok().data("eduTeacher",eduTeacher).data("courseList",list);
    }
    //讲师分页查询功能
    @GetMapping("getFrontTeacherPage/{page}/{limit}")
    public R getFrontTeacherPage(@PathVariable long page,
                                 @PathVariable long limit) {
        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        //前端实现分页，需要分页所有的数据都返回
        //把分页数据都封装到map集合中，返回map集合
        Map<String,Object> map = teacherService.getTeacherFrontPage(pageTeacher);
        return R.ok().data(map);
    }
}
