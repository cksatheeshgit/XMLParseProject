package com.SAXParser;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {	
	
	boolean breport = false;
	boolean bsection = false;
	boolean bbold = false;
	boolean bitalic = false;
	boolean bendbold = false;
	boolean benditalic = false;
	int sectionLevel = 0;
	boolean isHeader = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {

		if (qName.equalsIgnoreCase("REPORT")) {
			breport = true;
		}

		if (qName.equalsIgnoreCase("SECTION")) {
			bsection = true;
			sectionLevel++;

			if (bendbold || benditalic || isHeader) {
				System.out.print("\n");
				bendbold = false;
				benditalic = false;
			}
			String text = attributes.getValue("heading");
			String padText = repeat("=", sectionLevel);
			System.out.println(padText + text + padText);

		}

		if (qName.equalsIgnoreCase("BOLD")) {
			bsection = false;
			bbold = true;
		}

		if (qName.equalsIgnoreCase("ITALIC")) {
			bitalic = true;
			bsection = false;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("SECTION")) {
			sectionLevel--;
		}

		if (qName.equalsIgnoreCase("BOLD")) {
			String padText = repeat("'", 3);
			System.out.print(padText);
			bendbold = true;
		}

		if (qName.equalsIgnoreCase("ITALIC")) {
			String padText = repeat("'", 2);
			System.out.print(padText);
			benditalic = true;
		}

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		String text = new String(ch, start, length);
		isHeader = !(text.matches("[\\n\\t]+"));

		if (breport && isHeader) {
			System.out.println(text.trim() + " ");
			breport = false;
		}

		if (bsection && isHeader) {
			System.out.print(text.trim() + " ");
			bsection = false;
		}

		if (bbold) {
			String padText = repeat("'", 3);
			System.out.print(padText + text);
			bbold = false;
		}

		if (bitalic) {
			String padText = repeat("'", 2);
			System.out.print(padText + text);
			bitalic = false;
		}

		if (bendbold && isHeader) {
			System.out.println(" " + text.trim());
			bendbold = false;
			isHeader = false;
		}

		if (benditalic && isHeader) {
			System.out.print(" " + text.trim() + "\n");
			benditalic = false;
			isHeader = false;
		}
	}

	String repeat(String str, int times) {
		return new String(new char[times]).replace("\0", str);
	}

}