package com.company.example.controller.json;

import com.alibaba.fastjson.JSON;
import com.company.example.dao.user.UserDAO;
import com.company.example.exception.json.ParamException;
import com.company.example.model.user.User;
import com.company.example.outerapi.request.GetUserRequest;
import com.company.example.outerapi.response.GetUserResponse;
import com.jtool.codegenannotation.CodeGenApi;
import com.jtool.codegenannotation.CodeGenRequest;
import com.jtool.codegenannotation.CodeGenResponse;
import com.jtool.support.log.LogHelper;
import com.jtool.validator.ParamBeanValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
public class GetUserOuterInterfaceController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserDAO userDAO;

	/*
	<logicInfo>
		1:查询用户信息<br/>
	</logicInfo>
	 */
	
	//查询user list
	@CodeGenApi(name = "(外)查询用户", docSeq = 1.0, description = "查询用户信息")
	@CodeGenRequest(GetUserRequest.class)
	@CodeGenResponse(GetUserResponse.class)
	@ResponseBody
	@RequestMapping(value = "/cmd/getUser", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String getUser(GetUserRequest getUserRequest) throws ParamException {
		log.debug("查询用户request：" + JSON.toJSONString(getUserRequest));

		validateRequestParam(getUserRequest);
		String username = getUserRequest.getUsername();
		LogHelper.setLogUserId(username);

		GetUserResponse getUserResponse = new GetUserResponse();
		getUserResponse.setCode("0");
		Optional<User> user = userDAO.getUser(username);
		if (user.isPresent()) {
			getUserResponse.setUser(user.get());
		}

		log.debug("查询用户response：" + JSON.toJSONString(getUserResponse));
		return JSON.toJSONString(getUserResponse);
	}

	private void validateRequestParam(GetUserRequest getUserRequest) throws ParamException {
		if (ParamBeanValidator.isNotValid(getUserRequest)) {
			throw new ParamException(getUserRequest.toString());
		}
	}

}
