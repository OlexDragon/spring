package jk.web.workers;

import org.junit.Test;

public class FileWorkerTest {

	private FileWorker fileWorker = new FileWorker("c:/jk", "AIzaSyBNh-Q2UjdEuDUEiqYnxi0gp_GuZQ2t5E4", "500x200");

	@Test
	public void saveMapTest() {
		fileWorker.saveMap(null, "12-5535 Beaucourt", "Montreal", "QC", "Canada", "h3w2t7");
	}
}
