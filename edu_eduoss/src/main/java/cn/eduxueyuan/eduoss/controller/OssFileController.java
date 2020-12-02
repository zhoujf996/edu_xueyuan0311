package cn.eduxueyuan.eduoss.controller;

import cn.eduxueyuan.common.R;
import cn.eduxueyuan.eduoss.service.OssFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/oss")
@CrossOrigin
public class OssFileController {

    @Autowired
    private OssFileService ossFileService;

    //上传文件到阿里云oss的方法
    @PostMapping("upload")
    public R uploadFileAliyunOss(MultipartFile file) {
        //1 获取上传文件  MultipartFile file
        //file参数名称不是随便写的 和文件上传输入项里面name属性值一样 <input type="file" name="file"/>
        String url = ossFileService.uploadAliyun(file);

        return R.ok().data("url",url);
    }
}
