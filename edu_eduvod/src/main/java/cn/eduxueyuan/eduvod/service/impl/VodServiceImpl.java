package cn.eduxueyuan.eduvod.service.impl;

import cn.eduxueyuan.eduvod.service.VodService;
import cn.eduxueyuan.eduvod.utils.AliyunVodSDKUtils;
import cn.eduxueyuan.eduvod.utils.ConstantPropertiesUtil;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    //上传视频到阿里云操作
    @Override
    public String uploadAliyunVod(MultipartFile file) {
        try {
            //1 创建UploadStreamRequest对象，传递参数
            //fileName 上传文件名称
            String fileName = file.getOriginalFilename();
            //title   01.6.6.jpg
            //经常传递文件名称不带后缀名名字
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            //inputStream 文件输入流
            InputStream in = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, in);

            //创建UploadVideoImpl对象
            UploadVideoImpl uploader = new UploadVideoImpl();
            //调用UploadVideoImpl对象里面的方法
            UploadStreamResponse response = uploader.uploadStream(request);
            //通过response对象获取返回视频id
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据视频id删除阿里云视频
    @Override
    public void removeVideoAliyun(String videoId) {
        try {
            //1 获取初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //2 创建删除视频request和response对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            //3 向request对象里面设置视频id值
            request.setVideoIds(videoId);
            //调用client方法实现删除
            response = client.getAcsResponse(request);
            System.out.println(response.getRequestId());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //删除多个阿里云视频的方法
    @Override
    public void deleteMoreVideo(List videoIdList) {
        try {
            //1 获取初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //2 创建删除视频request和response对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            //3 向request对象里面设置视频id值
            //VideoId1,VideoId2
            //list集合数据变成  1,2,3
            String ids = StringUtils.join(videoIdList.toArray(), ",");

            request.setVideoIds(ids);
            //调用client方法实现删除
            response = client.getAcsResponse(request);
            System.out.println(response.getRequestId());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] aa) {
//        String fileName = "09998.jpg";
//        String title = fileName.substring(0,fileName.lastIndexOf("."));
//        System.out.println(title);

        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("12");
        list.add("13");

        String join = StringUtils.join(list.toArray(), ",");
        System.out.println(join);
    }
}
