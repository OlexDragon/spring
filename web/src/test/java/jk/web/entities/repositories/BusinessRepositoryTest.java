package jk.web.entities.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import jk.web.TestHomeController;
import jk.web.entities.AddressEntity;
import jk.web.entities.BusinessEntity;
import jk.web.entities.ContactEmailEntity;
import jk.web.entities.TelephonEntity;
import jk.web.entities.UrlEntity;

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

		BusinessEntity be = new BusinessEntity();
		be.setCompanyName("Test Company Name 1");
		be.setVatNumber("VAT Number 1");

		List<ContactEmailEntity> contactEmailEntities = new ArrayList<>();
		contactEmailEntities.add(ContactEmailRepositoryTest.contactEmailEntity);
		be.setContactEmailEntityList(contactEmailEntities);

		List<UrlEntity> urlEntities = new ArrayList<>();
		urlEntities.add(UrlRepositoryTest.urlEntity);
		be.setUrlEntityList(urlEntities);

		List<TelephonEntity> telephonEntityList = new ArrayList<>();
		telephonEntityList.add(TelephonRepositoryTest.telephonEntity);
		be.setTelephonEntityList(telephonEntityList);

		List<AddressEntity> addressEntityList = new ArrayList<AddressEntity>();
		addressEntityList.add(AddressRepositoryTest.addressEntity);
		be.setAddressEntityList(addressEntityList);

		be = businessRepository.save(be);
		logger.trace("\n\tbusinessEntity:{}", be);

		//RESUNT
		be = businessRepository.findOne(be.getBusinessId());
		assertNotNull(be);

		//Address
		addressEntityList = be.getAddressEntityList();
		logger.trace("\n\taddressEntityList:{}", addressEntityList);
		assertNotNull(addressEntityList);
		assertTrue(addressEntityList.size()>0);
		AddressEntity addressEntity = addressEntityList.get(0);
		assertNotNull(addressEntity.getAddsressId());
		assertEquals(AddressRepositoryTest.ADDRESS, addressEntity.getAddress());

		//Telephon
		telephonEntityList = be.getTelephonEntityList();
		logger.trace("\n\ttelephonEntityList:{}", telephonEntityList);
		assertNotNull(telephonEntityList);
		assertTrue(telephonEntityList.size()>0);
		TelephonEntity telephonEntity = telephonEntityList.get(0);
		assertNotNull(telephonEntity.getTelephonId());
		assertEquals(TelephonRepositoryTest.TELEPHON, telephonEntity.getTelephon());

		//URL
		urlEntities = be.getUrlEntityList();
		logger.trace("\n\turlEntities:{}", urlEntities);
		assertNotNull(urlEntities);
		assertTrue(urlEntities.size()>0);
		UrlEntity urlEntity = urlEntities.get(0);
		assertNotNull(urlEntity.getUrlId());
		assertEquals(UrlRepositoryTest.URL, urlEntity.getUrl());

		//ContactEmail
		contactEmailEntities = be.getContactEmailEntityList();
		logger.trace("\n\tcontactEmailEntities:{}", contactEmailEntities);
		assertNotNull(contactEmailEntities);
		assertTrue(contactEmailEntities.size()>0);
		ContactEmailEntity emailEntity = contactEmailEntities.get(0);
		assertNotNull(emailEntity.getEmailId());
		assertEquals(ContactEmailRepositoryTest.EMAIL, emailEntity.getEmail());
	}

}
