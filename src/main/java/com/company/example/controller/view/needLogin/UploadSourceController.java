package com.company.example.controller.view.needLogin;

import com.jtool.http.exception.StatusCodeNot200Exception;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UploadSourceController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String sourcePath = "/userdata1/example/source/";

	// 显示上传图片表单
	@RequestMapping(value = "/uploadPhoto")
	public String uploadPhoto(HttpServletRequest request, @RequestParam(required = false) String userInfo) {

		request.setAttribute("userInfo", userInfo);
		return "uploadsource/upload_photo";
	}

	// 提交上传图片
	@RequestMapping(value = "/uploadPhoto_do")
	public String uploadPhoto_do(HttpServletRequest request, @RequestParam(required = false) String userInfo)
			throws Exception {

		try {

			try {
				if (isImageEmptyValid(request)) {
					request.setAttribute("content", "Image can not be empty.");
					return "uploadsource/upload_photo_fail";
				}
				
				if (!isImageFormatValid(request)) {
					request.setAttribute("content", "Image format error.");
					return "uploadsource/upload_photo_fail";
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				request.setAttribute("content", "Image can not be empty.");
				return "uploadsource/upload_photo_fail";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("content", "Image too large.");
				return "uploadsource/upload_photo_fail";
			}

			makeUploadingFileDir(sourcePath);
			processUploadFile(request);
		} catch (StatusCodeNot200Exception e) {
			log.error(e.getMessage());
			return "uploadsource/upload_photo_fail";
		}

		request.setAttribute("userInfo", userInfo);
		return "uploadsource/upload_photo_success";
	}

	private void makeUploadingFileDir(String uploadingPath) throws IOException {
		File file = new File(uploadingPath);
		if (!file.isDirectory()) {
			FileUtils.forceMkdir(file);
		}
	}

	private static File processUploadFile(HttpServletRequest request) throws IllegalStateException, IOException {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgFile1 = multipartRequest.getFile("pic");

		String pic = System.currentTimeMillis() + ".jpg";
		File file = new File(sourcePath, pic);

		imgFile1.transferTo(file);

		return file;
	}

	private boolean isImageFormatValid(HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgFile1 = multipartRequest.getFile("pic");
		String fileName = imgFile1.getOriginalFilename();

		log.debug("要上传的文件名：" + fileName);

		String ext = FilenameUtils.getExtension(fileName);
		ext = ext == null ? "" : ext.toLowerCase();

		log.debug("文件扩展名为：" + ext);

		List<String> fileTypes = new ArrayList<String>();
		fileTypes.add("jpg");
		fileTypes.add("jpeg");

		return fileTypes.contains(ext);
	}

	private boolean isImageEmptyValid(HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgFile1 = multipartRequest.getFile("pic");
		String fileName = imgFile1.getOriginalFilename();

		log.debug("要上传的文件名：" + fileName);

		return "".equals(fileName);
	}

}
