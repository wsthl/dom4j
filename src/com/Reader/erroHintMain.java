package com.Reader;

public class erroHintMain {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Argument error: You must provide paths for input XML file and output errors file.");
			System.err.println("               ps:(the input XML path could be the filepath or directory with XML files)");
			System.err.println("Format: Scheduler [InputXmlFile] [ErrorPath] ");
			return;
		}
		String filePath = args[0];
		String erroPath = args[1];
		xmlReader test = new xmlReader(filePath, erroPath);
		test.reader();
	}

}
