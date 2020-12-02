package cn.eduxueyuan.eduservice.controller.front;

import cn.eduxueyuan.common.R;
import cn.eduxueyuan.eduservice.entity.EduCourse;
import cn.eduxueyuan.eduservice.entity.EduTeacher;
import cn.eduxueyuan.eduservice.entity.dto.ChapterDto;
import cn.eduxueyuan.eduservice.entity.query.CourseInfo;
import cn.eduxueyuan.eduservice.service.EduChapterService;
import cn.eduxueyuan.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/frontCourse")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    //根据课程id查询课程详情信息
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id) {
        //1 根据课程id查询大纲（章节和小节）
        List<ChapterDto> allChapterVideo = chapterService.getAllChapterVideo(id);

        //2 编写sql语句根据课程id查询课程信息
        CourseInfo courseInfo = courseService.getCourseBaseInfo(id);

        return R.ok().data("allChapterVideo",allChapterVideo).data("courseInfo",courseInfo);
    }

    //分页查询课程列表功能
    @GetMapping("getFrontCoursePage/{page}/{limit}")
    public R getFrontCoursePage(@PathVariable long page,
                                @PathVariable long limit) {
        // 创建page对象
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        //前端实现分页，需要分页所有的数据都返回
        //把分页数据都封装到map集合中，返回map集合
        Map<String,Object> map = courseService.getTeacherFrontPage(pageCourse);
        return R.ok().data(map);
    }
}
