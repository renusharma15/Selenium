package com.myaccess.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.Reporter;

public class ExecutionLog {
	public static void log(String text) {
		Reporter.log("<html><body><b>||" + text
				+ "||<br></br></b></html></body>");
		ExecutionLog executionLog = new ExecutionLog();
		String dateTime = executionLog.getDate();
		String fileName = executionLog.getFileName();
		try {
			// Create file
			FileWriter fstream = new FileWriter(System.getProperty("user.dir")
					+ "\\ExecutionLog\\" + fileName + ".txt", true);

			BufferedWriter out = new BufferedWriter(fstream);
			text = dateTime + " [info]  " + text;
			out.write(text);
			out.newLine();

			// Close the output stream
			out.close();
		} catch (Exception e) {
			System.err.println("Error: ExecutionLog failed to write log for "
					+ text);
			e.printStackTrace();
		}

	}

	public static void log(String text, String exception) {
		Reporter.log("<html><body><b>||" + text
				+ "||<br></br></b></html></body>");
		ExecutionLog executionLog = new ExecutionLog();
		String dateTime = executionLog.getDate();
		String fileName = executionLog.getFileName();
		try {
			// Create file
			FileWriter fstream = new FileWriter(System.getProperty("user.dir")
					+ "\\ExecutionLog\\" + fileName + ".txt", true);

			BufferedWriter out = new BufferedWriter(fstream);
			text = dateTime + " [info]  " + text;
			out.write(text);
			out.newLine();
			out.write(exception);

			// Close the output stream
			out.close();
		} catch (Exception e) {
			System.err.println("Error: ExecutionLog failed to write log for "
					+ text);
			e.printStackTrace();
		}

	}

	public static void logExceptionMessage(Exception ex) {
		ExecutionLog executionLog = new ExecutionLog();
		String dateTime = executionLog.getDate();
		ExecutionLog
				.log(dateTime
						+ " [info]  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Error message >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String fileName = executionLog.getFileName();
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(System.getProperty("user.dir")
					+ "//ExecutionLog//" + fileName + ".txt", true));
			ex.printStackTrace(pw);
			pw.close();
		} catch (IOException e) {
			System.err
					.println("Error: ExecutionLog logExceptionMessage failed to write log");
			e.printStackTrace();
		}
	}

	public static void logErrorMessage(Error ex) {
		ExecutionLog executionLog = new ExecutionLog();
		String dateTime = executionLog.getDate();
		ExecutionLog
				.log(dateTime
						+ " [info]  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Error message >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String fileName = executionLog.getFileName();
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(System.getProperty("user.dir")
					+ "//ExecutionLog//" + fileName + ".txt", true));
			ex.printStackTrace(pw);
			pw.close();
		} catch (IOException e) {
			System.err
					.println("Error: ExecutionLog logErrorMessage failed to write log");
			e.printStackTrace();
		}
	}

	private String getFileName() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String fileName = "Report-" + dateFormat.format(cal.getTime());
		return fileName;
	}

	private String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String dateTime = dateFormat.format(cal.getTime());
		return dateTime;
	}

	public static void logAddClass(String text) {
		ExecutionLog executionLog = new ExecutionLog();
		String dateTime = executionLog.getDate();
		String fileName = executionLog.getFileName();
		try {
			// Create file
			FileWriter fstream = new FileWriter(System.getProperty("user.dir")
					+ "//ExecutionLog//" + fileName + ".txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			text = dateTime + " [info]  " + " Execution Started of Test Class "
					+ text;
			out.newLine();
			out.write("*****************************************************************************");
			out.newLine();
			out.write(text);
			out.newLine();
			out.write("*****************************************************************************");
			out.newLine();
			// Close the output stream
			out.close();
		} catch (Exception e) {
			System.err
					.println("Error: ExecutionLog logAddClass failed to write log"
							+ text);
			e.printStackTrace();
		}
	}

	public static void logEndClass(String text) {
		ExecutionLog executionLog = new ExecutionLog();
		String dateTime = executionLog.getDate();
		String fileName = executionLog.getFileName();
		try {
			// Create file
			FileWriter fstream = new FileWriter(System.getProperty("user.dir")
					+ "//ExecutionLog//" + fileName + ".txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			text = dateTime + " [info]  End Execution of Test Script " + text;
			out.write(text);
			out.newLine();
			out.write("*****************************************************************************");
			out.newLine();
			// Close the output stream
			out.close();
		} catch (Exception e) {
			System.err
					.println("Error: ExecutionLog logEndClass failed to write log"
							+ text);
			e.printStackTrace();
		}
	}

}
