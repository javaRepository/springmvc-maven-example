package com.company.example.model.user;

import com.jtool.codegenannotation.CodeGenField;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class User {

	@NotNull
	@CodeGenField("用户id")
	private Integer id;

	@NotNull
	@CodeGenField("用户名")
	private String username;

	@NotNull
	@CodeGenField("用户密码")
	private String userpwd;

	@NotNull
	@CodeGenField("加入时间")
	private Date addtime;

	public User() {}

	public User(String username, String userpwd) {
		this.username = username;
		this.userpwd = userpwd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", userpwd='" + userpwd + '\'' +
				", addtime=" + addtime +
				'}';
	}

}
