package cn.eduxueyuan.eduservice.entity.query;

import lombok.Data;

@Data
public class PublishCourseInfo {
    private String id;//课程id
    private String title;//课程名称
    private String cover;//封面
    private Integer lessonNum;//课时数
    private String subjectLevelOne;//一级分类
    private String subjectLevelTwo;//二级分类
    private String teacherName;//讲师名称
    private String price;//只用于显示
}
