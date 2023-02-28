package edu.gatech.seclass.jobcompare6300;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class DefaultExceptionHandler implements UncaughtExceptionHandler {
	protected org.slf4j.Logger log;
	public DefaultExceptionHandler(org.slf4j.Logger log, boolean isDebugBuild) {
		this.log = log;
	}

	@Override
	public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
		final Writer stackTrace = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stackTrace);
		paramThrowable.printStackTrace(printWriter);
		log.error("uncaught exception in thread " + paramThread.getId() + " " + paramThread.getName() + ": " + stackTrace.toString());
	}
}
