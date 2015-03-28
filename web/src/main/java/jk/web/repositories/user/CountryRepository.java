package jk.web.repositories.user;

import jk.web.entities.user.CountryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {

	CountryEntity findByCountryName(String country);
}
