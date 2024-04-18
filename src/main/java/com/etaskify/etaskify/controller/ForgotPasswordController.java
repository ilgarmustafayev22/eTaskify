package com.etaskify.etaskify.controller;

import com.etaskify.etaskify.model.request.ChangePasswordRequest;
import com.etaskify.etaskify.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/forgot-password")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> sendOtp(@PathVariable String email){
       return ResponseEntity.ok(forgotPasswordService.sendOtp(email));
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        return ResponseEntity.ok(forgotPasswordService.verifyOtp(otp, email));
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePassword,
                                                   @PathVariable String email) {
        return ResponseEntity.ok(forgotPasswordService.changePassword(email, changePassword));
    }

}
