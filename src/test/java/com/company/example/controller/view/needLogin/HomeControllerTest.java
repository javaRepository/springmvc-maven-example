package com.company.example.controller.view.needLogin;

import com.company.example.controller.CommonControllerTest;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.company.example.util.HtmlUtilHelper.clickAnchor;
import static com.company.example.util.HtmlUtilHelper.pageContains;


public class HomeControllerTest extends CommonControllerTest {

	private String indexUri = "/";

	@Test
	public void test() throws Exception {

		home();

		noSessionHome();
	}

	private void noSessionHome() throws IOException{

		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri + "home");

		Assert.assertEquals("Not Login Info", indexPage.getTitleText());
		Assert.assertTrue(pageContains(indexPage, "homepage"));

	}

	//进入home页面
	private void home() throws IOException{

		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri);

		HtmlPage loginPage = clickAnchor(indexPage, "Login");

		HtmlForm form = loginPage.getFormByName("login_form");
		HtmlTextInput username = form.getInputByName("username");
		username.setValueAttribute("test123456");
		HtmlPasswordInput passwordField = form.getInputByName("userpassword");
		passwordField.setValueAttribute("123456");
		HtmlSubmitInput submitButton = form.getInputByName("submit");
		HtmlPage loginInfoPage = submitButton.click();

		Assert.assertEquals("Home", loginInfoPage.getTitleText());
		Assert.assertTrue(pageContains(loginInfoPage, "Home"));

	}

}
