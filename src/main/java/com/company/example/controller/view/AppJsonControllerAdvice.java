package com.company.example.controller.view;

import com.company.example.exception.json.MyCheckedException;
import com.jtool.http.exception.StatusCodeNot200Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

@ControllerAdvice(basePackages = "com.company.example.controller.json")
public class AppJsonControllerAdvice {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// 当com.company.example.controller.json包有异常时，都会调用此处处理,返回错误的json数据
	@ExceptionHandler
	public void processException(HttpServletResponse response, Exception e) throws IOException {
		
		if(e instanceof StatusCodeNot200Exception) {
			StatusCodeNot200Exception scn2e = ((StatusCodeNot200Exception) e);
			log.debug(scn2e.getStatusCode() + "\t" + scn2e.getUrl() + "\t" + scn2e.getParams().toString());
		}

		try (Writer writer = response.getWriter()) {
			if (e instanceof MyCheckedException) {
				MyCheckedException myCheckedException = (MyCheckedException) e;
				String responseString = "{\"code\":\"" + myCheckedException.getCode() + "\"}";
				writer.write(responseString);
				log.debug(myCheckedException.getDesc() + ":\t" + responseString);
			} else {
				e.printStackTrace();
				String eid = UUID.randomUUID().toString();
				log.error("未知错误-99\teid:" + eid + "\t" + e.getMessage());
				writer.write("{\"code\":\"-99\", \"eid\":\"" + eid + "\"}");
			}

		}
		
	}

}
