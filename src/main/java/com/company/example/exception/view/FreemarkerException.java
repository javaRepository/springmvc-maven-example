package com.company.example.exception.view;

public class FreemarkerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FreemarkerException(String error) {
		super(error);
	}

}
