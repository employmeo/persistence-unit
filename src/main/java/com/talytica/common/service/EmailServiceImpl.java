package com.talytica.common.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.employmeo.data.model.AccountSurvey;
import com.employmeo.data.model.Grader;
import com.employmeo.data.model.Respondant;
import com.employmeo.data.model.Survey;
import com.employmeo.data.model.User;
import com.employmeo.data.repository.AccountSurveyRepository;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d")
	private String INVITE_TEMPLATE_ID;
	
	@Value("8e5983ac-913d-4370-8ea9-312ff8665f39")
	private String RESULTS_TEMPLATE_ID;
	
	@Value("dfae3d61-007a-4991-a8f3-f46290313859")
	private String FORGOT_PASSWORD_TEMPLATE_ID;
		
	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d") // Not Created Yet
	private String INVITE_REMINDER_TEMPLATE_ID;
	
	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d") // Not Created Yet
	private String PHONE_INVITE_TEMPLATE_ID;
	
	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d") // Not Created Yet
	private String PHONE_REMINDER_TEMPLATE_ID;
	
	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d") // Not Created Yet
	private String REFERENCE_TEMPLATE_ID;
	
	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d") // Not Created Yet
	private String REFERENCE_REMINDER_TEMPLATE_ID;

	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d") // Not Created Yet
	private String QUICKREF_TEMPLATE_ID;
	
	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d") // Not Created Yet
	private String QUICKREF_REMINDER_TEMPLATE_ID;
	
	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d") // Not Created Yet
	private String NEW_ACCOUNT_TEMPLATE_ID;
	
	@Value("${com.talytica.apis.sendgrid}")
	private String SG_API;

	@Autowired
	ExternalLinksService externalLinksService;
	
	@Autowired
	AccountSurveyRepository accountSurveyRepository;
	
	private final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();
	private Email FROM_ADDRESS = new Email ("info@talytica.com");

	@Override
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

	@Override
	public void sendEmailInvitation(Respondant respondant) {
		sendEmailInvitation(respondant, false);
	}
	
	@Override
	public void sendEmailReminder(Respondant respondant) {
		sendEmailInvitation(respondant, true);
		
	}
	
	public void sendEmailInvitation(Respondant respondant, boolean reminder){
	
		Mail email = new Mail();
		email.setFrom(FROM_ADDRESS);
		Personalization pers = new Personalization();
		String link = externalLinksService.getAssessmentLink(respondant);
		String body = null;

		AccountSurvey as = respondant.getAccountSurvey();	
		if (Survey.TYPE_PHONE == as.getSurvey().getSurveyType()) {
		
			String idnum = respondant.getPayrollId();
			String phonenum = as.getPhoneNumber();
		
			body = "Dear " + respondant.getPerson().getFirstName() + ",\n" + "\n"
					+ "You have been invited to take a questionnaire as part of your application to "
					+ as.getAccount().getAccountName() + ".\n"
					+ "This automated questionairre can be completed over the phone. Please call: \n"
					+ phonenum + ", and you will be prompted for this ID number: " + idnum;
			pers.addSubstitution("[PHONE]", phonenum );
			pers.addSubstitution("[IDNUMBER]", idnum );
			if (reminder) {
				email.setSubject("Reminder: Complete Voice Application");
				email.setTemplateId(PHONE_REMINDER_TEMPLATE_ID);
			} else {
				email.setSubject("Invitation To Apply");
				email.setTemplateId(PHONE_INVITE_TEMPLATE_ID);
			}
		} else {
			body = "Dear " + respondant.getPerson().getFirstName() + ",\n" + "\n"
					+ "You have been invited to take a questionnaire as part of your application to "
					+ as.getAccount().getAccountName() + ".\n"
					+ "This assessment can be completed on a mobile device or in a browser at this link: \n"
					+ link;
			if (reminder) {
				email.setSubject("Invitation To Apply");
				email.setTemplateId(INVITE_TEMPLATE_ID);
			} else {
				email.setSubject("Reminder: Complete Application");
				email.setTemplateId(INVITE_REMINDER_TEMPLATE_ID);
			}
		}
		
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
	
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[FNAME]", respondant.getPerson().getFirstName() + " " +respondant.getPerson().getLastName());
		pers.addSubstitution("[ACCOUNT_NAME]",as.getAccount().getAccountName());
		pers.addTo(new Email(respondant.getPerson().getEmail()));		

		email.addPersonalization(pers);

		asynchSend(email);
	}
	
	
	@Override
	public void sendForgotPass(User user) {
		sendPassReset(user, false);
	}
	
	@Override
	public void sendVerifyAccount(User user) {
		sendPassReset(user, true);
	}
	
	public void sendPassReset(User user, boolean newUser) {

		Mail email = new Mail();
		email.setFrom(FROM_ADDRESS);
		String link = null;
		
		if (newUser) {
			email.setSubject("New Account Email Verification");
			email.setTemplateId(NEW_ACCOUNT_TEMPLATE_ID);
			link = externalLinksService.getVerifyEmailLink(user);
		} else {
			email.setSubject("Forgot Password");
			email.setTemplateId(FORGOT_PASSWORD_TEMPLATE_ID);
			link = externalLinksService.getForgotPasswordLink(user);
		}
		
		String body = "Dear " + user.getFirstName() + ",\n" + "\n"
				+ "A password reset was requested on your behalf. If you initiated this request,\n"
				+ "please click the Reset Password button below to continue:\n" + link 
				+ "\nIf you did not initiate this request, please ignore this email.";
		
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
		
		Personalization pers = new Personalization();
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[FNAME]", user.getFirstName());
		pers.addSubstitution("[ACCOUNT_NAME]",user.getAccount().getAccountName());	
		pers.addTo(new Email(user.getEmail()));		
		
		email.addPersonalization(pers);
		asynchSend(email);
	}	
	
	@Override
	public void sendResults(Respondant respondant) {

		String fullname = respondant.getPerson().getFirstName() + " " + respondant.getPerson().getLastName();
		String link = externalLinksService.getPortalLink(respondant);
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

	@Override
	public void sendReferenceRequest(Grader grader) {
		sendReferenceRequest(grader, false);
		
	}

	@Override
	public void sendReferenceRequestReminder(Grader grader) {
		sendReferenceRequest(grader, true);
		
	}

	public void sendReferenceRequest(Grader grader, boolean reminder){	
		Mail email = new Mail();
		email.setFrom(FROM_ADDRESS);
		String link = externalLinksService.getReferenceEmailLink(grader);
		Respondant respondant = grader.getRespondant();
		String fullname = respondant.getPerson().getFirstName() + " " + respondant.getPerson().getLastName();
		String body = "Dear " + grader.getPerson().getFirstName() + ",\n" + "\n"
				+ "You have been requested by: " + fullname + " to provide input on their application to "
				+ respondant.getAccount().getAccountName() + ".\n"
				+ "This assessment can be completed on a mobile device or in a browser at this link: \n"
				+ link;
		
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
		if (reminder) {
			email.setSubject("Reminder: " + fullname + " asked for your referall");
			email.setTemplateId(REFERENCE_REMINDER_TEMPLATE_ID);
		} else {
			email.setSubject("Reference Request from: " + fullname);
			email.setTemplateId(REFERENCE_TEMPLATE_ID);
		}
		
		Personalization pers = new Personalization();
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[APPLICANT]", fullname );
		pers.addSubstitution("[GRADER_NAME]", grader.getPerson().getFirstName());
		pers.addSubstitution("[ACCOUNT_NAME]",respondant.getAccount().getAccountName());
		pers.addTo(new Email(grader.getPerson().getEmail()));		

		email.addPersonalization(pers);

		asynchSend(email);
	}

	
	@Override
	public void sendQuickReference(Grader grader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendQuickReferenceReminder(Grader grader) {
		// TODO Auto-generated method stub
		
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
}