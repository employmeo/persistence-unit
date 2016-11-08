package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.Position;

import lombok.NonNull;

public interface AccountService {

	Set<Account> getAllAccounts();

	Account getAccountById(@NonNull Long accountId);

	Account save(@NonNull Account account);

	Position getPositionById(@NonNull Long positionId);

	Position save(@NonNull Position position);

	Set<Position> getAllPositions();

}