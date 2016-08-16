package com.company.example.controller;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:/application-context.xml" })
public abstract class CommonControllerTest {

	protected String host = "http://localhost:8080";
	protected String appContentUri = "";
	protected String appContentUrl = host + appContentUri;
	
	@Resource
	private WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;
	protected WebClient webClient;
	protected WebClient webOutClient;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		webOutClient = new WebClient(BrowserVersion.FIREFOX_24, "127.0.0.1", 8118);
		webClient = MockMvcWebClientBuilder
				.webAppContextSetup(webApplicationContext)
				.build();
		webClient.getOptions().setRedirectEnabled(true);

	}
	
	@After
	public void after() {
		/*runner.stop();*/
		webClient.closeAllWindows();
	}
	
	protected MockHttpServletRequestBuilder makePostByParams(String uri, Map<String, Object> params) {
		
		List<String> fileKeys = params.keySet().stream().filter(
				key -> params.get(key) instanceof MockMultipartFile).collect(Collectors.toList());
		
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder;
		
		if(fileKeys.size() == 0) {
			mockHttpServletRequestBuilder = post(uri);
		} else {
			MockMultipartHttpServletRequestBuilder mockMultipartHttpServletRequestBuilder = fileUpload(uri);
			fileKeys.stream().forEach(key -> mockMultipartHttpServletRequestBuilder.file(
					(MockMultipartFile)params.get(key)));
			fileKeys.stream().forEach(key -> params.remove(key));
			mockHttpServletRequestBuilder = mockMultipartHttpServletRequestBuilder;
		}
		
		params.keySet().stream().forEach(e -> mockHttpServletRequestBuilder.param(e, params.get(e).toString()));
		
		System.out.println("request param:" + params);
		
		return mockHttpServletRequestBuilder;
	}

	protected MockHttpServletRequestBuilder makePostByParamsByIP(String uri, Map<String, Object> params, String ip) {

		List<String> fileKeys = params.keySet().stream().filter(
				key -> params.get(key) instanceof MockMultipartFile).collect(Collectors.toList());

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder;

		if(fileKeys.size() == 0) {
			mockHttpServletRequestBuilder = post(uri).header("X-Real-IP", ip);
		} else {
			MockMultipartHttpServletRequestBuilder mockMultipartHttpServletRequestBuilder = fileUpload(uri);
			fileKeys.stream().forEach(key -> mockMultipartHttpServletRequestBuilder.file(
					(MockMultipartFile)params.get(key)));
			fileKeys.stream().forEach(key -> params.remove(key));
			mockHttpServletRequestBuilder = mockMultipartHttpServletRequestBuilder;
		}

		params.keySet().stream().forEach(e -> mockHttpServletRequestBuilder.param(e, params.get(e).toString()));

		System.out.println("request param:" + params);

		return mockHttpServletRequestBuilder;
	}
	
	protected MockHttpServletRequestBuilder makeGetByParams(String uri, Map<String, Object> params) {
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(uri);
		params.keySet().stream().forEach(e -> mockHttpServletRequestBuilder.param(e, params.get(e).toString()));
		
		return mockHttpServletRequestBuilder;
	}

	protected static String currentTimeMillis() {
		return System.currentTimeMillis() + "000";
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T fetchReturnObj(String uri, Map<String, Object> params, String key) throws Exception {
		return (T)this.mockMvc.perform(makePostByParams(uri, params)).andReturn().getRequest().getAttribute(key);
	}
	
	protected MvcResult requestPostUri(String uri, Map<String, Object> params) throws Exception {
		return this.mockMvc.perform(makePostByParams(uri, params)).andReturn();
	}
	
	protected MvcResult requestGetUri(String uri, Map<String, Object> params) throws Exception {
		return this.mockMvc.perform(makeGetByParams(uri, params)).andReturn();
	}
	
	protected MvcResult requestPostUri(String uri) throws Exception {
		return this.mockMvc.perform(makePostByParams(uri, new HashMap<String, Object>())).andReturn();
	}
	
	protected MvcResult requestGetUri(String uri) throws Exception {
		return this.mockMvc.perform(makeGetByParams(uri, new HashMap<String, Object>())).andReturn();
	}
	
	protected void requestParamsException(String uri, Map<String, Object> params) throws Exception {
		this.mockMvc.perform(makePostByParams(uri, params)).andExpect(status().isOk())
				.andExpect(request().attribute("code", -3));
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getAttribute(MockHttpServletRequest mockHttpServletRequest, String key) throws Exception {
		return (T)mockHttpServletRequest.getAttribute(key);
	}
	
	
	@SuppressWarnings("unchecked")
	protected <T> T requestPostUriAndGetAttribute(String uri, Map<String, Object> params, String key)
			throws Exception {

		return (T)requestPostUri(uri, params).getRequest().getAttribute(key);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T requestGetUriAndGetAttribute(String uri, Map<String, Object> params, String key)
			throws Exception {

		return (T)requestGetUri(uri, params).getRequest().getAttribute(key);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T requestPostUriAndGetAttribute(String uri, String key) throws Exception {
		return (T)requestPostUri(uri, new HashMap<String, Object>()).getRequest().getAttribute(key);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T requestGetUriAndGetAttribute(String uri, String key) throws Exception {
		return (T)requestGetUri(uri, new HashMap<String, Object>()).getRequest().getAttribute(key);
	}
	
	protected String getUri(HtmlPage page) {
		return page.getWebResponse().getWebRequest().getUrl().getPath();
	}

	protected String getQueryParam(HtmlPage page, String key) {
		try {
			return splitQuery(page.getUrl()).get(key);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
					URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	protected static Map<String, String> splitQuery(String url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    if(url.indexOf("?") != -1) {
	    	url = url.substring(url.indexOf("?") + 1);
	    }
	    String[] pairs = url.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
					URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	protected String checkSessionIdInAnchorByText(HtmlPage htmlPage, String text)
			throws UnsupportedEncodingException {

		HtmlAnchor htmlAnchor = htmlPage.getAnchorByText(text);
		return splitQuery(htmlAnchor.getHrefAttribute()).get("sessionId");
	}

	protected String checkSessionIdInAnchorById(HtmlPage htmlPage, String id)
			throws UnsupportedEncodingException {

		HtmlAnchor htmlAnchor = (HtmlAnchor)htmlPage.getElementById(id);
		return splitQuery(htmlAnchor.getHrefAttribute()).get("sessionId");
	}

	protected String requestContentStringGet(String uri, Map<String, ?> params) throws UnsupportedEncodingException, Exception {
		Map<String, Object> innerParams = new HashMap<>();
		for(String key : params.keySet()) {
			if(params.get(key) != null) {
				innerParams.put(key, params.get(key));
			}
		}
		System.out.println("发送地址：" + uri);
		String result = this.mockMvc.perform(makeGetByParams(uri, innerParams)).andReturn().getResponse().getContentAsString();
		System.out.println("返回数据：" + result);
		return result;
	}

	protected String requestContentString(String uri, Map<String, ?> params) throws UnsupportedEncodingException, Exception {
		Map<String, Object> innerParams = new HashMap<>();
		for(String key : params.keySet()) {
			if(params.get(key) != null) {
				innerParams.put(key, params.get(key));
			}
		}
		System.out.println("发送地址：" + uri);
		String result = this.mockMvc.perform(makePostByParams(uri, innerParams)).andReturn().getResponse().getContentAsString();
		System.out.println("返回数据：" + result);
		return result;
	}

	protected String requestContentStringByIP(String uri, Map<String, ?> params, String ip) throws UnsupportedEncodingException, Exception {
		Map<String, Object> innerParams = new HashMap<>();
		for(String key : params.keySet()) {
			if(params.get(key) != null) {
				innerParams.put(key, params.get(key));
			}
		}
		System.out.println("发送地址：" + uri);
		String result = this.mockMvc.perform(makePostByParamsByIP(uri, innerParams, ip)).andReturn().getResponse().getContentAsString();
		System.out.println("返回数据：" + result);
		return result;
	}

}
