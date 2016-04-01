package com.Reader;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;

/**
 * @author Big_TANG
 *
 */
public class errorHint {
	private static errorHint single = null;

	public static errorHint getInstance() {
		if (single == null) {
			single = new errorHint();
		}
		return single;
	}

	Attribute attr = null;
	int total = 0;

	/**
	 * to hint errors in XML files
	 */
	public void hint(String filepath) {
		Locator locator = new LocatorImpl();
		DocumentFactory docFactory = new DocumentFactoryWithLocator(locator);
		SAXReader reader = new GokuSAXReader(docFactory, locator);
		try {
			File file = new File(filepath);
			Document doc = reader.read(file);
			Element node = doc.getRootElement();
			System.err.println();
			System.err.println("----------fileName : " + file.getAbsolutePath() + " ------------------------");
			listNodes(node);
			System.err.println(
					"--------------------------------------Total : " + total + "  errors---------------------------");
			total=0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * the main logical to judge XML files
	 * 
	 * @param node
	 */
	private void listNodes(Element node) {
		List<Attribute> list = node.attributes();
		String str = null;
		String[] strArray = null;
		String type = null;
		for (Attribute attr : list) {
			switch (attr.getName()) {
			case "loop": {
				str = attr.getValue();
				try {
					int loop = Integer.parseInt(str);
					if (loop < -1) {
						System.err.print("********Error : The number of loop is less than -1! ");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "******");

						total++;
					}
				} catch (Exception e) {
					System.err.print("********Error : The loop is not a number!");
					System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
							+ "Col-->" + ((GokuElement) node).getColumnNumber() + "******");
					total++;
				}
				break;
			}
			case "type": {
				str = attr.getValue();
				if (!str.equals("CreateSparseFile") && !str.equals("CreateFile") && !str.equals("CreateFileEx")
						&& !str.equals("CopyFile") && !str.equals("CopyFileEx") && !str.equals("DeleteFile")
						&& !str.equals("DeleteFileEx") && !str.equals("CreateLargeFile") && !str.equals("Exec")
						&& !str.equals("CreateLargeSeq") && !str.equals("CreateDir") && !str.equals("CreateDirEx")) {
					System.err.print("********Error : The String of type is illegal!");
					System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
							+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
					System.err.println("Choice : 1.CreateDir  2.CreateDirEx  3.CopyFile  4.CopyFileEx ");
					System.err.println("         5.CreateFile 6.CreateFileEx 7.CreateLargeFile  8.CreateLargeSeq ");
					System.err.println("         9.CreateSparseFile  10.DeleteFile  11.DeleteFileEx  12.Exec");
					total++;
				}
				break;
			}
			case "parms": {
				type = list.get(3).getValue();
				switch (type) {
				case "CreateDir":
				case "CreateDirEx": {
					strArray = attr.getValue().split(" ");
					if (strArray.length > 2) {
						System.err.print("********Error : The args of param is more than 2 when createDir!");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					try {
						int count = Integer.parseInt(strArray[1]);
						if (count <= 0) {
							System.err.print("********Error : The filesize is less than 0!");
							System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}
					} catch (Exception e) {
						System.err.print("********Error : The filesize is not a number !");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					break;
				}
				case "CreateFile":
				case "CreateFileEx": {
					strArray = attr.getValue().split(" ");
					if (strArray.length > 3) {
						System.err.print("********Error : The args of param is more than 3 when createFile !");
						System.err.println("	Error happens at Line-->" + ((GokuElement) node).getLineNumber()
								+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					try {
						long size = Long.parseLong(strArray[1]);
						if (size <= 0) {
							System.err.print("********Error : The fileSize is less than 0 !");
							System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}
						int count = Integer.parseInt(strArray[2]);
						if (count <= 0) {
							System.err.print("********Error : The fileCount is less than 0 !");
							System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}
					} catch (Exception e) {
						System.err.print("********Error : The param is not a number !");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					break;
				}
				case "CreateSparseFile": {
					strArray = attr.getValue().split(" ");
					if (strArray.length > 5) {
						System.err.print("********Error : The args of param is more than 5 when createSparseFile !");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					long size = 0;
					try {

						size = Long.parseLong(strArray[1]);
						if (size <= 0) {
							System.err.print("********Error : The fileSize is less than 0 !");
							System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}
					} catch (Exception e) {
						System.err.print("********Error : The fileSize is not a number !");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					try {
						long hSize = Long.parseLong(strArray[2]);
						if (hSize <= 0) {
							System.err.print("********Error : The holeSize is less than 0!");
							System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}
						if (hSize >= size) {
							System.err.print("********Error : The holeSize cannot be larger than fileSize!");
							System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}

					} catch (Exception e) {
						System.err.print("********The fileSize is not a number !");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}

					String pos = strArray[3];
					if (!pos.equals("begin") && !pos.equals("middle") && !pos.equals("end") && !pos.equals("random")) {
						System.err.print("********Error : The position of hole is illegal !");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						System.err.println("Choice : 1.begin  2.middle  3.end  4.random");

						total++;
					}
					try {
						int count = Integer.parseInt(strArray[4]);
						if (count <= 0) {
							System.err.print("********Error : The fileCount is less than 0 !");
							System.err.println("	Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}

					} catch (Exception e) {
						System.err.println("********Error : The fileCount is not a number !");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					break;
				}
				case "CreateLargeFile": {
					strArray = attr.getValue().split(" ");
					if (strArray.length > 5) {
						System.err.print("********Error : The args of param is more than 5 when createLargeFile !");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					long size = 0l;
					try {
						size = Long.parseLong(strArray[1]);
						if (size <= 0) {
							System.err.print("********Error : The fileSize is less than 0 !");
							System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}
					} catch (Exception e) {
						System.err.print("********Error : The fileSize is not a number !");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					try {
						long hSize = Long.parseLong(strArray[2]);
						if (hSize <= 0) {
							System.err.print("********Error : The holeSize is less than 0 !");
							System.err.println("	Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}
						if (hSize >= size) {
							System.err.print("********Error : The holeSize cannot be larger than fileSize!");
							System.err.println("	Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}
					} catch (Exception e) {
						System.err.print("********Error : The holeSize is not a number !");
						System.err.println("	Error happens at Line-->" + ((GokuElement) node).getLineNumber()
								+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;

					}

					String pos = strArray[3];
					if (!pos.equals("begin") && !pos.equals("middle") && !pos.equals("end") && !pos.equals("random")) {
						System.err.print("********The position of hole is illegal!");
						System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber() + "    "
								+ "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						System.err.println("Choice : 1.begin  2.middle  3.end  4.random");

						total++;
					}
					int count = Integer.parseInt(strArray[4]);
					try {
						if (count <= 0) {
							System.err.print("********The fileCount is less than 0 !");
							System.err.println("Error happens at Line-->" + ((GokuElement) node).getLineNumber()
									+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
							total++;
						}
					} catch (Exception e) {
						System.err.print("********Error : The fileCount is not a number !");
						System.err.println("	Error happens at Line-->" + ((GokuElement) node).getLineNumber()
								+ "    " + "Col-->" + ((GokuElement) node).getColumnNumber() + "********");
						total++;
					}
					break;
				}
				default:
					break;
				}
				break;
			}
			default:
				break;
			}
		}
		Iterator<Element> it = node.elementIterator();
		while (it.hasNext()) {
			Element e = it.next();
			listNodes(e);
		}
	}

}
