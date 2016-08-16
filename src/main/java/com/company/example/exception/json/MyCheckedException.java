package com.company.example.exception.json;

public abstract class MyCheckedException extends Exception{
	private static final long serialVersionUID = 548781340282849707L;

	public String code;
	public String desc;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public MyCheckedException(String message) {
		super(message);
	}

	public MyCheckedException() {
		super();
	}
	
}
