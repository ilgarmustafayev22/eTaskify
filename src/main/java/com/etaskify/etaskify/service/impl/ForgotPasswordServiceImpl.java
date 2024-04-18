package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.dao.entity.ForgotPasswordEntity;
import com.etaskify.etaskify.dao.entity.UserEntity;
import com.etaskify.etaskify.dao.repositroy.ForgotPasswordRepository;
import com.etaskify.etaskify.dao.repositroy.UserRepository;
import com.etaskify.etaskify.exception.OtpNotFoundException;
import com.etaskify.etaskify.model.dto.MailDto;
import com.etaskify.etaskify.model.request.ChangePasswordRequest;
import com.etaskify.etaskify.service.AuthenticationService;
import com.etaskify.etaskify.service.ForgotPasswordService;
import com.etaskify.etaskify.service.MailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private static final String GENERATED_LINK = "http://localhost:8080/etaskify/swagger-ui/index.html#/forgot-password-controller/verifyOtp";

    private final MailService mailService;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final ForgotPasswordRepository forgotPasswordRepository;

    @Override
    @Transactional
    public String sendOtp(String email) {
        UserEntity user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email!"));

        int otp = otpGenerator();

        String text = String.format("""
                        Email Verification📧
                        " +
                        Dear User, %s🥰,
                        " +
                        This is the OTP for your Forgot Password request: %s.\s
                        Please use this OTP or visit the following link to reset your password: %s""",
                user.getName(), otp, GENERATED_LINK);

        MailDto mailBody = MailDto.builder()
                .to(email)
                .text(text)
                .subject("OTP for Forgot Password Request")
                .build();

        ForgotPasswordEntity fp = ForgotPasswordEntity.builder()
                .otp(otp)
                .expirationDate(new Date(System.currentTimeMillis() + 180 * 1000))
                .user(UserEntity.builder().id(user.getId()).build())
                .build();

        mailService.sendMail(mailBody);
        forgotPasswordRepository.save(fp);

        return "Email sent for verification!";
    }

    @Override
    public String verifyOtp(Integer otp, String email) {
        UserEntity user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email!"));

        ForgotPasswordEntity fp = forgotPasswordRepository
                .findByOtpAndUser(otp, user)
                .orElseThrow(() -> new OtpNotFoundException("Invalid OTP for email : " + email));

        if (fp.getExpirationDate().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getId());
            return "OTP has expired!";
        }
        return "OTP verified!";
    }

    @Override
    public String changePassword(String email, ChangePasswordRequest changePassword) {
        assert Objects.equals(changePassword.getPassword(), changePassword.getConfirmPassword())
                : "Please enter the password again!";

        authenticationService.changePassword(email, changePassword.getPassword());
        return "Password has been changed successfully!";
    }

    private Integer otpGenerator() {
        return new Random().nextInt(100_000, 999_999);
    }

    @Scheduled(fixedDelay = 300000)
    private void deleteAll() {
        forgotPasswordRepository.deleteAll();
    }

}
