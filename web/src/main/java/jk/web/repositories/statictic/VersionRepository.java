package jk.web.repositories.statictic;

import jk.web.entities.statistic.VersionEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<VersionEntity, Long> {

	public VersionEntity findOneByVersionAndMajorVersionAndMinorVersion(String version, String majorVersion, String minorVersion);
}
