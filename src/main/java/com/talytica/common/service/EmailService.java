package com.talytica.common.service;

import org.springframework.stereotype.Service;

import com.employmeo.data.model.Respondant;
import com.employmeo.data.model.User;

@Service
public interface EmailService {
	
	public void sendMessage(String from, String to, String subject, String text, String html) ;

	public void sendEmailInvitation(Respondant respondant);
	
	public void sendForgotPass(User user) ;
	
	public void sendResults(Respondant respondant);
	
	public String getAssessmentLink(Respondant respondant) ;

	public String getPortalLink(Respondant respondant);
	
	public String getRenderLink(Respondant respondant);
	
	public String getForgotPasswordLink(User user);
	
	public String getVerifyEmailLink(User user);

}