package cn.eduxueyuan.eduvod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    //上传视频到阿里云操作
    String uploadAliyunVod(MultipartFile file);

    //根据视频id删除阿里云视频
    void removeVideoAliyun(String videoId);

    //删除多个阿里云视频的方法
    void deleteMoreVideo(List videoIdList);
}
