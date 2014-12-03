package jk.web.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialConfig {

//	private final Logger logger = LogManager.getLogger();
//
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Autowired
//	private Environment env;
//	@Autowired
//	private DataSource dataSource;
//	@Autowired
//	private ConnectionRepository connectionRepository;
//	@Autowired
//	private ConnectController connectController;
//	@Autowired
//	private HiddenHttpMethodFilter hiddenHttpMethodFilter;
//
//	// @Bean
//	// public UsersConnectionRepository usersConnectionRepository(){
//	// logger.trace("\n\t{}\n\t{}", dataSource, connectionFactoryLocator);
//	// JdbcUsersConnectionRepository repository = new
//	// JdbcUsersConnectionRepository(
//	// dataSource, connectionFactoryLocator, Encryptors.noOpText());
//	// repository.setConnectionSignUp(new ConnectionSignUp() {
//	//
//	// @Override
//	// public String execute(Connection<?> connection) {
//	// // TODO Auto-generated method stub
//	// return null;
//	// }
//	// });
//	// return repository;
//	// }
//
//	@Bean
//	public SocialUserDetailsService socialUsersDetailService() {
//		logger.trace("\n\tenv = {}\n\t dataSource = {}\n\t connectionRepository = {}\n\t" + "connectController = {}\n\t hiddenHttpMethodFilter = {}", dataSource,
//				connectionRepository, connectController, hiddenHttpMethodFilter);
//		return new SimpleSocialUsersDetailService(userDetailsService);
//	}
//
//	@Bean
//	public TextEncryptor textEncryptor() {
//		logger.entry();
//		return Encryptors.noOpText();
//	}
//
//	@Bean
//	public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//		logger.entry(connectionFactoryLocator);
//		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor());
//	}
}
