package com.etaskify.etaskify.dao.repositroy;

import com.etaskify.etaskify.dao.entity.ForgotPasswordEntity;
import com.etaskify.etaskify.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity, Long> {

    Optional<ForgotPasswordEntity> findByOtpAndUser(Integer otp, UserEntity user);

}
