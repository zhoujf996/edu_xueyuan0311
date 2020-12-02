package cn.eduxueyuan.eduservice.controller;


import cn.eduxueyuan.common.R;
import cn.eduxueyuan.eduservice.entity.EduTeacher;
import cn.eduxueyuan.eduservice.entity.query.TeacherQuery;
import cn.eduxueyuan.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-26
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin   //可以被访问到，解决跨域问题
public class EduTeacherController {

    //调用service
    @Autowired
    private EduTeacherService eduTeacherService;

    //login
    //{"code":20000,"data":{"token":"admin"}}
    @GetMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    //1 测试环境 查询所有的讲师功能
    @GetMapping("getAllTeacher")
    public R getAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //2 逻辑删除讲师的方法
    @DeleteMapping("{id}")
    public R removeTeacherId(@PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3 多条件组合查询带分页
    //page代表当前页  limit代表每页显示记录数
    //getTeacherPageCondition/1/10
    @PostMapping("getTeacherPageCondition/{page}/{limit}")
    public R getTeacherPageCondition(@PathVariable long page,
                                     @PathVariable long limit,
                                     @RequestBody(required = false)  TeacherQuery teacherQuery) {
        //1 创建Page对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        //调用service的方法实现条件查询带分页
        //pageTeacher有分页查询之后的所有的数据
        eduTeacherService.getPageTeacher(pageTeacher,teacherQuery);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
//        Map<String,Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);
        return R.ok().data("total",total).data("rows",records);
    }

    //添加讲师的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //查询根据讲师id获取信息
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //修改讲师
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean result = eduTeacherService.updateById(eduTeacher);
        if(result) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

