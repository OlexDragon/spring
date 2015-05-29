package jk.web.entities.repositories;

import static org.junit.Assert.*;
import jk.web.TestHomeController;
import jk.web.entities.AddressEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestHomeController.class)
public class AddressRepositoryTest {

	public static final String ADDRESS = "Address";

	private final static Logger logger = LogManager.getLogger();

	@Autowired
	AddressRepository addressRepository;

	public static final AddressEntity addressEntity;
	static{
		addressEntity = new AddressEntity();
		addressEntity.setAddress(ADDRESS);
		addressEntity.setCity("city");
		addressEntity.setCountryCode("CAN");
		addressEntity.setPostalCode("postalCode");
		addressEntity.setRegionsCode("QC");
	}

	@Test
	public void test() {
		AddressEntity addressEntity = addressRepository.save(AddressRepositoryTest.addressEntity);
		logger.trace(addressEntity);

		addressEntity = addressRepository.findOne(addressEntity.getAddsressId());
		assertNotNull(addressEntity);
		assertEquals(ADDRESS, addressEntity.getAddress());
		logger.trace(addressEntity);
	}
}
