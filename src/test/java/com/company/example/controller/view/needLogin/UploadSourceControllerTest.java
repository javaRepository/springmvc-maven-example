package com.company.example.controller.view.needLogin;

import com.company.example.controller.CommonControllerTest;
import com.company.example.util.MockMultipartFileUtils;
import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.html.HTMLParser;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.company.example.util.HtmlUtilHelper.pageContains;


public class UploadSourceControllerTest extends CommonControllerTest {

	@Test
	public void test() throws Exception {

		uploadPhoto();

	}

	//上传照片
	private void uploadPhoto() throws Exception {
		MockMultipartFile jpgFile = MockMultipartFileUtils.makeMockMultipartFile("pic", "/media/pic.txt");
		Map<String, Object> params = new HashMap<>();
		params.put(jpgFile.getName(), jpgFile);
		String uploadPhotoUri = "/uploadPhoto_do?userInfo=test123456";

		String source = this.mockMvc.perform(makePostByParams(uploadPhotoUri, params)).andReturn().getResponse().getContentAsString();
		URL uploadAvatarDoUrl = new URL(appContentUrl + uploadPhotoUri);
		StringWebResponse response = new StringWebResponse(source, uploadAvatarDoUrl);
		HtmlPage page = HTMLParser.parseHtml(response, webClient.getCurrentWindow());
		Assert.assertEquals("Upload Source", page.getTitleText());
		Assert.assertTrue(pageContains(page, "Image format error"));

		jpgFile = MockMultipartFileUtils.makeMockMultipartFile("pic", "/media/1.jpg");
		params.put(jpgFile.getName(), jpgFile);
		source = this.mockMvc.perform(makePostByParams(uploadPhotoUri, params)).andReturn().getResponse().getContentAsString();
		uploadAvatarDoUrl = new URL(appContentUrl + uploadPhotoUri);
		response = new StringWebResponse(source, uploadAvatarDoUrl);
		page = HTMLParser.parseHtml(response, webClient.getCurrentWindow());
		Assert.assertTrue(pageContains(page, "Upload photo success"));
	}

}
