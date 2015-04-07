package jk.web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jk.web.user.LoginDetails;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.OncePerRequestFilter;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

public class Statistic extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		   if (ipAddress == null)
			   ipAddress = request.getRemoteAddr();

		   UserAgent ua = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
		   Browser browser = ua.getBrowser();
		   Version browserVersion = ua.getBrowserVersion();
		   OperatingSystem operatingSystem = ua.getOperatingSystem();

		   new DoStatistic(getUserId(request), ipAddress, request.getRequestURI(), browser, browserVersion, operatingSystem);
		   filterChain.doFilter(request, response);
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

	private class DoStatistic{

		private final Logger logger = LogManager.getLogger();

		public DoStatistic(Long userId, String ipAddress, String requestUrl, Browser browser, Version browserVersion, OperatingSystem operatingSystem) {
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
		}
		
	}
}
