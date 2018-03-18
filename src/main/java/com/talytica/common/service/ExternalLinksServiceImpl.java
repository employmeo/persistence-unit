package com.talytica.common.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.employmeo.data.model.AccountSurvey;
import com.employmeo.data.model.Grader;
import com.employmeo.data.model.Respondant;
import com.employmeo.data.model.User;

@Service
public class ExternalLinksServiceImpl implements ExternalLinksService {
	
	@Value("${com.talytica.urls.assessment}")
	private String BASE_SURVEY_URL;
	
	@Value("${com.talytica.urls.portal}")
	private String BASE_PORTAL_URL;
	
	@Value("${com.talytica.urls.integration}")
	private String BASE_SERVICE_URL;
	
	
	@Override
	public String getAssessmentLinkBase() {
		String link = null;
		try {
			link = new URL(BASE_SURVEY_URL + "/?&respondant_uuid=").toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/?&respondant_uuid=";
		}
		return link.toString();
	}
	
	@Override
	public String getAssessmentLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(
					BASE_SURVEY_URL + "/?&respondant_uuid=" + respondant.getRespondantUuid())
							.toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/?&respondant_uuid=" + respondant.getRespondantUuid();
		}
		return link.toString();
	}
	
	@Override
	public String getAssessmentLink(AccountSurvey accountSurvey) {
		String link = null;
		try {
			link = new URL(
					BASE_SURVEY_URL + "/?&asUuid=" + accountSurvey.getUuId())
							.toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/?&asUuid=" + accountSurvey.getUuId();
		}
		return link.toString();
	}

	@Override
	public String getPortalLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(
					BASE_PORTAL_URL + "/?&component=candidate_detail&respondantUuid=" + respondant.getRespondantUuid())
							.toString();
		} catch (Exception e) {
			link = BASE_PORTAL_URL + "/?&component=candidate_detail&respondantUuid=" + respondant.getRespondantUuid();
		}
		return link.toString();
	}

	@Override
	public String getRenderLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(BASE_PORTAL_URL + "/?&respondantUuid=" + respondant.getRespondantUuid())
					.toString();
		} catch (Exception e) {
			link = BASE_PORTAL_URL + "/?&respondantUuid=" + respondant.getRespondantUuid();
		}
		return link.toString();
	}

	@Override
	public String getForgotPasswordLink(User user) {
		String link = null;
		try {
			link = BASE_PORTAL_URL + "/?&email=" + user.getEmail() + "&hash=" + user.getPassword();
		} catch (Exception e) {
			link = BASE_PORTAL_URL + "/?&email=" + user.getEmail() + "&hash=" + user.getPassword();
		}
		return link.toString();
	}
	
	@Override
	public String getVerifyEmailLink(User user) {
		String link = null;
		try {
			link = BASE_PORTAL_URL + "/?&email=" + user.getEmail() + "&hash=" + user.getPassword();
		} catch (Exception e) {
			link = BASE_PORTAL_URL + "/?&email=" + user.getEmail() + "&hash=" + user.getPassword();
			}
		return link.toString();
	}

	@Override
	public String getReferenceEmailLink(Grader grader) {
		String link = null;
		try {
			link = new URL(
					BASE_SURVEY_URL + "/?&graderUuid=" + grader.getUuId()).toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/?&graderUuid=" + grader.getUuId();
		}
		return link.toString();
	}

	@Override
	public String getGraderEmailLink(Grader grader) {
		String link = null;
		try {
			link = new URL(BASE_PORTAL_URL + "/?&component=grading").toString();
		} catch (Exception e) {
			link = BASE_PORTAL_URL + "/?&component=grading";
		}
		return link.toString();
	}

	@Override
	public String getReferenceDeclineLink(Grader grader) {
		String link = null;
		try {
			link = new URL(
					BASE_SURVEY_URL + "/survey/1/grader/" + grader.getUuId()+"/decline").toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/survey/1/grader/" + grader.getUuId()+"/decline";
		}
		return link.toString();
	}
	
	@Override
	public String getCallMeLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(BASE_SURVEY_URL + "/survey/1/twilio/" + 
							respondant.getAccountSurveyId()+"/findbyid?&Digits=" +
							respondant.getPayrollId()).toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/survey/1/twilio/" + 
					respondant.getAccountSurveyId()+"/findbyid?&Digits=" +
					respondant.getPayrollId();
		}
		return link.toString();
	}

	@Override
	public String getCallStatusLink() {
		String link = null;
		try {
			link = new URL(BASE_SURVEY_URL + "/survey/1/twilio/status").toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/survey/1/twilio/status";
		}
		return link.toString();
	}

	@Override
	public String getIntegrationEcho() {
		String link = null;
		try {
			link = new URL(BASE_SERVICE_URL + "/integration/echo").toString();
		} catch (Exception e) {
			link = BASE_SERVICE_URL + "/integration/echo";
		}
		return link.toString();
	}	
	
}
