package com.company.example.task;

import com.alibaba.fastjson.JSON;
import com.company.example.dao.user.UserDAO;
import com.jtool.support.log.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.UUID;

@Repository
public class UserSelectTaskJob {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserDAO userDAO;

	public void run() throws InterruptedException {
		//定时器没有经过Filter,所以手动加上_logId方便查询日志
		LogHelper.setLogId(UUID.randomUUID().toString());
		LogHelper.setProjectName("example");

		log.debug("user select task job start");
		log.debug(JSON.toJSONString(userDAO.getUserList()));
		log.debug("user select task job end");
	}

}
