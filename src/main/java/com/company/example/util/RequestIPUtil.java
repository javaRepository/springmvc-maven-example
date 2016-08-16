package com.company.example.util;

import javax.servlet.http.HttpServletRequest;

public class RequestIPUtil {

	public static String getIp(HttpServletRequest request) {
		String realIP = request.getHeader("X-Real-IP");
		realIP = realIP != null ? realIP : request.getRemoteAddr();
		if(realIP.equals("127.0.0.1")) {
			return "127.0.0.1";
		} else {
			return realIP;
		}
		
	}

}
