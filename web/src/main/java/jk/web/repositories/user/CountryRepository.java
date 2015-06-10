package jk.web.repositories.user;

import jk.web.entities.CountryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
}
