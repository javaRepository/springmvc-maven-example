package com.company.example.controller.view.noLogin;

import com.company.example.dao.user.UserDAO;
import com.company.example.model.user.User;
import com.company.example.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserDAO userDAO;

	@Resource
	private UserService userService;

	// 登录页面
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,
						@RequestParam(defaultValue = "0") Integer errorType)
			throws Exception {
		// 错误信息标识，默认为0,没有错误
		request.setAttribute("errorType", errorType);

		return "login/login_form";
	}

	// 自动登录
	@RequestMapping(value = "/autoLogin")
	public String autoLogin(HttpServletResponse response, HttpServletRequest request,@RequestParam String username,
							@RequestParam String userpassword, RedirectAttributes redirectAttrs) throws Exception {

		return login(response, request, username, userpassword, redirectAttrs);
	}

	// 执行登录操作
	@RequestMapping(value = "/login_do")
	public String login_do(HttpServletResponse response, HttpServletRequest request, @RequestParam String username,
						   @RequestParam String userpassword, RedirectAttributes redirectAttrs) throws Exception {
		return login(response, request, username, userpassword, redirectAttrs);
	}

	// 封装登录方法，以便共用
	private String login(HttpServletResponse response, HttpServletRequest request, String username,
						 String userpassword, RedirectAttributes redirectAttrs) throws Exception {
		// 验证用户名和密码
		String redirect = validateLoginNameAndPassword(username, userpassword, redirectAttrs);
		if (null != redirect) {
			return redirect;
		}

		log.debug("登录name:" + username);
		log.debug("登录password:" + userpassword);

		if (!userDAO.hasUser(username, userpassword)) {
			redirectAttrs.addAttribute("errorType", 3);
			return "redirect:login";
		}

		userService.addUserToRedis(new User(username, userpassword));
		
		redirectAttrs.addAttribute("userInfo", username);
		return "redirect:home";
	}

	private String validateLoginNameAndPassword(String username, String userpassword, RedirectAttributes redirectAttrs) {
		if (StringUtils.isEmpty(username)) {
			redirectAttrs.addAttribute("errorType", 1);
			return "redirect:login";
		}

		if (StringUtils.isEmpty(userpassword)) {
			redirectAttrs.addAttribute("errorType", 2);
			return "redirect:login";
		}

		return null;
	}

}
