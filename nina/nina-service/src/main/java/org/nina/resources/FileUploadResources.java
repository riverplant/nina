package org.nina.resources;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:file-upload-dev.properties")
public class FileUploadResources {

	private String imageUserFaceLocation;
	private String imageServiceUrl;

	
	public String getImageServiceUrl() {
		return imageServiceUrl;
	}

	public void setImageServiceUrl(String imageServiceUrl) {
		this.imageServiceUrl = imageServiceUrl;
	}

	public String getImageUserFaceLocation() {
		return imageUserFaceLocation;
	}

	public void setImageUserFaceLocation(String imageUserFaceLocation) {
		this.imageUserFaceLocation = imageUserFaceLocation;
	}

}
