package jk.web.workers;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.PropertyException;

import jk.web.entities.user.AddressEntity;
import jk.web.entities.user.BusinessEntity;
import jk.web.entities.user.CountryEntity;
import jk.web.entities.user.EMailEntity;
import jk.web.entities.user.EMailEntity.EMailStatus;
import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.ProfessionalSkillEntity;
import jk.web.entities.user.RegionEntity;
import jk.web.entities.user.RegionEntityPK;
import jk.web.entities.user.TitleEntity;
import jk.web.entities.user.UserEntity;
import jk.web.entities.user.WorkplaceEntity;
import jk.web.entities.user.social.SocialEntity;
import jk.web.repositories.user.BusinessRepository;
import jk.web.repositories.user.TitleRepository;
import jk.web.repositories.user.UserRepository;
import jk.web.user.Address;
import jk.web.user.Address.AddressStatus;
import jk.web.user.Address.AddressType;
import jk.web.user.User;
import jk.web.user.User.Gender;
import jk.web.user.social.SocialRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionKey;
import org.thymeleaf.context.Context;

public class UserWorker extends LoginWorker{

	private static final Logger logger = LogManager.getLogger();
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy,m,dd");

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BusinessRepository bisinessRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private AddressWorker addressWorker;
	@Autowired
	private SocialRepository socialRepository;
	@Autowired
	private FileWorker fileWorker;

	private UserEntity userEntity;

	public enum ConfirmationStaus{
		NEW_USER,
		NEW_EMAIL,
		CONFIRMED_EMAIL
	}

	public UserEntity getUserEntity(String username){
		logger.entry(username);
		if(userEntity==null || !userEntity.getLoginEntity().getUsername().equals(username)){
			userEntity = userRepository.findByUsername(username);
			if(userEntity==null){
				LoginEntity loginEntity = getLoginEntity(username);
				if(loginEntity!=null){
					userEntity = new UserEntity(loginEntity.getId());
					userEntity.setLoginEntity(loginEntity);
				}
			}
		}
		return userEntity;
	}

	@Override
	public LoginEntity save(LoginEntity loginEntity) {
		LoginEntity le = super.save(loginEntity);
		userEntity = new UserEntity(le.getId());
		userEntity.setLoginEntity(loginEntity);
		return le;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		logger.entry(userEntity);
		this.userEntity = userEntity;
	}

	@Override
	public String toString() {
		return "UserWorker [userEntity=" + userEntity + "]";
	}

	/**
	 * @return e-mail to confirm, if not return active e-mail
	 */
	public EMailEntity getEmailToConfirm() {

		EMailEntity eMailEntity = null;
		if(userEntity!=null){
			LoginEntity loginEntity = userEntity.getLoginEntity();
			List<EMailEntity> emails = loginEntity.getEmails();
			if(emails!=null)
				for(EMailEntity em:emails) {
					EMailStatus status = em.getStatus();
					if(status==EMailStatus.ACTIVE)
						eMailEntity = em;
					else if(status==EMailStatus.TO_CONFIRM){
						eMailEntity = em;
						break;
					}
				}
		}
		return eMailEntity;
	}

	public UserEntity createNewUser(User user, String mainURL, Locale locale, Context context) throws ParseException, NoSuchAlgorithmException {
		logger.entry(user, passwordEncoder);

        LoginEntity loginEntity = save( new LoginEntity(user.getUsername(), passwordEncoder.encode(user.getNewPassword())));

        logger.trace("\n\t{}", loginEntity);

        userEntity = new UserEntity(
        							loginEntity.getId(),
        							user.getFirstName(),
        							user.getLastName(),
        							new Timestamp(
        									SIMPLE_DATE_FORMAT.parse(
        											String.format(
        													"%s,%s,%s",
        													user.getBirthYear(),
        													user.getBirthMonth(),
        													user.getBirthDay())
        									).getTime()),
        							user.getSex());
        TitleEntity titleEntity = user.getTitle();
        userEntity.setTitleID(titleEntity != null ? titleEntity.getId() : null);
 
        setWorkplace(user.getWorkplace());
        setProfessionalSkill(user.getProfessionalSkill());
        eMailStatus = ConfirmationStaus.NEW_USER;
        setEmail(loginEntity, user.getEMail());

        userEntity = save();
        userEntity.setLoginEntity(loginEntity);
		eMailWorker.sendRegistrationMail(user, mainURL+"/confirm/"+userEntity.getId()+'/'+getEMailId()+'/'+loginEntity.getPassword().hashCode(), locale, context);

        logger.trace("\n\t{}", userEntity);

        eMailStatus = null;
        return userEntity;
	}

	@Override
	public LoginEntity saveEMail(String username, String eMail) {
		LoginEntity loginEntity = super.saveEMail(username, eMail);
		userEntity.setLoginEntity(loginEntity);
		return loginEntity;
	}

	public void saveEMail(String eMail){
		if(userEntity!=null) {
			LoginEntity loginEntity = userEntity.getLoginEntity();
			List<EMailEntity> emails = loginEntity.getEmails();
			EMailEntity ee = new EMailEntity().setEMail(eMail);
			if(emails==null){
				emails = new ArrayList<>();
				loginEntity.setEmails(emails);
			}
			if(emails.isEmpty() || !emails.contains(ee)){
				ee.setUserId(userEntity.getId());
				ee.setStatus(EMailStatus.TO_CONFIRM);
				saveEMail(ee);
			}else
				logger.trace("\n\tE-mail {} already exists.", eMail);
		}else
			throw new NullPointerException("The Field 'userEntity' is null");
	}

	public void setProfessionalSkill(String professionalSkill) {
		List<ProfessionalSkillEntity> professionalSkills = userEntity.getProfessionalSkills();
        if(professionalSkills == null){
        	ProfessionalSkillEntity pse = new ProfessionalSkillEntity(userEntity.getId(), professionalSkill);
        	professionalSkills = new ArrayList<ProfessionalSkillEntity>();
        	professionalSkills.add(pse);
        	userEntity.setProfessionalSkills(professionalSkills);
        }else{
        	//TODO
        }
	}

	public void setWorkplace(String workplace) {
        List<WorkplaceEntity> workpaces = userEntity.getWorkplaces();
        if(workpaces==null){
        	WorkplaceEntity wpe = new WorkplaceEntity(userEntity.getId(), workplace);
        	workpaces = new ArrayList<WorkplaceEntity>();
        	workpaces.add(wpe);
        	userEntity.setWorkplaces(workpaces);
        }else{
        	//TODO
        }
	}

	public UserWorker setUser(String username) {
		logger.trace("\n\t{}\n\t{}", username, userEntity);

		if(userEntity==null || userEntity.getLoginEntity()==null || userEntity.getLoginEntity().getUsername()==null || !userEntity.getLoginEntity().getUsername().equals(username)){
			setUserEntity(userRepository.findByUsername(username));
			logger.trace("\n\tuserEntity has been set from repository. userEntity={}", userEntity);
		}

		LoginEntity loginEntity;
		if(userEntity==null){
			 loginEntity = super.getLoginEntity(username);
			if(loginEntity!=null){
				logger.trace("\n\tCreated new userEntity");
				setUserEntity(new UserEntity(loginEntity.getId()).setLoginEntity(loginEntity));
			}
		}else{
			loginEntity = userEntity.getLoginEntity();
			if(loginEntity==null){
				userEntity.setLoginEntity(getLoginEntity(username));
				logger.trace("\n\tSet loginEntity");
			}
		}

		return this;
	}

	public String getFirstName(){
		return userEntity!=null ? userEntity.getFirstName() : "";
	}

	public String getLastName(){
		return userEntity!=null ? userEntity.getLastName() : "";
	}

	public Long getEMailId(){
		EMailEntity eMailEntity = getEMailEntity();
		return eMailEntity!=null ? eMailEntity.getId() : null;
	}

	public String getEMail(){
		EMailEntity eMailEntity = getEMailEntity();
		return eMailEntity!=null ? eMailEntity.getEMail() : null;
	}

	public EMailEntity getEMailEntity(){
		EMailEntity eMail = null;
		if(userEntity!=null){
			LoginEntity loginEntity = userEntity.getLoginEntity();
			List<EMailEntity> emails = loginEntity.getEmails();
			logger.trace("\n\t{}", emails);
			if(emails!=null)
				for(EMailEntity eme:emails)
					if(eme.getStatus()==null || eme.getStatus()==EMailStatus.ACTIVE || eme.getStatus()==EMailStatus.TO_CONFIRM){
						eMail = eme;
						break;
					}
		}
		return eMail;
	}

	public String getBirthday(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
		return userEntity!=null && userEntity.getBirthday()!=null ? simpleDateFormat.format(userEntity.getBirthday()) : "";
	}

	public Gender getGender(){
		return userEntity!=null ? userEntity.getGender() : null;
	}

	public User getSignUpForm(String username) {
		setUser(username);
		User signUpForm = new User();
		if(userEntity!=null)
			fillUser(signUpForm);
		return signUpForm;
	}

	public void setUser(String username, User user) {
		setUser(username);
		if(userEntity!=null)
			fillUser(user);
	}

	public void fillUserAddress(String username, Address address) {
		logger.trace("\n\t{}\n\t{}", username, address);
		setUser(username);
		fillUserAddress(address);
		logger.exit(address);
	}

	public void fillUserAddress(Address address){

		AddressEntity addressEntity = getAddressEntity(address.getAddressType());
		logger.trace("\n\t{}", addressEntity);

		if(addressEntity!=null) {

			CountryEntity countryEntity = addressEntity.getCountryEntity();

			if(countryEntity!=null){
				address.setCountryCode(countryEntity.getCountryCode());
				address.setRegionName(countryEntity.getRegionName());
			}
			address.setAddress(addressEntity.getAddress());
			address.setCity(addressEntity.getCity());
			address.setPostalCode(addressEntity.getPostalCode());

			RegionEntity regionEntity = addressEntity.getRegionEntity();
			if(regionEntity!=null) {
				RegionEntityPK regionEntityPK = regionEntity.getRegionEntityPK();
				if(regionEntityPK!=null){
					address.setRegionCode(regionEntityPK.getRegionCode());
				}
			} 
		}
		address.setMapPath(fileWorker.getMapFileUrl(address.getAddressType(), userEntity.getId()));
	}

	public void fillUser(User user) {
		
		user.setUsername(getUsername());
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());
		user.setSex(userEntity.getGender());
		user.setEMail(getEMail());
		TitleEntity titleEntity = userEntity.getTitleEntity();
		user.setTitle(titleEntity);

		Date birthday = userEntity.getBirthday();
		if(birthday!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(birthday);
			user.setBirthYear(calendar.get(Calendar.YEAR));
			user.setBirthMonth(calendar.get(Calendar.MONTH));
			user.setBirthDay(calendar.get(Calendar.DAY_OF_MONTH));
		}

		logger.trace("Filled userEntity:\n\t{}\n\t{}", user, userEntity);
	}

	private String getUsername() {
		return userEntity!=null && userEntity.getLoginEntity()!=null ? userEntity.getLoginEntity().getUsername() : null;
	}

	public LoginEntity saveNewUsername(String username) {
		LoginEntity loginEntity = userEntity.getLoginEntity();
		loginEntity.setUsername(username);
		loginEntity = super.saveNewUsername(loginEntity, getEMail());
		userEntity.setLoginEntity(loginEntity);
		return loginEntity;
	}

	public LoginEntity saveNewPassword(String newPassword) {
		LoginEntity loginEntity = userEntity.getLoginEntity();
		loginEntity = super.saveNewPassword(loginEntity, newPassword, getEMail());
		userEntity.setLoginEntity(loginEntity);
		return loginEntity;
	}

	public void setTitle(TitleEntity titleEntity) {
		userEntity.setTitleEntity(titleEntity);
		userEntity.setTitleID(titleEntity!=null ? titleEntity.getId() : null);
	}

	public String getPassword(String username) {
		setUser(username);
		LoginEntity loginEntity = userEntity.getLoginEntity();
		return loginEntity!=null ? loginEntity.getPassword() : null;
	}

	public void setFirstName(String firstName) {
		userEntity.setFirstName(firstName);
	}

	public void setLastName(String lastName) {
		userEntity.setLastName(lastName);
	}

	public void setGender(Gender gender) {
		userEntity.setGender(gender);
	}

	public UserEntity saveBirthday(String username,Integer year, Integer month, Integer day) throws ParseException {
		logger.entry(username, year, month, day);
		setBirthday(username, parseBirthday(year, month, day));
		return save();
	}

	public void setBirthday(String username, Date birthday) {
		logger.entry(username, birthday);
		setUser(username);
		userEntity.setBirthday(birthday);
	}

	public void setBirthday(Integer year, Integer month, Integer day) throws ParseException {
		userEntity.setBirthday(parseBirthday(year, month, day));
	}

	public UserEntity save() {
		logger.entry(eMailStatus, userEntity);

		LoginEntity loginEntity = userEntity.getLoginEntity();
		userEntity.setLoginEntity(null);
		setUserEntity(userRepository.save(userEntity));
		userEntity.setLoginEntity(loginEntity);

		if(eMailStatus!=null){
			switch(eMailStatus){
			case CONFIRMED_EMAIL:
				eMailWorker.sendEMail(	getEMail(),
						applicationContext.getMessage(	"UserController.email_had_been_changed",
														null,
														"email had been changed",
														locale),
						applicationContext.getMessage(	"UserController.email_had_been_changed_message",
														new String[]{getEMail()},
														"email had been changed",
														locale));
				break;
			case NEW_EMAIL:
				eMailWorker.sendEMail(	getEMail(),
						applicationContext.getMessage(	"UserController.email_confirmation",
														null,
														"email confirmation",
														locale),
						applicationContext.getMessage(	"UserController.email_confirmation_message",
														new String[]{getEMail()},
														"email confirmation",
														locale));
				break;
			case NEW_USER:
			}
			eMailStatus = null;
		}
		return userEntity;
	}

	public boolean isValid() {
		return	   userEntity					!=null
				&& userEntity.getId()			!=null
				&& userEntity.getFirstName()	!=null
				&& userEntity.getFirstName()	!=null
				&& userEntity.getBirthday()		!=null
				&& userEntity.getGender()		!=null;
	}

	@Override
	public LoginEntity getLoginEntity(String username) {
		setUser(username);
		return userEntity.getLoginEntity();
	}

	public AddressEntity getAddressEntity(String username, AddressType addressType) {
		setUser(username);
		return getAddressEntity(addressType);
	}

	public AddressEntity getAddressEntity(AddressType addressType) {
		AddressEntity addressEntity = null;
		if(userEntity!=null) {
			List<AddressEntity> aes = addressWorker.getAddressEntities(userEntity.getId(), addressType);
			if(aes!=null)
				for(AddressEntity a:aes){
					if(a.getStatus()==AddressStatus.ACTIVE){
						addressEntity = a;
						break;
					}
				}
		}
		return addressEntity;
	}

	public boolean saveAddress(AddressEntity addressEntity) {
		logger.entry(addressEntity);
		AddressEntity existsAE = AddressWorker.getFrom(userEntity.getAddressEntities(), addressEntity);

		boolean saved = false;
		if(isValid() && addressEntity!=null && existsAE==null){
			addressEntity.setUserId(userEntity.getId());
			addressWorker.save(addressEntity);
			saved = true;

			//Reset UserEntity
			String username = getUsername();
			setUserEntity(null);
			setUser(username);
		}
		return saved;
	}

	public SocialEntity getSocialEntity(ConnectionKey connectionKey) {
		logger.entry(connectionKey);
		return socialRepository.findOne(connectionKey.toString());
	}

	public UserEntity getUserEntityByEMail(String eMail) {
		logger.entry(eMail);
		return userRepository.findByEMail(eMail);
	}

	public static Date parseBirthday(Integer year, Integer month, Integer day) {
		logger.entry(year, month, day);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return logger.exit(calendar.getTime());
	}

	public String getCountryCode(AddressType addressType) {
		AddressEntity addressEntity = getAddressEntity(addressType);
		return addressEntity!=null ? addressEntity.getCountryCode() : null;
	}

	public void saveBusinessEntity(BusinessEntity businessEntity) throws PropertyException {
		logger.entry(businessEntity);
		if(userEntity!=null && businessEntity!=null){
			BusinessEntity be = userEntity.getBusinessEntity();
			if(be==null)
				userEntity.setBusinessEntity(bisinessRepository.save(businessEntity.setUserID(userEntity.getId())));
			else
				throw new PropertyException("businessEntity for this user olready exist");
		}else
			throw new NullPointerException("'userEntity' or 'businessEntity' equals null");
	}
}
