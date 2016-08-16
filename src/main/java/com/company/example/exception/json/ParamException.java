package com.company.example.exception.json;

import com.jtool.codegenannotation.CodeGenExceptionDefine;
import com.jtool.doc.annotation.DocExceptionDefine;

@CodeGenExceptionDefine(code="-3", desc="参数错误")
@DocExceptionDefine
public class ParamException extends MyCheckedException {
	private static final long serialVersionUID = -5557637960378865183L;

	public static final String code = "-3";
	public static final String desc = "参数错误";

	public ParamException() {
		this.setCode(code);
	}

	public ParamException(String paramJson) {
		super(paramJson);
		this.setCode(code);
		this.setDesc(desc);
	}

}
