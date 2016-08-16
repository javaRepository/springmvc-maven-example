package com.company.example.controller.view.noLogin;

import com.company.example.controller.CommonControllerTest;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.company.example.util.HtmlPageForm.getFormByName;
import static com.company.example.util.HtmlUtilHelper.clickAnchor;
import static com.company.example.util.HtmlUtilHelper.pageContains;


public class LoginControllerTest extends CommonControllerTest {

	private String indexUri = "/";

	@Test
	public void test() throws Exception {

		testAutoLogin();

		testIndexClickLogin();

		testLoginerror();
	}

	// 自动登录
	private void testAutoLogin() throws IOException {
		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri + "autoLogin?username=test123456&userpassword=123456");

		Assert.assertEquals("Home", indexPage.getTitleText());
		Assert.assertTrue(pageContains(indexPage, "Home"));

	}

	//从index页面点击 进入login
	private void testIndexClickLogin() throws IOException {
		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri);

		HtmlPage loginPage = clickAnchor(indexPage, "Login");

		HtmlPage homePage = getFormByName(loginPage, "login_form").setInput("username",
				"test123456").setInput("userpassword", "123456").submit();
		Assert.assertTrue(pageContains(homePage, "Home"));

	}

	//登录
	private void testLoginerror() throws IOException {
		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri);

		HtmlPage loginPage = clickAnchor(indexPage, "Login");

		HtmlForm form = loginPage.getFormByName("login_form");
		HtmlSubmitInput submitButton = form.getInputByName("submit");
		HtmlPage loginInfoPage = submitButton.click();
		Assert.assertTrue(pageContains(loginInfoPage, "Please enter the username."));

		HtmlTextInput username = form.getInputByName("username");
		username.setValueAttribute("test123456");
		loginInfoPage = submitButton.click();
		Assert.assertTrue(pageContains(loginInfoPage, "Please enter the password."));

		HtmlPasswordInput passwordField = form.getInputByName("userpassword");
		username.setValueAttribute("test123456");
		passwordField.setValueAttribute("1234");
		loginInfoPage = submitButton.click();
		Assert.assertTrue(pageContains(loginInfoPage,
				"Invalid account or password. Please enter again."));

		username.setValueAttribute("test123456");
		passwordField.setValueAttribute("123456");
		HtmlPage homePage = submitButton.click();

		Assert.assertEquals("Home", homePage.getTitleText());
		Assert.assertTrue(pageContains(homePage, "Home"));
	}

}
