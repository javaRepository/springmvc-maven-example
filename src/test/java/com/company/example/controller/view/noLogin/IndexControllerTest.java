package com.company.example.controller.view.noLogin;

import com.company.example.controller.CommonControllerTest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.company.example.util.HtmlUtilHelper.pageContains;
import static com.company.example.util.HtmlUtilHelper.clickAnchor;

public class IndexControllerTest extends CommonControllerTest {

	private String indexUri = "/";

	@Test
	public void test() throws Exception {

		testIndex();

		toLogin();

		toRegister();
	}

	private void toRegister() throws IOException {
		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri);

		Assert.assertEquals("Index", indexPage.getTitleText());
		Assert.assertTrue(pageContains(indexPage, "Sign Up."));

		HtmlPage regPage = clickAnchor(indexPage, "Sign Up.");
		Assert.assertEquals("Register", regPage.getTitleText());
		Assert.assertTrue(pageContains(regPage, "UserName"));
	}

	private void toLogin() throws IOException {
		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri);

		Assert.assertEquals("Index", indexPage.getTitleText());
		Assert.assertTrue(pageContains(indexPage, "Login"));

		HtmlPage loginPage = clickAnchor(indexPage, "Login");
		Assert.assertEquals("Login", loginPage.getTitleText());
		Assert.assertTrue(pageContains(loginPage, "UserName"));
	}

	private void testIndex() throws IOException {
		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri);
		
		Assert.assertEquals("Index", indexPage.getTitleText());
		Assert.assertTrue(pageContains(indexPage, "Login"));

	}

}
