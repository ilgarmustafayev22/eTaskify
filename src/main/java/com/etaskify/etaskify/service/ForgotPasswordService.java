package com.etaskify.etaskify.service;

import com.etaskify.etaskify.model.request.ChangePasswordRequest;

public interface ForgotPasswordService {

    String sendOtp( String email);
    String verifyOtp(Integer otp, String email);
    String changePassword(String email, ChangePasswordRequest changePassword);


}
