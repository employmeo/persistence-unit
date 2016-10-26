package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.User;

import lombok.NonNull;

public interface UserService {

	Set<User> getAllUsers();

	User save(User user);

	User getUserById(Long userId);

}