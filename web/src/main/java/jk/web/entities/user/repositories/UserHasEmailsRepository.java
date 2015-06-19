package jk.web.entities.user.repositories;

import jk.web.entities.user.UserHasEmails;
import jk.web.entities.user.UserHasEmailsPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasEmailsRepository  extends JpaRepository<UserHasEmails, UserHasEmailsPK>  {
}
