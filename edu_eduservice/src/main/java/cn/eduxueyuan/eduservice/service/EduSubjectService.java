package cn.eduxueyuan.eduservice.service;

import cn.eduxueyuan.eduservice.entity.EduSubject;
import cn.eduxueyuan.eduservice.entity.dto.OneSubjectDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-31
 */
public interface EduSubjectService extends IService<EduSubject> {

    //导入课程分类
    List importData(MultipartFile file);

    //返回一级和二级分类
    List<OneSubjectDto> getSubjectAll();

    //根据分类id删除分类
    boolean removeSubjectId(String id);

    //添加一级分类
    boolean addLevelOne(EduSubject eduSubject);

    //添加二级分类
    boolean addLevelTwo(EduSubject eduSubject);
}
