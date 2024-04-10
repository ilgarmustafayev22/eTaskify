package com.etaskify.etaskify.service;

import com.etaskify.etaskify.model.dto.UserDto;

public interface UserService {

    long createUser(UserDto user);

    boolean existsByEmail(String email);

    UserDto findById(long id);

    void deleteUser(long id);

}
