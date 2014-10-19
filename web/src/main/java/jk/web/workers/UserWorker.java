package jk.web.workers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jk.web.user.User;
import jk.web.user.User.Gender;
import jk.web.user.entities.AddressEntity;
import jk.web.user.entities.CountryEntity;
import jk.web.user.entities.EMailEntity;
import jk.web.user.entities.EMailEntity.EMailStatus;
import jk.web.user.entities.LoginEntity;
import jk.web.user.entities.ProfessionalSkillEntity;
import jk.web.user.entities.RegionEntity;
import jk.web.user.entities.RegionEntityPK;
import jk.web.user.entities.SocialEntity;
import jk.web.user.entities.TitleEntity;
import jk.web.user.entities.UserEntity;
import jk.web.user.entities.WorkplaceEntity;
import jk.web.user.repository.EMailRepository;
import jk.web.user.repository.SocialRepository;
import jk.web.user.repository.TitleRepository;
import jk.web.user.repository.UserRepository;
import jk.web.workers.AddressWorker.AddressStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionKey;

public class UserWorker extends LoginWorker{

	private static final Logger logger = LogManager.getLogger();
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy,m,dd");

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private EMailRepository eMailRepository;
	@Autowired
	private AddressWorker addressWorker;
	@Autowired
	private SocialRepository socialRepository;
	@Autowired
	private FileWorker fileWorker;

	private UserEntity userEntity;
	private ConfirmationStaus eMailStatus;

	public enum ConfirmationStaus{
		NEW_USER,
		NEW_EMAIL,
		CONFIRMED_EMAIL
	}

	public UserEntity getUserEntity(String username){
		userEntity = userRepository.findByUsername(username);
		if(userEntity==null){
			LoginEntity loginEntity = getLoginEntity(username);
			if(loginEntity!=null){
				userEntity = new UserEntity(loginEntity.getId());
				userEntity.setLoginEntity(loginEntity);
			}
		}
		return userEntity;
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
		if(userEntity!=null)
			if(userEntity.getEmails()!=null)
				for(EMailEntity em:userEntity.getEmails()) {
					EMailStatus status = em.getStatus();
					if(status==EMailStatus.ACTIVE)
						eMailEntity = em;
					else if(status==EMailStatus.TO_CONFIRM){
						eMailEntity = em;
						break;
					}
				}
		return eMailEntity;
	}

	public boolean existsEMail(String eMail) {
		return logger.exit(eMailRepository.exists(eMail));
	}

	public UserEntity createNewUser(User user) throws ParseException {
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
        Integer titleId = user.getTitleId();
        if(titleId!=null)
        	userEntity.setTitle(titleRepository.findOne(titleId));
 
        setWorkplace(user.getWorkplace());
        setProfessionalSkill(user.getProfessionalSkill());
        eMailStatus = ConfirmationStaus.NEW_USER;
        setEmail(userEntity, user.getEMail());

        userEntity = save();
        userEntity.setLoginEntity(loginEntity);
 
        logger.trace("\n\t{}", userEntity);

        user.setTitles(titleRepository.findAll());
        eMailStatus = null;
        return userEntity;
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

	public String getEMail(){
		String eMail = "";
		if(userEntity!=null){
			List<EMailEntity> emails = userEntity.getEmails();
			logger.trace("\n\t{}", emails);
			if(emails!=null)
				for(EMailEntity eme:emails)
					if(eme.getStatus()==null || eme.getStatus()==EMailStatus.ACTIVE || eme.getStatus()==EMailStatus.TO_CONFIRM){
						eMail = eme.getEMail();
						break;
					}
		}
		return eMail;
	}

	public EMailEntity getEMail(String eMail) {
		return eMailRepository.findByEMail(eMail);
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
		if(userEntity!=null){
			fillUser(user);
			fillUserAddress(user);
		}
	}

	public void fillUser(User user) {
		logger.entry(user);
		
		user.setUsername(getUsername());
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());
		user.setSex(userEntity.getGender());
		user.setEMail(getEMail());
		TitleEntity title = userEntity.getTitle();
		if(title!=null)
			user.setTitleId(title.getId());
		else
			user.setTitleId(null);

		Date birthday = userEntity.getBirthday();
		if(birthday!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(birthday);
			logger.trace("\n\tUserEntityBirthday{}", birthday);
			user.setBirthYear(calendar.get(Calendar.YEAR));
			user.setBirthMonth(calendar.get(Calendar.MONTH));
			user.setBirthDay(calendar.get(Calendar.DAY_OF_MONTH));
		}
		List<AddressEntity> addressEntities = userEntity.getAddressEntities();
		if(addressEntities!=null){
			for(AddressEntity ae:addressEntities)
				if(ae.getStatus()==null || ae.getStatus()==AddressStatus.ACTIVE){
					logger.trace("\n\t{}",ae);
					user.setAddress(ae.getAddress());
					user.setCity(ae.getCity());
					user.setRegion(ae.getRegionsCode());
					user.setCountry(ae.getCountryCode());
					user.setPostalCode(ae.getPostalCode());
					break;
				}
		}
		user.setMapPath(fileWorker.getMapPath(userEntity.getId()));

		user.setTitles(titleRepository.findAll());

		logger.trace("EXIT\n\t{}\n\t{}", user, userEntity);
	}

	public void fillUserAddress(User user){
		AddressEntity addressEntity = getAddressEntity();
		if(addressEntity!=null) {
			CountryEntity countryEntity = addressEntity.getCountryEntity();
			RegionEntity regionEntity = addressEntity.getRegionEntity();

			if(countryEntity!=null){
				user.setCountry(countryEntity.getCountryCode());
				user.setRegionName(countryEntity.getRegionName());
			}
			user.setAddress(addressEntity.getAddress());
			user.setCity(addressEntity.getCity());
			user.setPostalCode(addressEntity.getPostalCode());
			if(regionEntity!=null) {
				RegionEntityPK regionEntityPK = regionEntity.getRegionEntityPK();
				if(regionEntityPK!=null){
					user.setRegion(regionEntityPK.getRegionCode());
				}
			} else
				user.setRegion(null);
		}else
			user.setRegion(null);
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

	public UserEntity saveTitle(String username, Integer titleId) {
		setTitle(username, titleId);
		return save();
	}

	public void setTitle(String username, Integer titleId) {
		setUser(username);
		userEntity.setTitle(titleRepository.findOne(titleId));
	}

	public String getPassword(String username) {
		setUser(username);
		LoginEntity loginEntity = userEntity.getLoginEntity();
		return loginEntity!=null ? loginEntity.getPassword() : null;
	}

	public UserEntity saveFirstName(String username, String firstName) {
		setFirstName(username, firstName);
		logger.trace(userEntity);
		return save();
	}

	public void setFirstName(String username, String firstName) {
		logger.entry(username, firstName);
		setUser(username);
		userEntity.setFirstName(firstName);
	}

	public UserEntity saveLastName(String username, String lastName) {
		logger.entry(username, lastName);
		setLastName(username, lastName);
		logger.trace(userEntity);
		return save();
	}

	public void setLastName(String username, String lastName) {
		logger.entry(username, lastName);
		setUser(username);
		userEntity.setLastName(lastName);
	}

	public void saveEMail(EMailEntity eMailEntity) {
		eMailRepository.save(eMailEntity);
	}

	public UserEntity saveEMail(String username, String eMail) {
		setEMail(username, eMail);
		logger.trace(userEntity);
		return save();
	}

	public void setEMail(String username, String eMail) {
		logger.entry(username, eMail);
		setUser(username);
		setEmail(userEntity, eMail);
	}

	private void setEmail(UserEntity userEntity, String eMail) {
		logger.entry(userEntity, eMail);

		if(addEMail(userEntity, eMail)){
			List<EMailEntity> emails = userEntity.getEmails();
			logger.trace("{}", emails);

			Date time = Calendar.getInstance().getTime();
			for(EMailEntity eme:userEntity.getEmails()){
				boolean equals = eme.getEMail().equals(eMail);
				switch(eme.getStatus()){
				case ACTIVE:
					if(equals)
						eMailStatus = ConfirmationStaus.CONFIRMED_EMAIL;
					else
						eme.setStatus(EMailStatus.NOT_ACTIVE).setUpdateDate(time);
					break;
				case TO_CONFIRM:
					if(eMailStatus!=ConfirmationStaus.NEW_USER)
						if(equals)
							eMailStatus = ConfirmationStaus.NEW_EMAIL;
						else
							eme.setStatus(EMailStatus.NOT_CONFIRMED).setUpdateDate(time);
					break;
				case NOT_ACTIVE:
					if(equals){
						eme.setStatus(EMailStatus.ACTIVE).setUpdateDate(time);
						eMailStatus = ConfirmationStaus.CONFIRMED_EMAIL;
					}
					break;
				case NOT_CONFIRMED:
					if(equals){
						eme.setStatus(EMailStatus.TO_CONFIRM).setUpdateDate(time);
						eMailStatus = ConfirmationStaus.NEW_EMAIL;
					}
					break;
				}
			}
		}
	}

	public boolean addEMail(UserEntity userEntity, String eMail) {
		logger.entry(userEntity, eMail);

		boolean added;
		EMailEntity eMailEntity = new EMailEntity().setIdUsers(userEntity.getId()).setEMail(eMail);

		List<EMailEntity> emails = userEntity.getEmails();
		if(emails==null){
			emails = new ArrayList<>();
			userEntity.setEmail(emails);
			added = emails.add(eMailEntity);
		}else if(!emails.contains(new EMailEntity().setEMail(eMail))){
			logger.trace("\n\tAdd e-mail: {}", eMail);
			added = emails.add(eMailEntity);
		}else
			added = false;

		return logger.exit(added);
	}

	public UserEntity saveGender(String username, Gender gender) {
		setGender(username, gender);		
		logger.trace(userEntity);
		return save();
	}

	public void setGender(String username, Gender gender) {
		logger.entry(username, gender);
		setUser(username);
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

	public void setBirthday(String username, Integer year, Integer month, Integer day) throws ParseException {
		logger.entry(username, year, month, day);
		setUser(username);
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
				&& userEntity.getEmails()		!=null
				&& userEntity.getBirthday()		!=null
				&& userEntity.getGender()		!=null;
	}

	@Override
	public LoginEntity getLoginEntity(String username) {
		setUser(username);
		return userEntity.getLoginEntity();
	}

	public AddressEntity getAddressEntity(String username) {
		setUser(username);
		return getAddressEntity();
	}

	public AddressEntity getAddressEntity() {
		AddressEntity addressEntity = null;
		if(userEntity!=null) {
			List<AddressEntity> aes = userEntity.getAddressEntities();
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
		return socialRepository.findOne(connectionKey.toString());
	}

	public UserEntity getUserEntityByEMail(String eMail) {
		return userRepository.findByEMail(eMail);
	}

	public static Date parseBirthday(Integer year, Integer month, Integer day) throws ParseException {
		logger.entry(year, month, day);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return logger.exit(calendar.getTime());
	}

	public String getCountryCode() {
		AddressEntity addressEntity = getAddressEntity();
		return addressEntity!=null ? addressEntity.getCountryCode() : null;
	}
}
