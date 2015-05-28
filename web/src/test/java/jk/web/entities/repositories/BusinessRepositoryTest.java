package jk.web.entities.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import jk.web.TestHomeController;
import jk.web.entities.BusinessEntity;
import jk.web.entities.user.AddressEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestHomeController.class)
public class BusinessRepositoryTest {

	private final static Logger logger = LogManager.getLogger();

	@Autowired
	BusinessRepository businessRepository;

	@Test
	public void test() {
		BusinessEntity businessEntity = new BusinessEntity();
		businessEntity.setCompanyName("Test Company Name 1");
		businessEntity.setVATnumber("VAT Number 1");
		List<AddressEntity> addressEntityList = new ArrayList<AddressEntity>();
		AddressEntity address = new AddressEntity();
		address.setAddress("Address Test");
		address.setCity("City Test");
		addressEntityList.add(address);
		businessEntity.setAddressEntityList(addressEntityList);
		BusinessEntity save = businessRepository.save(businessEntity);
		assertNotNull(save);
		long businessId = save.getBusinessId().longValue();
		assertNotNull(businessId);
		assertEquals(save.getCompanyName(), "Test Company Name 1");
		assertEquals(save.getVATnumber(), "VAT Number 1");
		addressEntityList = save.getAddressEntityList();
		assertNotNull(addressEntityList);
		assertTrue(addressEntityList.size()>0);
		AddressEntity addressEntity = addressEntityList.get(0);
//		assertNotNull(addressEntity.getAddsressId());
		assertEquals("Address Test", addressEntity.getAddress());
		logger.trace(save);

		businessEntity = new BusinessEntity();
		businessEntity.setCompanyName("Test Company Name 2");
		businessEntity.setVATnumber("VAT Number 2");
		save = businessRepository.save(businessEntity);
		assertNotNull(save);
		assertNotNull(save.getBusinessId());
		assertEquals(save.getCompanyName(), "Test Company Name 2");
		assertEquals(save.getVATnumber(), "VAT Number 2");
		logger.trace(save);

		businessRepository.flush();

		List<BusinessEntity> findAll = businessRepository.findAll();
		assertEquals(2, findAll.size());
		logger.trace("{}", findAll);

		logger.trace("businessId:\t{}", businessId);
		businessEntity = businessRepository.findOne(businessId);
		addressEntityList = businessEntity.getAddressEntityList();
		assertNotNull(addressEntityList);
		assertTrue(addressEntityList.size()>0);
		addressEntity = addressEntityList.get(0);
		assertNotNull(addressEntity.getAddsressId());
		assertEquals("Address Test", addressEntity.getAddress());
		logger.trace(businessEntity);
	}

}
