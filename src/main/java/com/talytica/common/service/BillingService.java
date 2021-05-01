package com.talytica.common.service;

import java.util.List;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.User;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;

public interface BillingService {
	
	List<String> getActiveSubscriptionStatuses();

	String getStripePK();

	List<Plan> getAllPlans() throws StripeException;
	
	void clearCache();

	Customer createCustomerFor(Account account) throws StripeException;

	Customer createCustomerFor(Account account, User user) throws StripeException;
	
	Subscription subscribeCustomerToPlan(Customer customer, String planId) throws StripeException;	

	Subscription subscribeCustomerToPlan(String customerId, String planId, Integer quantity, Integer trialPeriod)
			throws StripeException;
	
	Subscription subscribeCustomerToPlan(String customerId, String planId, Integer quantity, Integer trialPeriod, String coupon)
			throws StripeException;

	Card addCardToCustomer(String stripeToken, Account account) throws StripeException;

	Customer getCustomer(String email) throws StripeException;
	
	Customer getStripeCustomer(Account account) throws StripeException;

	Subscription checkSubscription(String id) throws StripeException;

	List<Plan> getCustomerPlans(String id) throws StripeException;

	List<Invoice> getCustomerInvoices(String id) throws StripeException;

	Invoice getCustomerNextInvoice(String id) throws StripeException;

	String getDashboardPrefix(Customer customer) throws StripeException;

}
