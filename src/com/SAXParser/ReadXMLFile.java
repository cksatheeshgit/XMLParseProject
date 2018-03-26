package com.SAXParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ReadXMLFile {

	static FileOutputStream fos;

	public static void main(String argv[]) throws IOException {

		String inputFolderText, outputFolderText;

		inputFolderText = argv[0] + "/";
		outputFolderText = argv[1] + "/";

		final File inputFolder = new File(inputFolderText);
		final File outputFolder = new File(outputFolderText);

		if (!(inputFolder.exists() && outputFolder.exists())) {
			System.out.println("Invalid Folders..");
			System.exit(0);

		}

		File[] filesList = inputFolder.listFiles();

		if (filesList.length > 0) {
			for (final File fileEntry : filesList) {

				String[] fileNameParts = fileEntry.getName().split("\\.");
				if (fileNameParts.length == 2) {
					String fileName = fileNameParts[0];
					String fileExtension = fileNameParts[1];

					if (!(fileEntry.isDirectory()) && fileExtension.equals("xml")) {
						writeWikiFile(outputFolderText, fileName);
						parseFile(inputFolderText + fileEntry.getName());
						fos.close();
					}

				}
			}

		}

	}

	private static void writeWikiFile(String outputFolderText, String fileName) throws IOException {

		String wikiFile = outputFolderText + fileName + ".wiki";

		File file = new File(wikiFile);

		fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);

	}

	public static void parseFile(String inputFile) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			XMLHandler handler = new XMLHandler();

			saxParser.parse(inputFile, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
