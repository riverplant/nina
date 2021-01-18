package org.nina.fs.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 与资源映射
 * 
 * @author riverplant
 *
 */
@Component
// 前缀
@ConfigurationProperties(prefix = "file")
// 文件的原地址
@PropertySource("classpath:file.properties")
public class FileResources {

	private String host;

	private String endpoint;

	private String accessKeyId;

	private String accessKeySecret;

	private String bucketName;

	private String objectName;

	private String ossHost;

	public String getOssHost() {
		return ossHost;
	}

	public void setOssHost(String ossHost) {
		this.ossHost = ossHost;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
