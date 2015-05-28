package jk.web.repositories.user;

import jk.web.entities.user.AddressEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
