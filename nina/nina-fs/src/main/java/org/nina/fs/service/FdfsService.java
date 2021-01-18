package org.nina.fs.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * fastdfs操作接口
 * @author riverplant
 *
 */
public interface FdfsService {
    //分布式文件上传
	public String upload(MultipartFile file, String fileExtName) throws Exception;
	//aliyun oss
	public String uploadOss(MultipartFile file, String userId, String fileExtName) throws Exception;
}
