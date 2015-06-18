package jk.web.entities.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import jk.web.TestHomeController;
import jk.web.entities.UrlEntity;
import jk.web.entities.business.BusinessUrlEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestHomeController.class)
public class UrlRepositoryTest {

	public static final String URL = "url";

	private final static Logger logger = LogManager.getLogger();

	public static final BusinessUrlEntity urlEntity;
	static{
		urlEntity = new BusinessUrlEntity();
		urlEntity.setUrl(URL);
	}

	@Autowired
	UrlRepository urlRepository;

	@Test
	public void test() {
		UrlEntity ue = urlRepository.save(urlEntity);
		logger.trace(ue);
		assertNotNull(ue);

		ue = urlRepository.findOne(ue.getUrlId());
		logger.trace(ue);
		assertNotNull(ue);
		assertEquals(URL, ue.getUrl());
	}

}
