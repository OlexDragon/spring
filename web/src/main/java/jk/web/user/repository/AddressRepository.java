package jk.web.user.repository;

import java.util.List;

import jk.web.user.entities.AddressEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	List<AddressEntity> findByUserId(Long userId);
}
