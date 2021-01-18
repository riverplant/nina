package org.nina.fs.service;

import java.io.InputStream;

import org.nina.fs.resource.FileResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

@Service
@Transactional
public class FdfsServiceImpl implements FdfsService {
    @Autowired
	private FastFileStorageClient fastFileStorageClient;
    
    @Autowired
	private FileResources  fileResources;
    
	@Override
	public String upload(MultipartFile file, String fileExtName) throws Exception {
		/**
	     *
	     * @param inputStream
	     * @param fileSize
	     * @param fileExtName
	     * @param metaDataSet
	     * @return
	     */
		StorePath storePath = fastFileStorageClient.uploadFile(
				file.getInputStream(),
				file.getSize(),
				fileExtName,
				null);
		return storePath.getFullPath();
	}
	/**
	 *aliuyun oss
	 *file:传入的文件
	 *userId
	 *fileExtName：文件的后缀名(.jpg)
	 */
	@Override
	public String uploadOss(MultipartFile file, String userId, String fileExtName) throws Exception {
		//构建OSS CLient
		OSS ossClient = new OSSClientBuilder().build(
				fileResources.getEndpoint(),
				fileResources.getAccessKeyId(),
				fileResources.getAccessKeySecret());
		//获得文件输入流
		InputStream inputStream = file.getInputStream();
		
		String myObjectName = fileResources.getObjectName()+ "/" + userId + "." + fileExtName;

		ossClient.putObject(fileResources.getBucketName(), myObjectName, inputStream);
		
		ossClient.shutdown();
		//可以直接返回myObjectName，最后的访问地址在外部进行拼接
		return fileResources.getOssHost()+myObjectName;
	}

}
