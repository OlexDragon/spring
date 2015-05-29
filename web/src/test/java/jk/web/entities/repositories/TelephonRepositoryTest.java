package jk.web.entities.repositories;

import static org.junit.Assert.*;
import jk.web.TestHomeController;
import jk.web.entities.TelephonEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestHomeController.class)
public class TelephonRepositoryTest {

	public static final String TELEPHON = "5555555";

	private final static Logger logger = LogManager.getLogger();

	public static final TelephonEntity telephonEntity = new TelephonEntity();
	static{
		telephonEntity.setTelephon(TELEPHON);
	}

	@Autowired
	TelephonRepository telephonRepository;

	@Test
	public void test() {
		TelephonEntity te = telephonRepository.save(telephonEntity);
		logger.trace("\n\t{}", te);
		assertNotNull(te);
		assertEquals(TELEPHON, te.getTelephon());
	}

}
