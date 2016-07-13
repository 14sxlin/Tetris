package lin.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MyLogger {
	
	public MyLogger() {
		PropertyConfigurator.configure(this.getClass().getResourceAsStream("/log4j.properties"));
	
	}
	
	private Logger getLogger(Class<?> myclass) {
		return Logger.getLogger(myclass);
	}
	
	public void debug(Class<?> myclass,String message) {
		getLogger(myclass).debug(message);
	}
	public void fatal(Class<?> myclass,String message) {
		getLogger(myclass).fatal(message);
	}
}
