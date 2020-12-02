package cn.eduxueyuan.eduvod.controller;

import cn.eduxueyuan.common.R;
import cn.eduxueyuan.eduvod.service.VodService;
import cn.eduxueyuan.eduvod.utils.AliyunVodSDKUtils;
import cn.eduxueyuan.eduvod.utils.ConstantPropertiesUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/vid")
@CrossOrigin
@Api(description = "阿里云视频操作接口")
public class VodController {

    @Autowired
    private VodService vodService;

    //根据视频id获取视频播放凭证
    @GetMapping("getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable String vid) {
        try {
            //获取初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.
                    initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            //向request设置视频id
            request.setVideoId(vid);
            //调用方法实现
            response = client.getAcsResponse(request);
            //通过response获取播放凭证
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
    //删除多个阿里云视频的方法
    //传递多个视频id，使用list集合
    @DeleteMapping("removeMoreVideo")
    public R removeMoreVideo(@RequestParam("videoIdList") List videoIdList) {
        vodService.deleteMoreVideo(videoIdList);
        return R.ok();
    }

    //上传视频到阿里云操作
    @PostMapping("uploadVideoAliyun")
    public R uploadVideoAliyun(MultipartFile file) {
        String videoId = vodService.uploadAliyunVod(file);
        return R.ok().data("videoId",videoId);
    }

    //根据视频id删除阿里云视频
    @DeleteMapping("deleteVideoAli/{videoId}")
    public R deleteVideoAli(@PathVariable String videoId) {
        vodService.removeVideoAliyun(videoId);
        return R.ok();
    }
}
