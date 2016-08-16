package com.company.example.controller.view.noLogin;

import com.company.example.dao.user.UserDAO;
import com.company.example.model.user.User;
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
import java.util.Date;


@Controller
public class RegisterController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserDAO userDAO;

	// 注册页面
	@RequestMapping(value = "/register")
	public String register(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer errorType)
			throws Exception {

		request.setAttribute("errorType", errorType);
		return "register/register_form";
	}

	// 提交注册
	@RequestMapping(value = "/register_do")
	public String register_do(HttpServletRequest request, HttpServletResponse response,
							  @RequestParam(required = false) String username,
							  @RequestParam(required = false) String userpassword,
							  RedirectAttributes redirectAttrs) throws Exception {

		log.debug("register user:" + username);

		String redirect = validateUserRegister(request, username, userpassword, redirectAttrs);
		if (null != redirect) {
			return redirect;
		}
		// 发送注册请求
		User user = new User();
		user.setUsername(username);
		user.setUserpwd(userpassword);
		user.setAddtime(new Date());
		userDAO.addUser(user);

		return "redirect:register_success";
	}

	// 注册成功
	@RequestMapping(value = "/register_success")
	public String register_success(HttpServletRequest request) throws Exception {
		String refresh = "10; URL=/autoLogin?username=test123456&userpassword=123456";
		request.setAttribute("Refresh", refresh);

		return "register/register_success";
	}

	// 验证 user注册信息
	private String validateUserRegister(HttpServletRequest request, String username, String userpassword,
										RedirectAttributes redirectAttrs) {

		if (StringUtils.isEmpty(username)) {
			redirectAttrs.addAttribute("errorType", 1);
			return "redirect:register";
		}

		if (username.length() < 6 || username.length() > 16) {
			redirectAttrs.addAttribute("errorType", 2);
			return "redirect:register";
		}

		if (StringUtils.isEmpty(userpassword)) {
			redirectAttrs.addAttribute("errorType", 3);
			return "redirect:register";
		}

		if (userpassword.length() < 6 || userpassword.length() > 16) {
			redirectAttrs.addAttribute("errorType", 4);
			return "redirect:register";
		}

		return null;
	}

}
