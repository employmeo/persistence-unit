package com.employmeo.data.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.GraderConfig;
import com.employmeo.data.model.Location;
import com.employmeo.data.model.User;
import com.employmeo.data.repository.GraderConfigRepository;
import com.employmeo.data.repository.LocationRepository;
import com.employmeo.data.repository.UserRepository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	GraderConfigRepository graderConfigRepository;
	
	@Autowired
	LocationRepository locationRepository;

	@Override
	public Set<User> getAllUsers() {
		Set<User> users = Sets.newHashSet(userRepository.findAll());
		log.debug("Retrieved all {} users", users);

		return users;
	}

	@Override
	public User save(@NonNull User user) {
		User savedUser = userRepository.save(user);
		log.debug("Saved user {}", user);

		return savedUser;
	}

	@Override
	public User getUserById(@NonNull Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			log.debug("Retrieved for id {} entity {}", userId, user);
			return user.get();
		}
		return null;
	}
	
	@Override
	public User getUserByEmail(@NonNull String email) {
		User user = userRepository.findByEmailIgnoreCase(email);
		log.debug("Retrieved for email {} entity {}", email, user);

		return user;
	}

	@Override
	public Set<User> getUsersForAccount(Long accountId) {
		return userRepository.findAllByUserAccountId(accountId);
	}

	@Override
	public Set<GraderConfig> getGraderConfigs(Long userId) {
		return graderConfigRepository.findAllByUserId(userId);
	}	
	
	@Override
	public List<Long> getLocationLimits(User user) {
		List<Long> locationIds = Lists.newArrayList();
		Long keyId = user.getLocationRestrictionId();
		if (keyId != null) {
			locationIds.add(keyId);
			Location location = locationRepository.findById(keyId).get(); //blows up if Loc not there
			if ((location.getType() == Location.TYPE_HIDDEN_PARENT) || (location.getType() == Location.TYPE_VISIBLE_PARENT)) {
				List<Location> locations = getChildrenFor(location);
				for (Location loc : locations) locationIds.add(loc.getId());
			}
		}
		return locationIds;
	}	
	
	private List<Location> getChildrenFor(Location location) {
		List<Location> children = locationRepository.findAllByParentId(location.getId());
		List<Location> descendants = Lists.newArrayList();
		for (Location loc : children) {
			if ((location.getType() == Location.TYPE_HIDDEN_PARENT) || (location.getType() == Location.TYPE_VISIBLE_PARENT)) {
				descendants.addAll(getChildrenFor(loc));
			}
		}
		descendants.addAll(children);
		return descendants;
	}
}
