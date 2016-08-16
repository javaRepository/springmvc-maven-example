package com.company.example.controller.view.noLogin;

import com.company.example.controller.CommonControllerTest;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import static com.company.example.util.HtmlUtilHelper.clickAnchor;
import static com.company.example.util.HtmlUtilHelper.pageContains;


public class RegisterControllerTest extends CommonControllerTest {

	private String indexUri = "/";

	@Test
	public void test() throws Exception {

		testIndexToRegister();

		testRegister();
	}

	//从index到注册页面
	private void testIndexToRegister() throws IOException, MalformedURLException {
		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri);

		HtmlPage regPage = clickAnchor(indexPage, "Sign Up.");

		Assert.assertEquals("Register", regPage.getTitleText());
		Assert.assertTrue(pageContains(regPage, "UserName"));

	}

	//注册
	private void testRegister() throws IOException {
		HtmlPage indexPage = webClient.getPage(appContentUrl + indexUri);

		HtmlPage regPage = clickAnchor(indexPage, "Sign Up.");

		Assert.assertEquals("Register", regPage.getTitleText());
		Assert.assertTrue(pageContains(regPage, "UserName"));
		
		HtmlForm form = regPage.getFormByName("registerForm");
		HtmlSubmitInput submitButton = form.getInputByName("submit");
		HtmlPage registerPage = submitButton.click();
		Assert.assertTrue(pageContains(registerPage, "Please enter the username"));

		HtmlTextInput usernameField = form.getInputByName("username");
		usernameField.setValueAttribute("1234");
		registerPage = submitButton.click();
		Assert.assertTrue(pageContains(registerPage,
				"The username must be 6 to 16 characters, letters and numbers are allowed"));


		usernameField.setValueAttribute(System.currentTimeMillis() + "");
		registerPage = submitButton.click();
		Assert.assertTrue(pageContains(registerPage, "Please enter the password."));

		usernameField.setValueAttribute(System.currentTimeMillis() + "");
		HtmlPasswordInput passwordField = form.getInputByName("userpassword");
		passwordField.setValueAttribute("1234");
		registerPage = submitButton.click();
		Assert.assertTrue(pageContains(registerPage,
				"The password must be 6 to 16 characters, letters and numbers are allowed"));

		usernameField.setValueAttribute(System.currentTimeMillis() + "");
		passwordField.setValueAttribute("987654321");
		registerPage = submitButton.click();

		Assert.assertEquals("Register Success", registerPage.getTitleText());
		Assert.assertTrue(pageContains(registerPage, "Success"));
	}

}
