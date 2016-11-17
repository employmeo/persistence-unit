package com.talytica.common.service;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.employmeo.data.model.Respondant;
import com.employmeo.data.model.User;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class EmailServiceImpl implements EmailService {

	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d")
	private String INVITE_TEMPLATE_ID;
	
	@Value("8e5983ac-913d-4370-8ea9-312ff8665f39")
	private String RESULTS_TEMPLATE_ID;
	
	@Value("dfae3d61-007a-4991-a8f3-f46290313859")
	private String FORGOT_PASSWORD_TEMPLATE_ID;
	
	@Value("${com.talytica.apis.sendgrid}")
	private String SG_API;
	
	@Value("${com.talytica.urls.assessment}")
	private String BASE_SURVEY_URL;
	
	@Value("${com.talytica.urls.portal}")
	private String BASE_PORTAL_URL;
	
	@Value("${com.talytica.urls.integration}")
	private String BASE_SERVICE_URL;
	
	private final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();
	private Email FROM_ADDRESS = new Email ("info@talytica.com");
	private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	public void sendMessage(String from, String to, String subject, String text, String html) {
		
		Email fromEmail = new Email(from);
		Email toEmail = new Email(to);
		Content textContent = new Content("text/plain", text);
		Mail email = new Mail(fromEmail, subject, toEmail, textContent);
		if (html != null) {
			Content textHtml = new Content("text/html", html);
			email.addContent(textHtml);
		}	
		
		asynchSend(email);
	
		return;
	}

	public void sendEmailInvitation(Respondant respondant) {

		String link = getAssessmentLink(respondant);

		String body = "Dear " + respondant.getPerson().getFirstName() + ",\n" + "\n"
				+ "You have been invited to take a questionnaire as part of your application to "
				+ respondant.getAccount().getAccountName() + ".\n"
				+ "This assessment can be completed on a mobile device or in a browser at this link: \n"
				+ link;
		
		
		Mail email = new Mail();
		email.setFrom(FROM_ADDRESS);
		email.setSubject("Invitation To Apply");
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
		email.setTemplateId(INVITE_TEMPLATE_ID);

		Personalization pers = new Personalization();
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[FNAME]", respondant.getPerson().getFirstName() + " " +respondant.getPerson().getLastName());
		pers.addSubstitution("[ACCOUNT_NAME]",respondant.getAccount().getAccountName());
		pers.addTo(new Email(respondant.getPerson().getEmail()));		

		email.addPersonalization(pers);

		asynchSend(email);

		return;
	}
	
	public void sendForgotPass(User user) {

		String link = getForgotPasswordLink(user);
		
		String body = "Dear " + user.getFirstName() + ",\n" + "\n"
				+ "A password reset was requested on your behalf. If you initiated this request,\n"
				+ "please click the Reset Password button below to continue:\n" + link 
				+ "\nIf you did not initiate this request, please ignore this email.";
		
		Mail email = new Mail();
		email.setFrom(FROM_ADDRESS);
		email.setSubject("Forgot Password");
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
		email.setTemplateId(FORGOT_PASSWORD_TEMPLATE_ID);

		Personalization pers = new Personalization();
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[FNAME]", user.getFirstName());
		pers.addSubstitution("[ACCOUNT_NAME]",user.getAccount().getAccountName());	
		pers.addTo(new Email(user.getEmail()));		
		
		email.addPersonalization(pers);
		asynchSend(email);

		return;
	}
	
	public void sendResults(Respondant respondant) {

		String fullname = respondant.getPerson().getFirstName() + " " + respondant.getPerson().getLastName();
		String link = getPortalLink(respondant);
		String body = "The assessment for applicant " + fullname
				+ " has been submitted and scored. The results are now available on the portal at:\n"
		        + link + "\n";	
		
		Mail email = new Mail();
		email.setFrom(FROM_ADDRESS);
		email.setSubject("Assessment Results Available: " + fullname);
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
		email.setTemplateId(RESULTS_TEMPLATE_ID);

		Personalization pers = new Personalization();
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[FULL_NAME]", fullname);
		pers.addSubstitution("[ACCOUNT_NAME]",respondant.getAccount().getAccountName());
		pers.addTo(new Email(respondant.getEmailRecipient()));		
		
		email.addPersonalization(pers);
		asynchSend(email);
	}
	
	private void asynchSend(Mail email) {
		
	    SendGrid sg = new SendGrid(SG_API);
	    Request request = new Request();
        request.method = Method.POST;
        request.endpoint = "mail/send";
	    
		TASK_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {	
			    try {
			        request.body = email.build();
			        log.debug("Sending Email with request body {}", request.body);
			        Response resp = sg.api(request);
			        log.debug("Sent Email with Response code {} Response Body {}", resp.statusCode, resp.body);

			      } catch (Exception e) {
			    	log.error("Sent Email failed with request {}, exception {}", request.body, e);
			      }
			}
		});
	}

	public String getAssessmentLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(
					BASE_SURVEY_URL + "/take" + "?&respondant_uuid=" + respondant.getRespondantUuid())
							.toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/take" + "?&respondant_uuid=" + respondant.getRespondantUuid();
		}
		return link.toString();
	}

	public String getPortalLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(
					BASE_PORTAL_URL + "/portal.htm?&component=respondant_score&respondantUuid=" + respondant.getRespondantUuid())
							.toString();
		} catch (Exception e) {
			link = BASE_PORTAL_URL + "/portal.htm?&component=respondant_score&respondantUuid=" + respondant.getRespondantUuid();
		}
		return link.toString();
	}

	public String getRenderLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(BASE_PORTAL_URL + "/render.htm?&respondant_uuid=" + respondant.getRespondantUuid())
					.toString();
		} catch (Exception e) {
			link = BASE_PORTAL_URL + "/render.htm?&respondant_uuid=" + respondant.getRespondantUuid();
		}
		return link.toString();
	}

	public String getForgotPasswordLink(User user) {
		String link = null;
		try {
			link = BASE_PORTAL_URL + "/portal.htm?&component=resetpass&email=" + user.getEmail()
			+ "&hash=" + user.getPassword();
		} catch (Exception e) {
			link = BASE_PORTAL_URL + "/portal.htm?&component=resetpass&email=" + user.getEmail()
					+ "&hash=" + user.getPassword();		}
		return link.toString();
	}

}