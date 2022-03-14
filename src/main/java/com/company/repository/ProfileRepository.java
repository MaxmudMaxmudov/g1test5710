package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByLoginAndPswd(String login, String pswd);

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByLogin(String login);


}
