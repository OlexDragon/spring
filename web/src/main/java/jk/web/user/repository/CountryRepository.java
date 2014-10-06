package jk.web.user.repository;

import jk.web.user.entities.CountryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {

	CountryEntity findByCountryName(String country);
}
