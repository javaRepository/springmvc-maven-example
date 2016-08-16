package com.company.example.aspects;

import com.company.example.exception.view.NotLoginException;
import com.company.example.util.RequestIPUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Repository
public class IsLoginAspect {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	//拦截needLogin包子包，都需要登录信息
    @Before("execution(public * com.company.example.controller.view.needLogin..*Controller.*(..)) && args(request, ..)")
    public void logbefore(HttpServletRequest request){
		checkUserInfo(request);

		checkIP(request);

    }

	//判断登录的ip和操作时的ip地址是不是同一个
	private void checkIP(HttpServletRequest request) {
		String loginIp = "127.0.0.1";
		log.debug("IsLoginAspect check loginIp:" + loginIp + ",requestIp:" + RequestIPUtil.getIp(request));
		if (null == loginIp || !RequestIPUtil.getIp(request).equals(loginIp)) {
			throw new NotLoginException();
		}
	}

	//判断有没有用户信息
	private void checkUserInfo(HttpServletRequest request) {
		String userInfo = request.getParameter("userInfo");
		log.debug("IsLoginAspect check userInfo:" + userInfo);
		if (userInfo == null) {
			throw new NotLoginException();
		}
	}

}
