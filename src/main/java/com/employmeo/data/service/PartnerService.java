package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.Partner;

import lombok.NonNull;

public interface PartnerService {

	Set<Partner> getAllPartners();

	Partner getPartnerById(Long partnerId);

	Partner save(@NonNull Partner partner);

	Set<Partner> getPartnerSetByName(String partnerName);

}