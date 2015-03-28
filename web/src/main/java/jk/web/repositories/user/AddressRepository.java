package jk.web.repositories.user;

import java.util.List;

import jk.web.entities.user.AddressEntity;
import jk.web.user.Address.AddressType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	List<AddressEntity> findByUserId(Long userId);

	List<AddressEntity> findByUserIdAndType(Long userId, AddressType addressType);
}
