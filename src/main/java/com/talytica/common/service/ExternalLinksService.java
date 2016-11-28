package com.talytica.common.service;

import com.employmeo.data.model.Grader;
import com.employmeo.data.model.Respondant;
import com.employmeo.data.model.User;

public interface ExternalLinksService {

	public String getAssessmentLink(Respondant respondant) ;

	public String getPortalLink(Respondant respondant);
	
	public String getRenderLink(Respondant respondant);
	
	public String getForgotPasswordLink(User user);
	
	public String getVerifyEmailLink(User user);
	
	public String getReferenceEmailLink(Grader grader);
	
}