package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.Location;
import com.employmeo.data.model.Position;

import lombok.NonNull;

public interface AccountService {

	Set<Account> getAllAccounts();

	Account getAccountById(@NonNull Long accountId);
	
	Account getAccountByAtsId(@NonNull String atsId);

	Account save(@NonNull Account account);

	Position getPositionById(@NonNull Long positionId);
	
	Position getPositionByAtsId(@NonNull Long accountId, @NonNull String atsId);
	
	Location getLocationById(@NonNull Long locationId);
	
	Location getLocationByAtsId(@NonNull Long accountId, @NonNull String atsId);

	Position save(@NonNull Position position);

	Set<Position> getAllPositions();

}