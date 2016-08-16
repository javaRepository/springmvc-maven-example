package com.company.example.util;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

import java.io.IOException;

public class HtmlPageForm {
	
	private HtmlForm htmlForm;

	private HtmlPageForm(HtmlPage htmlPage, String formName) {
		super();
		this.htmlForm = htmlPage.getFormByName(formName);
	}
	
	public static HtmlPageForm getFormByName(HtmlPage htmlPage, String formName) {
		return new HtmlPageForm(htmlPage, formName); 
	}
	
	public HtmlPageForm setInput(String inputName, String value) {
		this.htmlForm.getInputByName(inputName).setValueAttribute(value);
		return this;
	}
	
	public HtmlPageForm setSelect(String name, String value) {
		HtmlSelect select = (HtmlSelect)this.htmlForm.getSelectByName(name);
		select.setSelectedAttribute(value, true);
		return this;
	}
	
	public HtmlPage submit() throws ElementNotFoundException, IOException{
		return submit("submit");
	}
	
	public HtmlPage submit(String submitName) throws ElementNotFoundException, IOException{
		return this.htmlForm.getInputByName(submitName).click();
	}

	public HtmlForm getHtmlForm() {
		return htmlForm;
	}
	
	
}
