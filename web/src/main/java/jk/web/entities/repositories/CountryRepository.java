package jk.web.entities.repositories;

import jk.web.entities.CountryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
}
