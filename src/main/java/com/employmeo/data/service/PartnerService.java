package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.Partner;

import lombok.NonNull;

public interface PartnerService {

	Set<Partner> getAllPartners();

	Partner getPartnerById(Long partnerId);
	
	Partner getPartnerByLogin(String login);
	
	Set<Partner> getPartnerSetByName(String partnerName);

	Partner save(@NonNull Partner partner);
}