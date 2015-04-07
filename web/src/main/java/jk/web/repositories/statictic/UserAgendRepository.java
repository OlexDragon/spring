package jk.web.repositories.statictic;

import jk.web.entities.statistic.UserAgentEntity;
import jk.web.entities.statistic.VersionEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;

public interface UserAgendRepository extends JpaRepository<UserAgentEntity, Long> {

	public UserAgentEntity findOneByBrowserAndBrowserVersionAndOperatingSystem(Browser browser, VersionEntity browserVersion, OperatingSystem operatingSystem);
}
