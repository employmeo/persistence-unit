package com.employmeo.data.service;

import java.util.List;
import java.util.Set;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.Benchmark;
import com.employmeo.data.model.Location;
import com.employmeo.data.model.Position;
import com.employmeo.data.model.User;

import lombok.NonNull;

public interface AccountService {

	Set<Account> getAllAccounts();

	Account getAccountById(@NonNull Long accountId);
	
	Account getAccountByAtsId(@NonNull String atsId);
	
	Account getAccountByName(@NonNull String accountName);

	Account save(@NonNull Account account);


	Location getLocationById(@NonNull Long locationId);
	
	Location getLocationByAtsId(@NonNull Long accountId, @NonNull String atsId);

	Location save(@NonNull Location location);
	

	Position getPositionById(@NonNull Long positionId);
	
	Position getPositionByAtsId(@NonNull Long accountId, @NonNull String atsId);
	
	Position save(@NonNull Position position);
	
	Set<Position> getAllPositions();
	
	
	Set<User> getUsersForAccount(@NonNull Long accountId);
		
	
	Benchmark getBenchmarkById(@NonNull Long benchmarkId);
	
	List<Benchmark> getBenchmarksByPositionId(@NonNull Long positionId);
	
	List<Benchmark> getBenchmarksByAccountId(@NonNull Long accountId);
	
	List<Benchmark> getIncompleteBenchmarksByAccountId(@NonNull Long accountId);
	
	List<Benchmark> getAllBenchmarks();

	Benchmark save(@NonNull Benchmark benchmark);

}