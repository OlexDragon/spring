/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.user.repository;

import jk.web.user.entities.SocialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alex
 */
public interface SocialRepository extends JpaRepository<SocialEntity, String>  {
    
}
