package lin.logger;

import org.junit.Test;

public class TestLogger {

	private MyLogger logger = new MyLogger();
	@Test
	public void test() {
		logger.debug(this.getClass(),"test logger");
		System.out.println(logger.getClass().getResource("/").toString());
		System.out.println(logger.getClass().getResource("").toString());
	}

}
