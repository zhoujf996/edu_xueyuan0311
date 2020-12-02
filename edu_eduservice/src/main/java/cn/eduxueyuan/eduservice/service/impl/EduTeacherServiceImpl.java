package cn.eduxueyuan.eduservice.service.impl;

import cn.eduxueyuan.eduservice.entity.EduTeacher;
import cn.eduxueyuan.eduservice.entity.query.TeacherQuery;
import cn.eduxueyuan.eduservice.handler.EduException;
import cn.eduxueyuan.eduservice.mapper.EduTeacherMapper;
import cn.eduxueyuan.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-26
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //实现条件查询带分页
    @Override
    public void getPageTeacher(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery) {
//        try {
//            int i = 10/0;
//        }catch(Exception e) {
//            //抛出自定义异常
//            throw new EduException(20001,"自定义异常执行了....");
//        }

        //判断条件值是否为空，进行条件拼接
        //从teacherQuery对象里面获取条件值
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

//        SELECT *
//                FROM edu_teacher et
//        WHERE et.name? AND et.level=? AND et.gmt_create>? AND et.gmt_create<?
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //拼接条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            //大于开始时间
            wrapper.gt("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            //小于结束时间
            wrapper.lt("gmt_create",end);
        }

        //调用mapper里面的方法实现条件查询带分页
        //做一件事情：把分页之后的数据封装到pageTeacher对象里面
        baseMapper.selectPage(pageTeacher,wrapper);
    }

    //前台讲师分页查询功能
    @Override
    public Map<String, Object> getTeacherFrontPage(Page<EduTeacher> pageTeacher) {
        //调用方法实现分页查询，查询之后分页所有数据封装到pageTeacher对象里面
        baseMapper.selectPage(pageTeacher,null);
        //把pageTeacher里面分页数据封装到map集合
        List<EduTeacher> records = pageTeacher.getRecords();//每页数据list集合
        long total = pageTeacher.getTotal();//总记录数
        long size = pageTeacher.getSize();//每页记录数
        long pages = pageTeacher.getPages();//总页数
        long current = pageTeacher.getCurrent();//当前页
        boolean hasPrevious = pageTeacher.hasPrevious();//是否有上一页
        boolean hasNext = pageTeacher.hasNext();//是否有下一页

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
}
