package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.*;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;

public interface AccountService {

	Set<Account> getAllAccounts();

	Account getAccountById(@NonNull Long accountId);

	Account save(@NonNull Account account);

	Position getPositionById(@NonNull Long positionId);

	Position save(@NonNull Position position);

	Set<Position> getAllPositions();

}