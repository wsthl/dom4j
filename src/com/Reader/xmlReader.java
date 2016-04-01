package com.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Big_TANG
 * 
 *         read files ,include directory,sub-directory,xml files,or other files
 *
 */
public class xmlReader {

	private String path;
	private String erroPath;

	Vector<String> ver = null;
	List<String> list = null;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public xmlReader(String path, String erroPath) {
		super();
		this.path = path;
		this.erroPath = erroPath;
		ver = new Vector<String>();
		list = new ArrayList<String>();
	}

	public void reader() {
		PrintStream printStream = null;
		PrintStream sysout = System.out;
		PrintStream syserr = System.err;
		File file = new File(this.erroPath);
		errorHint single=errorHint.getInstance();
		if (file.exists())
			file.delete();
		try {
			printStream = new PrintStream(new FileOutputStream(new File(this.erroPath), true));
			System.setOut(printStream);
			System.setErr(printStream);

			if (!judgeDir())
				single.hint(this.path);
			else {
				getFileList();
				for (String s : list) {
					single.hint(s);;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (printStream != null) {
				printStream.close();
			}
			// Reset the output to console
			System.setOut(sysout);
			System.setErr(syserr);

			if (!judgeDir())
				single.hint(this.path);
			else {
				for (String s : list) {
					single.hint(s);
				}
			}

			System.out.println("The Function is over");
			System.out.println("The error hints has been saved at  " + new File(erroPath).getAbsolutePath());
		}

	}

	/**
	 * judge if the path is a directory
	 * 
	 * @return boolean
	 */
	private boolean judgeDir() {
		File file = new File(this.path);
		if (file.isDirectory())
			return true;
		else
			return false;
	}

	/**
	 * get all the filename below the directory
	 * 
	 */
	private void getFileList() {
		ver.add(this.path);
		try {
			while (ver.size() > 0) {
				File[] files = new File(ver.get(0).toString()).listFiles();
				ver.remove(0);

				int len = files.length;
				for (int i = 0; i < len; i++) {
					String tmp = files[i].getAbsolutePath();
					if (files[i].isDirectory())
						ver.add(tmp);
					else {
						if (files[i].getName().endsWith(".xml"))
							list.add(tmp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getErroPath() {
		return erroPath;
	}

	public void setErroPath(String erroPath) {
		this.erroPath = erroPath;
	}

}
