package com.employmeo.data.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.Position;
import com.employmeo.data.repository.AccountRepository;
import com.employmeo.data.repository.PositionRepository;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private PositionRepository positionRepository;


	@Override
	public Set<Account> getAllAccounts() {
		Set<Account> accounts = Sets.newHashSet(accountRepository.findAll());
		log.debug("Retrieved all {} accounts", accounts);

		return accounts;
	}

	@Override
	public Account save(@NonNull Account account) {
		Account savedAccount = accountRepository.save(account);
		log.debug("Saved account {}", savedAccount);

		return savedAccount;
	}

	@Override
	public Account getAccountById(@NonNull Long accountId) {
		Account account = accountRepository.findOne(accountId);
		log.debug("Retrieved for id {} entity {}", accountId, account);

		return account;
	}

	@Override
	public Set<Position> getAllPositions() {
		Set<Position> positions = Sets.newHashSet(positionRepository.findAll());
		log.debug("Retrieved all {} positions", positions);

		return positions;
	}

	@Override
	public Position save(@NonNull Position position) {
		Position savedPosition = positionRepository.save(position);
		log.debug("Saved position {}", position);

		return savedPosition;
	}

	@Override
	public Position getPositionById(@NonNull Long positionId) {
		Position position = positionRepository.findOne(positionId);
		log.debug("Retrieved for id {} entity {}", positionId, position);

		return position;
	}


}
