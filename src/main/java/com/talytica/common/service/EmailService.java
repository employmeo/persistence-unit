package com.talytica.common.service;

import com.employmeo.data.model.Grader;
import com.employmeo.data.model.Respondant;
import com.employmeo.data.model.User;

public interface EmailService {
	
	public void sendMessage(String from, String to, String subject, String text, String html) ;
	public void sendEmailInvitation(Respondant respondant);
	public void sendEmailInvitation(Respondant respondant, String bcc);	
	public void sendEmailReminder(Respondant respondant);
	public void sendEmailReminder(Respondant respondant, String bcc);	
	public void sendVerifyAccount(User user) ;	
	public void sendForgotPass(User user);	
	public void sendResults(Respondant respondant);	
	public void sendReferenceRequest(Grader grader);
	public void sendReferenceRequestReminder(Grader grader);	
	public void sendQuickReference(Grader grader);
	public void sendQuickReferenceReminder(Grader grader);	
	public void sendGraderRequest(Grader grader);
	public void sendGraderReminder(Grader grader);
	
}