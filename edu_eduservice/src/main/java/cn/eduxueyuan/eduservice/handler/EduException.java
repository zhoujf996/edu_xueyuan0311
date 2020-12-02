package cn.eduxueyuan.eduservice.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //有参数构造
@NoArgsConstructor //无参数构造
public class EduException extends RuntimeException{
    //异常状态码
    private Integer code;
    //异常描述
    private String message;
}
