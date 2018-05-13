package com.employmeo.data.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Partner;
import com.employmeo.data.repository.PartnerRepository;

import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	private PartnerRepository partnerRepository;

	@Override
	public Set<Partner> getAllPartners() {
		Set<Partner> partners = Sets.newHashSet(partnerRepository.findAll());
		log.debug("Retrieved all {} partners", partners);

		return partners;
	}

	@Override
	public Set<Partner> getPartnerSetByName(String partnerName) {
		Set<Partner> partners = Sets.newHashSet(partnerRepository.findAllByPartnerName(partnerName));
		log.debug("Retrieved all {} partners", partners);

		return partners;
	}
	@Override
	public Partner getPartnerById(@NonNull Long partnerId) {
		Optional<Partner> partner = partnerRepository.findById(partnerId);
		if(partner.isPresent()){
			log.debug("Retrieved for id {} entity {}", partnerId, partner);
			return partner.get();
		}

		return null;
	}

	@Override
	public Partner save(@NonNull Partner partner) {
		Partner savedPartner = partnerRepository.save(partner);
		log.debug("Saved partner {}", partner);

		return savedPartner;
	}

	@Override
	public Partner getPartnerByLogin(String login) {
		return partnerRepository.findByLogin(login);
	}
}
