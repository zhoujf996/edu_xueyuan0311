package cn.eduxueyuan.eduservice.controller;


import cn.eduxueyuan.common.R;
import cn.eduxueyuan.eduservice.entity.EduChapter;
import cn.eduxueyuan.eduservice.entity.dto.ChapterDto;
import cn.eduxueyuan.eduservice.service.EduChapterService;
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
 * @since 2019-08-03
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //查询课程里面的所有章节和小节数据，使用dto进行封装
    @GetMapping("getAllChapterVideoByCourse/{courseId}")
    public R getAllChapterVideoByCourse(@PathVariable String courseId) {
        List<ChapterDto> list = eduChapterService.getAllChapterVideo(courseId);
        return R.ok().data("items",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //根据id查询章节
    @GetMapping("getChapterId/{chapterId}")
    public R getChapterId(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter",eduChapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除章节
    @DeleteMapping("deleteChapter/{id}")
    public R deleteChapter(@PathVariable String id) {
        eduChapterService.removeChapterId(id);
        return R.ok();
    }

}

