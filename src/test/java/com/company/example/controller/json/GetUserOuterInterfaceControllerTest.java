package com.company.example.controller.json;

import com.alibaba.fastjson.JSON;
import com.company.example.controller.CommonControllerTest;
import com.company.example.outerapi.response.GetUserResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class GetUserOuterInterfaceControllerTest extends CommonControllerTest {

	@Test
	public void testGetUser() throws Exception {
		String json = requestContentStringGet("/cmd/getUser?username=test123456", new HashMap<>());
		GetUserResponse response = JSON.parseObject(json, GetUserResponse.class);

		Assert.assertEquals("0", response.getCode());
	}

	@Test
	public void testGetUserParaEmpty() throws Exception {
		String json = requestContentStringGet("/cmd/getUser", new HashMap<>());
		GetUserResponse response = JSON.parseObject(json, GetUserResponse.class);

		Assert.assertEquals("-3", response.getCode());
	}

}
