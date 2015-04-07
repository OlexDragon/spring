package jk.web.repositories.statictic;

import jk.web.entities.statistic.IpAddressEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IpAddressRepository extends JpaRepository<IpAddressEntity, Long> {

	IpAddressEntity findOneByIpAddress(String ipAddress);
}
