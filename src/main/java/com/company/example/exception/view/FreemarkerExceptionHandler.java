package com.company.example.exception.view;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.stereotype.Repository;

import java.io.Writer;

@Repository
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

	@Override
	public void handleTemplateException(TemplateException e, Environment environment, Writer writer)
			throws TemplateException {

		throw new FreemarkerException("freemarker error:" + e);

	}

}
