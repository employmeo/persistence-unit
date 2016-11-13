package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.User;

import lombok.NonNull;

public interface UserService {

	Set<User> getAllUsers();

	User save(@NonNull User user);

	User getUserById(@NonNull Long userId);
	
	User getUserByEmail(@NonNull String email);

}