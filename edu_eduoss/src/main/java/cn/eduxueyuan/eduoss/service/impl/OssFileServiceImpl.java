package cn.eduxueyuan.eduoss.service.impl;

import cn.eduxueyuan.eduoss.service.OssFileService;
import cn.eduxueyuan.eduoss.utils.ConstantPropertiesUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssFileServiceImpl implements OssFileService {

    //上传文件到阿里云oss
    @Override
    public String uploadAliyun(MultipartFile file) {
        try {
            // 1 获取上传需要的固定值
            String endpoint = ConstantPropertiesUtil.END_POINT;
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

            //2 创建OssClient对象
            OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);

            //3 获取文件信息，为了上传
            //文件输入流
            InputStream in = file.getInputStream();
            //文件名称
            String fileName = file.getOriginalFilename();

            //添加uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            // 01.jpg
            //234joewtjrer3501.jpg
            fileName = uuid+fileName;

            //4 调用ossClient方法实现上传
            //   /2019/07/30/34joewtjrer3501.jpg
            //实现出 /2019/07/30/
            String dateUrl = new DateTime().toString("yyyy/MM/dd");

            fileName = dateUrl+"/"+fileName;
            //第一个参数bucketName
            //第二个参数是文件名称（包含路径）
            //第三个参数是文件输入流
            ossClient.putObject(bucketName, fileName, in);

            //  504  timeout
             //5 关闭ossClient
            ossClient.shutdown();

            //返回上传之后地址，拼接地址
            //https://edu-demo0311.oss-cn-beijing.aliyuncs.com/2019/07/30/bc22737276904a74bb5242f00bc1c01801.jpg
            String uploadUrl = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return uploadUrl;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
