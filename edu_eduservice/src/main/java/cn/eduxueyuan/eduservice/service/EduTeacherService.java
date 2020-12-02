package cn.eduxueyuan.eduservice.service;

import cn.eduxueyuan.eduservice.entity.EduTeacher;
import cn.eduxueyuan.eduservice.entity.query.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-26
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //实现条件查询带分页
    void getPageTeacher(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery);

    //前台讲师分页查询功能
    Map<String, Object> getTeacherFrontPage(Page<EduTeacher> pageTeacher);
}
