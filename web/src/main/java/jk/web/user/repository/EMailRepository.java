package jk.web.user.repository;

import jk.web.user.entities.EMailEntity;

import org.springframework.data.repository.CrudRepository;

public interface EMailRepository extends CrudRepository<EMailEntity, Long> {

}
