package com.company.example.util;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class HtmlUtilHelper {

	public static boolean pageContains(Page page, String text) {
		return page.getWebResponse().getContentAsString().contains(text);
	}

	public static HtmlPage clickAnchor(Page page, String text) throws ElementNotFoundException, IOException {
		return ((HtmlPage) page).getAnchorByText(text).click();
	}

	public static HtmlPage clickAnchorById(Page page, String id) throws ElementNotFoundException, IOException {
		return ((HtmlAnchor) ((HtmlPage) page).getElementById(id)).click();
	}

	public static boolean hasAnchorByText(Page page, String text) throws ElementNotFoundException, IOException {
		try {
			((HtmlPage) page).getAnchorByText(text);
			return true;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}

	public static boolean hasElementById(Page page, String id) throws ElementNotFoundException, IOException {
		DomElement de = ((HtmlPage) page).getElementById(id);
		return de != null;
	}

}
