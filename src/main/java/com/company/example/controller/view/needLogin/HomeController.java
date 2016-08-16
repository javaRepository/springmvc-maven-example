package com.company.example.controller.view.needLogin;

import com.company.example.model.user.User;
import com.company.example.service.user.UserService;
import com.jtool.support.log.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserService userService;

	// 显示home
	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request,
					   @RequestParam(required = false) String userInfo) throws Exception {

		log.debug("into home" + userInfo);

		User user = userService.getUserByRedis(userInfo);
		if (null != user) {
			LogHelper.setLogUserId(user.getUsername());
		}

		log.debug("get user by reids:" + user.toString());

		return "home";
	}

}
