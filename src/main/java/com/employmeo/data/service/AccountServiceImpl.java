package com.employmeo.data.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.Benchmark;
import com.employmeo.data.model.Location;
import com.employmeo.data.model.Position;
import com.employmeo.data.repository.AccountRepository;
import com.employmeo.data.repository.BenchmarkRepository;
import com.employmeo.data.repository.LocationRepository;
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
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private BenchmarkRepository benchmarkRepository;


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
	public Account getAccountByName(@NonNull String accountName) {
		Account account = accountRepository.findByAccountName(accountName);
		log.debug("Retrieved for id {} entity {}", accountName, account);
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

	@Override
	public Account getAccountByAtsId(String atsId) {
		Account account = accountRepository.findByAtsId(atsId);
		log.debug("Retrieved for ATS id {} entity {}", atsId, account);
		return account;
	}

	@Override
	public Position getPositionByAtsId(Long accountId, String atsId) {
		return positionRepository.findByAccountIdAndAtsId(accountId, atsId);
	}

	@Override
	public Location getLocationById(Long locationId) {
		return locationRepository.findOne(locationId);
	}

	@Override
	public Location getLocationByAtsId(Long accountId, String atsId) {
		return locationRepository.findByAccountIdAndAtsId(accountId, atsId);
	}

	@Override
	public Location save(Location location) {
		Location savedLocation = locationRepository.save(location);
		log.debug("Saved location {}", location);
		return savedLocation;
	}

	@Override
	public Benchmark save(@NonNull Benchmark benchmark) {
		Benchmark savedBenchmark = benchmarkRepository.save(benchmark);
		log.debug("Saved benchmark {}", savedBenchmark);

		return savedBenchmark;
	}

	@Override
	public List<Benchmark> getBenchmarksByPositionId(@NonNull Long positionId) {
		List<Benchmark> benchmarks = benchmarkRepository.findAllByPositionId(positionId);
		log.debug("Retrieved for id {} entity {}", positionId, benchmarks);
		return benchmarks;
	}

	@Override
	public List<Benchmark> getBenchmarksByAccountId(Long accountId) {
		List<Benchmark> benchmarks = benchmarkRepository.findAllByAccountId(accountId);
		log.debug("Retrieved for id {} entity {}", accountId, benchmarks);
		return benchmarks;
	}
	
	@Override
	public List<Benchmark> getIncompleteBenchmarksByAccountId(Long accountId) {
		List<Benchmark> benchmarks = benchmarkRepository.findAllByAccountIdAndStatusLessThan(accountId, Benchmark.STATUS_COMPLETED);
		log.debug("Retrieved for id {} entity {}", accountId, benchmarks);
		return benchmarks;
	}
	
	@Override
	public Benchmark getBenchmarkById(Long benchmarkId) {
		Benchmark benchmark = benchmarkRepository.findOne(benchmarkId);
		return benchmark;
	}


}
