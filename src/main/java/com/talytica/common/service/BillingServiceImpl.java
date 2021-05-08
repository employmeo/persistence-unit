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

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
	public Customer getCustomer(String id) throws StripeException {
		Map<String, Object> retrieveParams = new HashMap<>();
		List<String> expandList = new ArrayList<>();
		expandList.add("sources");
		retrieveParams.put("expand", expandList);
		Customer customer = addLinkTo(Customer.retrieve(id, retrieveParams, null));
		return customer;
	}

	@Override
	public List<Plan> getCustomerPlans(String id) throws StripeException {
		Map<String, Object> params = new HashMap<>();
		params.put("customer", id);
		List<Subscription> subs = Subscription.list(params).getData();
		
		List<Plan> plans = Lists.newArrayList();
		for (Subscription sub : subs) {
			if (activeSubscriptionStatuses.contains(sub.getStatus())) {
				for (SubscriptionItem item : sub.getItems().getData()) {
					plans.add(item.getPlan());
				}
			}

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

		return subscribeCustomerToPlan(customer.getId(), planId, 1, 30, null);
	}
	@Override
	public Subscription subscribeCustomerToPlan(String stripeId, String planId, Integer quantity, Integer trialPeriod) throws StripeException {
		return subscribeCustomerToPlan(stripeId, planId, quantity, trialPeriod, null);
	}
	
	@Override
	public Subscription subscribeCustomerToPlan(String stripeId, String planId, Integer quantity, Integer trialPeriod, String coupon) throws StripeException {

		Map<String, Object> item = new HashMap<String, Object>();
		item.put("plan", planId);
		item.put("quantity", quantity);

		Map<String, Object> items = new HashMap<String, Object>();
		items.put("0", item);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", stripeId);
		params.put("items", items);
		params.put("trial_period_days", trialPeriod);
		Subscription subscription = Subscription.create(params);
				
		return subscription;
	}

	@Override
	public Card addCardToCustomer(String stripeToken, Account account) throws StripeException {
		Customer customer = getCustomer(account.getStripeId());
		
		Map<String, Object> creationParams = new HashMap<String, Object>();
		creationParams.put("source", stripeToken);        		
		Card card = (Card) customer.getSources().create(creationParams);
		return card;
	}
	
	@Override
	public Customer getStripeCustomer(Account account) throws StripeException {
		return getCustomer(account.getStripeId());
	}

	@Override
	public List<Subscription> getCustomerSubscriptions(String id) throws StripeException {
		Map<String, Object> params = new HashMap<>();
		params.put("customer", id);
		List<Subscription> subs = Subscription.list(params).getData();
		return subs;
	}

	@Override
	public Subscription checkSubscription(String id) throws StripeException {
		List<Subscription> subs = getCustomerSubscriptions(id);
		Subscription subscription = null;
		for (Subscription sub : subs) {
			sub.getEndedAt();
			if (activeSubscriptionStatuses.contains(sub.getStatus())) {
				subscription = sub;
				break;
			}
			if ((subscription != null) && (subscription.getEndedAt() < sub.getEndedAt())) subscription = sub;			
		}
		return subscription;
	}
	
	
	@Override
	public List<Invoice> getCustomerInvoices(String id) throws StripeException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", id);
		return Invoice.list(params).getData();
	}
	
	@Override
	public Invoice getCustomerNextInvoice(String id) throws StripeException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", id);
		return Invoice.upcoming(params);
	}
	


	@Override
	public String getDashboardPrefix(Customer customer) throws StripeException {
		String link;
		if (customer.getLivemode()) {
			link = DASHBOARD;
		} else {
			link = DASHBOARD + "test/";
		}
		return link;
	}

	// Helper functions to add stripe links

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
