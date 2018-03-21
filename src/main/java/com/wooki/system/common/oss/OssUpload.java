package com.wooki.system.common.oss;

import com.aliyun.oss.OSSClient;
import com.wooki.config.AppConfig;
import com.wooki.util.common.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/7
 * Time : 下午11:53
 */
@Component
public class OssUpload {

    @Autowired
    AppConfig appConfig;

    public String upload(MultipartFile file)throws IOException{
//    public String upload(InputStream stream){
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        // 创建OSSClient实例
        String originName = file.getOriginalFilename();
        String fileName = MD5Util.encode(originName+"_"+file.getSize())+originName.substring(originName.lastIndexOf("."));
        InputStream stream = file.getInputStream();
        return ossUpload(fileName,stream);
    }

    // 上传base64
    public String uploadByBase64Str(String imgStr) throws Exception{
        SerialBlob serialBlob = decodeToImage(imgStr);
        InputStream stream = serialBlob.getBinaryStream();
        String fileName = MD5Util.encode(imgStr)+".jpg";
        return ossUpload(fileName,stream);
    }

    public static SerialBlob decodeToImage(String imageString) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(imageString);
        return new SerialBlob(imageByte);
    }

    private String ossUpload(String fileName,InputStream stream){
        OSSClient ossClient = new OSSClient(appConfig.getEndPoint(), appConfig.getAliAccessKeyId(), appConfig.getAccessKeySecret());
        // 上传文件
        ossClient.putObject(appConfig.getBucketName(), fileName, stream);
        // 关闭client
        ossClient.shutdown();
        return appConfig.getPicturePrefix()+fileName;
    }
}
