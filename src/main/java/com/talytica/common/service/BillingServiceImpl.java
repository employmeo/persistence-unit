package com.talytica.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;

import jersey.repackaged.com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Slf4j
@Service
public class BillingServiceImpl implements BillingService {

	@Value("${com.talytica.apis.stripe.secret.key:null}")
	private String SECRET_KEY;

	@Value("${com.talytica.apis.stripe.public.key:null}")
	private String PUBLIC_KEY;

	@Value("${com.talytica.apis.stripe.dashboard:https://dashboard.stripe.com/}")
	private String DASHBOARD;
	
	private List<String> activeSubscriptionStatuses;
	
	@PostConstruct
	private void logConfiguration() {
		activeSubscriptionStatuses= Lists.newArrayList("trialing", "active", "past_due");
		if (("null".equals(SECRET_KEY)) || ("null".equals(PUBLIC_KEY))) {
			log.warn("--- STRIPE API SERVICE UNAVAILABLE - KEYS NOT CONFIGURED ---");
		} else {
			Stripe.apiKey = SECRET_KEY;
		}
	}
	
	@Override
	public List<String> getActiveSubscriptionStatuses(){
		return activeSubscriptionStatuses;
	}

	@Override
	public String getStripePK(){
		return PUBLIC_KEY;
	}
	
	@Override
	@Cacheable("allplans")
	public List<Plan> getAllPlans() throws StripeException {
		Map<String, Object> listParams = new HashMap<String, Object>();
		//listParams.put("count", 1);
		List<Plan> plans = Plan.list(listParams).getData();
		for (Plan plan : plans) addLinkTo(plan);
		return plans;
	}
	
	@Scheduled(fixedRate = 300000)
	@Override
	@CacheEvict("allplans")
	public void clearCache() {};
	
	@Override
	public List<Customer> getAllCustomers() throws StripeException {
		Map<String, Object> listParams = new HashMap<String, Object>();
		//listParams.put("count", 1);
		CustomerCollection customers = Customer.list(listParams);
		return customers.getData();
	}
	
	@Override
	public Customer getCustomer(String id) throws StripeException {
		Customer customer = addLinkTo(Customer.retrieve(id));
		return customer;
	}

	@Override
	public List<Plan> getCustomerPlans(String id) throws StripeException {
		Customer customer = Customer.retrieve(id);
		List<Subscription> subs = customer.getSubscriptions().getData();
		List<Plan> plans = Lists.newArrayList();
		for (Subscription sub : subs) {
			if (activeSubscriptionStatuses.contains(sub.getStatus())) plans.add(sub.getPlan());
		}
		return plans;
	}
	
	@Override
	public Customer createCustomerFor(@NotNull Account account, @NotNull User user) throws StripeException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", user.getEmail());
		params.put("description", account.getAccountName());
		Customer customer = Customer.create(params);
		return addLinkTo(customer);
	}
	
	@Override
	public Customer createCustomerFor(@NotNull Account account) throws StripeException {
		User user = null;
		for (User anyUser : account.getUsers()) {user = anyUser; break; }
		if (user == null) return null;
		return createCustomerFor(account, user);
	}

	@Override
	public Subscription subscribeCustomerToPlan(Customer customer, String planId) throws StripeException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("plan", planId);
		params.put("trial_period_days", 10);
		Subscription subscription = customer.getSubscriptions().create(params);
		return subscription;
	}

	@Override
	public Card addCardToCustomer(String stripeToken, Account account) throws StripeException {
		Customer customer = getCustomer(account.getStripeId());
		Map<String, Object> creationParams = new HashMap<String, Object>();
		creationParams.put("source", stripeToken);        		
		Card card = customer.createCard(creationParams);
		return card;
	}
	
	@Override
	public Customer getStripeCustomer(Account account) throws StripeException {
		return getCustomer(account.getStripeId());
	}
	
	

	private Customer addLinkTo(Customer customer) {
		String link = null;
		if (customer.getLivemode()) {
			link = DASHBOARD + "customers/" + customer.getId();
		} else {
			link = DASHBOARD + "test/customers/" + customer.getId();
		}
		Map<String,String> meta = customer.getMetadata();
		meta.put("link",link);
		customer.setMetadata(meta);
		return customer;
	}
	
	private Plan addLinkTo(Plan plan) {
		String link = null;
		if (plan.getLivemode()) {
			link = DASHBOARD + "plans/" + plan.getId();
		} else {
			link = DASHBOARD + "test/plans/" + plan.getId();
		}
		Map<String,String> meta = plan.getMetadata();
		meta.put("link",link);
		plan.setMetadata(meta);
		return plan;
	}
	
}
