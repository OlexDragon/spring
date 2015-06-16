package jk.web.controllers.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.List;

import jk.web.HomeController;
import jk.web.entities.CountryEntity;
import jk.web.entities.RegionEntity;
import jk.web.entities.repositories.CountryRepository;
import jk.web.entities.repositories.RegionRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HomeController.class)
@WebAppConfiguration
public class AddressRestTest {

	public static final long GEONAME_ID = 6251999L;

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private CountryRepository countryRepository;

	private final MediaType contentType;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public AddressRestTest() {
        this.contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));
    }

	@Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        logger.exit(mockMvc);
    }

	@Test
	public void getCountry() throws Exception {
		CountryEntity countryEntity = countryRepository.findOne(GEONAME_ID);
		logger.trace(countryEntity);
		mockMvc.perform(get("/rest/country?geonamesId=" + GEONAME_ID))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.geonamesId", is(countryEntity.getGeonamesId().intValue())))
		.andExpect(jsonPath("$.capital", is(countryEntity.getCapital())))
		.andExpect(jsonPath("$.countryCode", is(countryEntity.getCountryCode())))
		.andExpect(jsonPath("$.countryName", is(countryEntity.getCountryName())))
		.andExpect(jsonPath("$.isoAlfa3", is(countryEntity.getIsoAlfa3())))
		.andExpect(jsonPath("$.postalCodeFormat", is(countryEntity.getPostalCodeFormat())));
	}

	@Test
	public void getCountries() throws Exception {
		mockMvc.perform(get("/rest/countries"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(250)));
	}

	@Autowired
	RegionRepository regionRepository;
	@Test
	public void getRegions() throws Exception{
		CountryEntity countryEntity = countryRepository.findOne(GEONAME_ID);
		List<RegionEntity> entities = regionRepository.findByCountryEntityGeonamesIdOrderByRegionNameAsc(GEONAME_ID);
		logger.trace(entities);

		mockMvc.perform(get("/rest/regions?geonamesId="+countryEntity.getGeonamesId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(entities.size())));
		
		mockMvc.perform(get("/rest/regions?countryCode="+countryEntity.getCountryCode()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(entities.size())));
		
	}
}
