package com.talytica.common.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

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

	@Value("be0791d3-a047-4cbb-9b06-a79450822c46")
	private String INVITE_TEMPLATE_ID;
	
	@Value("be0791d3-a047-4cbb-9b06-a79450822c46")  // Use Above
	private String INVITE_REMINDER_TEMPLATE_ID;
	
	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d")
	private String PHONE_INVITE_TEMPLATE_ID;
	
	@Value("ea059aa6-bac6-41e0-821d-98dc4dbfc31d")  // Use Above
	private String PHONE_REMINDER_TEMPLATE_ID;
		
	@Value("dfae3d61-007a-4991-a8f3-f46290313859")
	private String FORGOT_PASSWORD_TEMPLATE_ID;
	
	@Value("6d624c56-e765-4419-882a-6baa44bf02bc")
	private String NEW_ACCOUNT_TEMPLATE_ID;	

	@Value("ff02e214-d13a-45ad-837e-9159f42a7180")
	private String RESULTS_TEMPLATE_ID;
	
	@Value("8e5983ac-913d-4370-8ea9-312ff8665f39")
	private String GRADER_NOTIFICATION_TEMPLATE_ID; 

	@Value("321ca619-10ed-4e80-9eb9-23a371d60aac") 
	private String REFERENCE_TEMPLATE_ID;
	
	@Value("321ca619-10ed-4e80-9eb9-23a371d60aac") // Use Above
	private String REFERENCE_REMINDER_TEMPLATE_ID;

	@Value("321ca619-10ed-4e80-9eb9-23a371d60aac") // Not Created Yet
	private String QUICKREF_TEMPLATE_ID;
	
	@Value("321ca619-10ed-4e80-9eb9-23a371d60aac") // Use Above
	private String QUICKREF_REMINDER_TEMPLATE_ID;
	
	
	@Value("${com.talytica.apis.sendgrid}")
	private String SG_API;
	
	@Value("${email.delivery.override.enabled:false}")
	private Boolean emailDeliveryOverrideEnabled;
	
	@Value("${email.delivery.override.address:email-delivery-overrides@talytica.com}")
	private String deliveryOverrideAddress;

	@Value("info@talytica.com")
	private String EMAIL_ADDRESS;
	
	@Autowired
	ExternalLinksService externalLinksService;
	
	@Autowired
	AccountSurveyRepository accountSurveyRepository;
	
	private final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();
	private Email FROM_ADDRESS = new Email (EMAIL_ADDRESS);
	
	@PostConstruct
	private void reportDeliveryConfiguration() {
		if(emailDeliveryOverrideEnabled) {
			log.warn("Email delivery OVERRIDE ENABLED !! - Emails will be diverted to: {}", deliveryOverrideAddress);
		} else {
			log.warn("Emails will be DELIVERED to target recipients - no overrides enabled !");
		}
	}

	@Override
	public void sendMessage(String from, String to, String subject, String text, String html) {
		
		Email fromEmail = new Email(from);
		Email toEmail = getEmailDeliveryAddress(to);
		Content textContent = new Content("text/plain", text);
		Mail email = new Mail(fromEmail, subject, toEmail, textContent);
		if (html != null) {
			Content textHtml = new Content("text/html", html);
			email.addContent(textHtml);
		}	
		
		asynchSend(email);
	
		return;
	}
	
	private Email getEmailDeliveryAddress(String to) {
		String deliveryAddress = null;
		
		if(! emailDeliveryOverrideEnabled) {
			deliveryAddress = to;
		} else {
			log.warn("Email delivery diverted to: {} instead of actual: {}", deliveryOverrideAddress, to);
			deliveryAddress = deliveryOverrideAddress;
		}
		
		return new Email(deliveryAddress);
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
		if (respondant.getAccount().getDefaultEmail() != null) {
			email.setFrom(new Email(EMAIL_ADDRESS, respondant.getAccount().getAccountName()));
			email.setReplyTo(new Email(respondant.getAccount().getDefaultEmail()));
		} else {
			email.setFrom(FROM_ADDRESS);			
		}
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
				email.setSubject("Reminder: Complete Questionnaire");
				email.setTemplateId(INVITE_REMINDER_TEMPLATE_ID);
			} else {
				email.setSubject("Invitation from " + as.getAccount().getAccountName());
				email.setTemplateId(INVITE_TEMPLATE_ID);
			}
		}
		// replace template if there is a custom template set on account survey.
		if ((as.getInviteTemplateId() != null) && !as.getInviteTemplateId().isEmpty()) email.setTemplateId(as.getInviteTemplateId());
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
	
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[FNAME]", respondant.getPerson().getFirstName());
		pers.addSubstitution("[FULL_NAME]", respondant.getPerson().getFirstName() + " " +respondant.getPerson().getLastName());
		pers.addSubstitution("[ACCOUNT_NAME]",as.getAccount().getAccountName());
		if (respondant.getPosition() != null) pers.addSubstitution("[JOB_TITLE]", respondant.getPosition().getPositionName());		
		pers.addTo(getEmailDeliveryAddress(respondant.getPerson().getEmail()));

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
		pers.addTo(getEmailDeliveryAddress(user.getEmail()));		
		
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
		pers.addTo(getEmailDeliveryAddress(respondant.getEmailRecipient()));		
		
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

		if (grader.getRespondant().getAccount().getDefaultEmail() != null) {
			email.setFrom(new Email(EMAIL_ADDRESS, grader.getRespondant().getAccount().getAccountName()));
			email.setReplyTo(new Email(grader.getRespondant().getAccount().getDefaultEmail()));
		} else {
			email.setFrom(FROM_ADDRESS);		
		}
		String link = externalLinksService.getReferenceEmailLink(grader);
		String declineLink = externalLinksService.getReferenceDeclineLink(grader);
		Respondant respondant = grader.getRespondant();
		String positionName = respondant.getPosition().getPositionName();
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
		pers.addSubstitution("[DECLINE_LINK]", declineLink );
		pers.addSubstitution("[APPLICANT]", fullname );
		pers.addSubstitution("[POSITION_NAME]", positionName );
		pers.addSubstitution("[GRADER_NAME]", grader.getPerson().getFirstName());
		pers.addSubstitution("[ACCOUNT_NAME]",respondant.getAccount().getAccountName());
		pers.addTo(getEmailDeliveryAddress(grader.getPerson().getEmail()));		

		email.addPersonalization(pers);

		asynchSend(email);
	}

	
	@Override
	public void sendQuickReference(Grader grader) {
		sendQuickReference(grader, false);
	}

	@Override
	public void sendQuickReferenceReminder(Grader grader) {
		sendQuickReference(grader, true);
	}
	
	public void sendQuickReference(Grader grader, boolean reminder) {
		// TODO: Fill out with one click reference
	}
	
	@Override
	public void sendGraderRequest(Grader grader) {
		sendGraderRequest(grader, false);
		
	}

	@Override
	public void sendGraderReminder(Grader grader) {
		sendGraderRequest(grader, true);	
	}
	
	public void sendGraderRequest(Grader grader, boolean reminder){	
		Mail email = new Mail();
		email.setFrom(FROM_ADDRESS);
		String link = externalLinksService.getGraderEmailLink(grader);
		Respondant respondant = grader.getRespondant();
		String fullname = respondant.getPerson().getFirstName() + " " + respondant.getPerson().getLastName();
		String body = "Dear " + grader.getUser().getFirstName() + ",\n" + "\n"
				+ fullname + " has completed an assessment that requires evaluation as part of their application to "
				+ respondant.getAccount().getAccountName() + ".\n"
				+ "Please visit the Talytica portal at this link: \n"
				+ link;
		
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
		if (reminder) {
			email.setSubject("Reminder: " + fullname + "'s Assessment requires evaluation");
			email.setTemplateId(GRADER_NOTIFICATION_TEMPLATE_ID);
		} else {
			email.setSubject("Assessment completed: " + fullname);
			email.setTemplateId(GRADER_NOTIFICATION_TEMPLATE_ID);
		}
		
		Personalization pers = new Personalization();
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[FULL_NAME]", fullname );
		pers.addTo(getEmailDeliveryAddress(grader.getUser().getEmail()));		

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

}