package cn.eduxueyuan.eduoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssFileService {
    //上传到阿里云oss
    String uploadAliyun(MultipartFile file);
}
