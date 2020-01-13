package org.nina.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.nina.dto.FileInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
	/**
	 * 
	 * @param file :参数名与MockMultipartFile构造第一个参数名保持一致
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@PostMapping("/upload")
	public FileInfo upload(MultipartFile file) throws IllegalStateException, IOException {

		System.out.println(file.getContentType());
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		String desPath = "J:\\riverplant\\nina\\nina-api\\src\\main\\java\\org\\nina\\api\\controller";
		String extention = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		File localfile = new File(desPath, new Date().getTime() + "." + extention);
		file.transferTo(localfile);
		return new FileInfo(localfile.getName());
	}

	@GetMapping("/downsload")
	public void downsload(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		String filePath = "J:\\riverplant\\nina\\nina-api\\src\\main\\java\\org\\nina\\api\\controller\\";
		try (InputStream inputStream = new FileInputStream(new File(filePath));
				OutputStream outputStream = response.getOutputStream();) {
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition", "attachment;filename=test.txt");
			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
