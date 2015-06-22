package jk.web.controllers.rest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import jk.web.HomeController;
import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.UserEntity;
import jk.web.entities.user.repositories.LoginRepository;
import jk.web.entities.user.repositories.UserRepository;
import jk.web.user.User.Gender;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HomeController.class)
@WebAppConfiguration
public class UserResrControllerTest {

	private MockMvc mockMvc;

	@Autowired
	LoginRepository loginRepository;
	@Autowired
	UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
	private FilterChainProxy  filterChainProxy;

	@Before
    public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
        mockMvc = webAppContextSetup(webApplicationContext)
        		.dispatchOptions(true)
        		.addFilter(filterChainProxy)
                .build();
    }

	@Test
	public void getUser() throws Exception {
		mockMvc.perform(get("/rest/user")
				.contentType("application/json;charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AddressRestTest.CONTENT_TYPE))
				.andExpect(jsonPath("$.firstName", is("Anonymous")));
	}

	@Test
	public void getTestUser() throws Exception {

		LoginEntity loginEntity = loginRepository.findOne(3L);

		mockMvc.perform(get("/rest/user")
				.param("username", loginEntity.getUsername())
				.param("password", "1234567")
				.contentType("application/json;charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AddressRestTest.CONTENT_TYPE))
				.andExpect(jsonPath("$.id", is(3)));
	}

	@Test
	public void updateUser() throws Exception {

		long id = 3L;
		LoginEntity loginEntity = loginRepository.findOne(id);
		UserEntity userEntity = userRepository.findOne(id);

		String newFirstName;
		String newLastName;
		Gender gender;
		if(userEntity==null || userEntity.getFirstName().equals("First Name 2")){
			newFirstName = "First Name 1";
			newLastName = "Last Name 1";
			gender = Gender.FEMALE;
		}else{
			newFirstName = "First Name 2";
			newLastName = "Last Name 2";
			gender = Gender.MALE;
		}

		mockMvc.perform(post("/rest/user")
				.contentType("application/json;charset=UTF-8")
				.param("username", loginEntity.getUsername())
				.param("password", "1234567")
				.param("firstName", newFirstName)
				.param("lastName", newLastName)
				.param("gender", gender.name()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AddressRestTest.CONTENT_TYPE))
				.andExpect(jsonPath("$.firstName", is(newFirstName)))
				.andExpect(jsonPath("$.lastName", is(newLastName)))
				.andExpect(jsonPath("$.gender", is(gender.name())));
	}
}
