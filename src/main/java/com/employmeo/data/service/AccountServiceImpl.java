package com.employmeo.data.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.Benchmark;
import com.employmeo.data.model.CustomWorkflow;
import com.employmeo.data.model.Location;
import com.employmeo.data.model.Partner;
import com.employmeo.data.model.Position;
import com.employmeo.data.model.User;
import com.employmeo.data.repository.AccountRepository;
import com.employmeo.data.repository.BenchmarkRepository;
import com.employmeo.data.repository.CustomWorkflowRepository;
import com.employmeo.data.repository.LocationRepository;
import com.employmeo.data.repository.PartnerRepository;
import com.employmeo.data.repository.PositionRepository;
import com.employmeo.data.repository.UserRepository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private BenchmarkRepository benchmarkRepository;
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private CustomWorkflowRepository customWorkflowRepository;


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
		Optional<Account> account = accountRepository.findById(accountId);
		if (account.isPresent()) {
			log.debug("Retrieved for id {} entity {}", accountId, account);
			return account.get();			
		}
		return null;
	}

	@Override
	public Account getAccountByName(@NonNull String accountName) {
		Account account = accountRepository.findByAccountName(accountName);
		log.debug("Retrieved for id {} entity {}", accountName, account);
		return account;
	}
	

	@Override
	public Set<User> getUsersForAccount(Long accountId) {
		return userRepository.findAllByUserAccountId(accountId);
	}

	
	
	@Override
	public Set<Position> getAllPositions() {
		Set<Position> positions = Sets.newHashSet(positionRepository.findAll());
		log.debug("Retrieved all {} positions", positions);

		return positions;
	}

	@Override
	public List<Position> getActivePositions(Long accountId) {
		List<Integer> statuses = Arrays.asList(Position.STATUS_ACTIVE);
		List<Position> positions = positionRepository.findAllByAccountIdAndStatusIn(accountId, statuses);
		log.debug("Retrieved {} active positions", positions.size());
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
		Optional<Position> position = positionRepository.findById(positionId);
		if (position.isPresent()) {
			log.debug("Retrieved for id {} entity {}", positionId, position);
			return position.get();
		}
		return null;
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
		Optional<Location> location = locationRepository.findById(locationId);
		if (location.isPresent()) {
			return location.get();
		}
		return null;
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
	public List<Benchmark> getAllBenchmarks() {
		return  Lists.newArrayList(benchmarkRepository.findAll());
	}
	
	@Override
	public Benchmark getBenchmarkById(Long benchmarkId) {
		Benchmark benchmark = benchmarkRepository.findById(benchmarkId).get();
		return benchmark;
	}

	@Override
	public void delete(Location location) {
		locationRepository.delete(location);		
	}

	@Override
	public Set<Location> getAllLocations() {
		return Sets.newHashSet(locationRepository.findAll());
	}

	@Override
	public void delete(Position position) {
		positionRepository.delete(position);		
	}

	@Override
	public Set<Account> getAccountsForPartner(String partnerName) {
		Set<Partner> partners = partnerRepository.findAllByPartnerName(partnerName);
		List<Long> ids = Lists.newArrayList();
		for (Partner partner : partners) ids.add(partner.getId());
		return accountRepository.findAllByAtsPartnerIdIn(ids);
	}

	@Override
	public Account getByPartnerId(Long partnerId) {
		return accountRepository.findOneByAtsPartnerId(partnerId);
	}

	@Override
	public List<Location> getVisibleLocations(Long accountId) {
		List<Integer> statuses = Arrays.asList(Location.STATUS_ACTIVE,Location.STATUS_UNVERIFIED);
		List<Integer> types = Arrays.asList(Location.TYPE_NORMAL,Location.TYPE_VISIBLE_PARENT);
		List<Location> locations = locationRepository.findAllByAccountIdAndStatusInAndTypeIn(accountId, statuses, types);
		log.debug("Retrieved {} active positions", locations.size());
		return locations;
	}

	@Override
	public List<Location> getAllActiveLocations(Long accountId) {
		List<Integer> statuses = Arrays.asList(Location.STATUS_ACTIVE,Location.STATUS_UNVERIFIED);
		List<Integer> types = Arrays.asList(Location.TYPE_NORMAL,Location.TYPE_VISIBLE_PARENT,Location.TYPE_HIDDEN_PARENT);
		List<Location> locations = locationRepository.findAllByAccountIdAndStatusInAndTypeIn(accountId, statuses, types);
		log.debug("Retrieved {} active positions", locations.size());
		return locations;
	}

	@Override
	public CustomWorkflow getCustomWorkflowById(Long workflowId) {
		return customWorkflowRepository.findById(workflowId).get();
	}

	@Override
	public Iterable<CustomWorkflow> getAllWorkflows() {
		return customWorkflowRepository.findAll();
	}

	@Override
	public List<CustomWorkflow> getWorkflowsForPosition(Long positionId) {
		return customWorkflowRepository.findByPositionId(positionId);
	}

	@Override
	public CustomWorkflow save(CustomWorkflow workflow) {
		return customWorkflowRepository.save(workflow);
	}

	@Override
	public void delete(CustomWorkflow workflow) {
		customWorkflowRepository.delete(workflow);
	}
}
