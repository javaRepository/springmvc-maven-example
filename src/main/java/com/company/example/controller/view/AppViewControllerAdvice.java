package com.company.example.controller.view;

import com.company.example.exception.view.NotLoginException;
import com.jtool.http.exception.HttpRequestException;
import com.jtool.http.exception.StatusCodeNot200Exception;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice(basePackages = "com.company.example.controller.view")
public class AppViewControllerAdvice {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// 当com.company.example.controller.view包有异常时，都会调用此处处理返回对应错误页面
	@ExceptionHandler
	public ModelAndView processException(HttpServletRequest request, Exception e) throws IOException {
		
		if(e instanceof StatusCodeNot200Exception) {
			StatusCodeNot200Exception scn2e = ((StatusCodeNot200Exception) e);
			log.debug(scn2e.getStatusCode() + "\t" + scn2e.getUrl() + "\t" + scn2e.getParams().toString());
		}

		if(e instanceof NotLoginException) {
			return new ModelAndView("not_login_exception");
		}

		try {
			doWithExceptionFromHttpComponents(request, e);
		} catch (Exception e1) {
			ModelAndView model = new ModelAndView();
			model.setView(new RedirectView("home"));
			model.addObject("userInfo", request.getAttribute("userInfo"));
			return model;
		}

		log.error("Catch Exception: ", e);// 把漏网的异常信息记入日志
		String errorMsg = e.getMessage() + "\t" + ExceptionUtils.getStackTrace(e);
		saveErrorLogToDB(errorMsg);
		return new ModelAndView("error", "errorMsg", errorMsg);
		
	}

	private void doWithExceptionFromHttpComponents(HttpServletRequest request, Exception ex) throws Exception {
		if(ex instanceof HttpRequestException || ex instanceof StatusCodeNot200Exception) {
			log.error("Http Exception message:" + ex.getMessage() + "\t" + ex);

			if(ex instanceof StatusCodeNot200Exception) {
				StatusCodeNot200Exception statusCodeNot200Exception = (StatusCodeNot200Exception)ex;
				log.debug("StatusCodeNot200Exception: " + statusCodeNot200Exception.getStatusCode() + " url:" +
						statusCodeNot200Exception.getUrl());
			}

			ex.printStackTrace();
		}
	}

	//错误日志保存到数据库
	private void saveErrorLogToDB(String errorMsg) {
		log.debug("to DB:" + errorMsg);
	}

}
