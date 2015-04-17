package jk.web.filters;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jk.web.entities.BlacklistEntity;
import jk.web.entities.BlacklistEntity.BlackListType;
import jk.web.entities.statistic.IpAddressEntity;
import jk.web.entities.statistic.RequestUrlEntity;
import jk.web.entities.statistic.StatisticEntity;
import jk.web.entities.statistic.StatisticRequestUrlEntity;
import jk.web.entities.statistic.StatisticRequestUrlEntityPK;
import jk.web.entities.statistic.UserAgentEntity;
import jk.web.entities.statistic.VersionEntity;
import jk.web.repositories.statictic.BlacklistRepository;
import jk.web.repositories.statictic.IpAddressRepository;
import jk.web.repositories.statictic.RequestUrlRepository;
import jk.web.repositories.statictic.StatisticRepository;
import jk.web.repositories.statictic.StatisticRequestUrlsRepository;
import jk.web.repositories.statictic.UserAgendRepository;
import jk.web.repositories.statictic.VersionRepository;
import jk.web.user.LoginDetails;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.OncePerRequestFilter;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

public class Statistic extends OncePerRequestFilter {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private StatisticRepository statisticRepository;
	@Autowired
	private UserAgendRepository userAgendRepository;
	@Autowired
	private VersionRepository versionRepository;
	@Autowired
	private IpAddressRepository ipAddressRepository;
	@Autowired
	private RequestUrlRepository requestUrlRepository;
	@Autowired
	private StatisticRequestUrlsRepository statisticRequestUrlsRepository;
	@Autowired
	private BlacklistRepository blacklistRepository;

	private List<BlacklistEntity> blacklistEntities;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		if(blacklistEntities==null)
			blacklistEntities = blacklistRepository.findByBlacklistType(BlackListType.CONTAINS);

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null)
			ipAddress = request.getRemoteAddr();

		UserAgent ua = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
		Browser browser = ua.getBrowser();
		Version browserVersion = ua.getBrowserVersion();
		OperatingSystem operatingSystem = ua.getOperatingSystem();

		String requestURI = request.getRequestURI();
		new DoStatistic(getUserId(request), ipAddress, requestURI, browser, browserVersion, operatingSystem);
		if(!contains(requestURI, blacklistEntities))
			filterChain.doFilter(request, response);
		else
			logger.info("\n\t{} has been blocked.\n\tIt is tried to access to {}", ipAddress, requestURI);
	}

	private boolean contains(String requestURI, List<BlacklistEntity> blacklistEntities) {
		boolean result = false;

		for(BlacklistEntity ble:blacklistEntities)
			if(requestURI.contains(ble.getBlacklistEntityPK().getBlacklistValue())){
				result = true;
				break;
			}

			return result;
	}

	private Long getUserId(HttpServletRequest request) {
		Long id;

		HttpSession session = request.getSession(false);
		if (session != null) {
			SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");

			if (sci != null) {
				LoginDetails lds = (LoginDetails) sci.getAuthentication().getPrincipal();
				id = lds.getId();
			} else
				id = null;
		}else
			id = null;

		return id;
	}

	public static Date getDate() {
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return logger.exit(cal.getTime());
	}

	private class DoStatistic extends Thread{

		private final Calendar ACCESS_TIME = Calendar.getInstance();
		private final Long userId;
		private final String ipAddress, requestUrl;
		private final Browser browser;
		private final Version browserVersion;
		private final OperatingSystem operatingSystem;

		public DoStatistic(Long userId, String ipAddress, String requestUrl, Browser browser, Version browserVersion, OperatingSystem operatingSystem) {

				this.userId = userId;
				this.ipAddress = ipAddress;
				this.requestUrl = requestUrl;
				this.browser = browser;
				this.browserVersion = browserVersion;
				this.operatingSystem = operatingSystem;
				setPriority(MIN_PRIORITY);
				start();
		}

		@Override
		public void run() {
			logger.trace("\n\tuserId:{}"
			   			+ "\n\tipAddress: {}"
			   			+ "\n\trequestUrl: {}"
				   		+ "\n\tbrowser: {}"
				   		+ "\n\tbrowserVersion: {}"
				   		+ "\n\toperatingSystem: {}",
				   			userId,
				   			ipAddress,
				   			requestUrl,
				   			browser,
				   			browserVersion,
				   			operatingSystem);

			try{
				synchronized (logger) {
				UserAgentEntity userAgent = gerUserAgent(getVersion());
				IpAddressEntity ipAddressEntity = getIpAddressEntity();
				StatisticEntity statisticEntity = getStatistic(userAgent, ipAddressEntity);

				RequestUrlEntity requestUrlEntity = getRequestUrlEntity();
				StatisticRequestUrlEntityPK id = new StatisticRequestUrlEntityPK(statisticEntity.getStatisticId(), requestUrlEntity.getRequestUrlId());
				StatisticRequestUrlEntity statisticRequestUrlEntity = statisticRequestUrlsRepository.findOne(id);
				if(statisticRequestUrlEntity!=null){
					statisticRequestUrlEntity.setTimes(statisticRequestUrlEntity.getTimes()+1);
					statisticRequestUrlEntity.setAccessTime(new Timestamp(ACCESS_TIME.getTime().getTime()));
					statisticRequestUrlsRepository.save(statisticRequestUrlEntity);
				}else
					statisticRequestUrlsRepository.save(new StatisticRequestUrlEntity(id));
				}
			}catch(Exception e){
				logger.catching(e);
			}
		}

		private StatisticEntity getStatistic(UserAgentEntity userAgent, IpAddressEntity ipAddressEntity) {
			logger.entry(userAgent, ipAddressEntity);
			Date date = getDate();
			StatisticEntity s = statisticRepository.findOneByLoginIdAndIpAddressAndUserAgentAndStatisticDate(userId, ipAddressEntity, userAgent, date);
			if(s == null)
				s = statisticRepository.save(new StatisticEntity(userId, ipAddressEntity, userAgent, date));
			return logger.exit(s);
		}

		private RequestUrlEntity getRequestUrlEntity() {
			RequestUrlEntity ru = requestUrlRepository.getOneByRequestUrl(requestUrl);
			if(ru==null)
				ru = requestUrlRepository.save(new RequestUrlEntity(requestUrl));
			return logger.exit(ru);
		}

		private IpAddressEntity getIpAddressEntity() {
			IpAddressEntity ip = ipAddressRepository.findOneByIpAddress(ipAddress);
			if(ip==null)
				ip = ipAddressRepository.save(new IpAddressEntity(ipAddress));
			return logger.exit(ip);
		}

		private UserAgentEntity gerUserAgent(VersionEntity browserVersion) {
			logger.entry(browserVersion);
			UserAgentEntity ua = userAgendRepository.findOneByBrowserAndBrowserVersionAndOperatingSystem(browser, browserVersion, operatingSystem);
			if(ua == null)
				ua = userAgendRepository.save(new UserAgentEntity(browser, browserVersion, operatingSystem));
			return logger.exit(ua);
		}

		private VersionEntity getVersion() {
			VersionEntity v = null;
			if(browserVersion!=null){
				String version = browserVersion.getVersion();
				String majorVersion = browserVersion.getMajorVersion();
				String minorVersion = browserVersion.getMinorVersion();
				logger.trace("\n\tversion: {}"
					+ "\n\tmajorVersion: {},"
					+ "\n\tminorVersion: {}",
							version,
							majorVersion,
							minorVersion);

				v = versionRepository.findOneByVersionAndMajorVersionAndMinorVersion(version, majorVersion, minorVersion);
				if(v==null)
					v =versionRepository.save(new VersionEntity(version, majorVersion, minorVersion));
			}

			return logger.exit(v);
		}
	}
}
