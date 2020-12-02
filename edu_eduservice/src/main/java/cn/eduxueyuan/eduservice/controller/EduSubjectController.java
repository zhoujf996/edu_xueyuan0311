package cn.eduxueyuan.eduservice.controller;


import cn.eduxueyuan.common.R;
import cn.eduxueyuan.eduservice.entity.EduSubject;
import cn.eduxueyuan.eduservice.entity.dto.OneSubjectDto;
import cn.eduxueyuan.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-31
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加一级分类
    @PostMapping("addOneLevel")
    public R addOneLevel(@RequestBody EduSubject eduSubject) {
        boolean flag = eduSubjectService.addLevelOne(eduSubject);
        return R.ok();
    }

    //添加二级分类
    @PostMapping("addTwoLevel")
    public R addTwoLevel(@RequestBody EduSubject eduSubject) {
        boolean flag = eduSubjectService.addLevelTwo(eduSubject);
        return R.ok();
    }

    //根据分类id删除分类
    @DeleteMapping("{id}")
    public R deleteSubjectId(@PathVariable String id) {
        boolean flag = eduSubjectService.removeSubjectId(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //返回显示的json数据
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubjectDto> list = eduSubjectService.getSubjectAll();
        return R.ok().data("items",list);
    }

    //导入课程分类
    @PostMapping("import")
    public R importSubjectData(MultipartFile file) {
        //获取上传文件
        List<String> msg = eduSubjectService.importData(file);
        //判断如果返回msg没有数据
        if(msg.size()>0) {//有提示错误数据
            return R.error().message("部分数据导入成功").data("msg",msg);
        } else {
            return R.ok();
        }

    }
}

