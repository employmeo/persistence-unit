package com.talytica.common.service;

import java.util.List;

import com.employmeo.data.model.Account;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;

public interface BillingService {
	
	List<String> getActiveSubscriptionStatuses();

	List<Plan> getAllPlans() throws StripeException;

	Customer createCustomerFor(Account account) throws StripeException;
	
	Subscription subscribeCustomerToPlan(Customer customer, String planId) throws StripeException;	
	
	List<Customer> getAllCustomers() throws StripeException;

	Customer getCustomer(String email) throws StripeException;
	
	Customer getStripeCustomer(Account account) throws StripeException;

	List<Plan> getCustomerPlans(String id) throws StripeException;

	Card addCardToCustomer(String stripeToken, Account account) throws StripeException;

	String getStripePK();

}
