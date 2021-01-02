package com.talytica.common.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.AccountSurvey;
import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.CustomProfile;
import com.employmeo.data.model.Grade;
import com.employmeo.data.model.Grader;
import com.employmeo.data.model.Prediction;
import com.employmeo.data.model.PredictionTarget;
import com.employmeo.data.model.Question;
import com.employmeo.data.model.ReferenceCheckConfig;
import com.employmeo.data.model.Respondant;
import com.employmeo.data.model.RespondantNVP;
import com.employmeo.data.model.RespondantScore;
import com.employmeo.data.model.SendGridEmailEvent;
import com.employmeo.data.model.Survey;
import com.employmeo.data.model.User;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.employmeo.data.repository.SendGridEventRepository;
import com.employmeo.data.service.AccountSurveyService;
import com.employmeo.data.service.CorefactorService;
import com.employmeo.data.service.GraderService;
import com.employmeo.data.service.PredictionModelService;
import com.employmeo.data.service.RespondantService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.stripe.model.Invoice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	@Value("e7274ea4-b73d-4045-a020-92e6f0ad9701")
	private String INVITE_TEMPLATE_ID;
	
	@Value("e7274ea4-b73d-4045-a020-92e6f0ad9701")  // Use Above
	private String INVITE_REMINDER_TEMPLATE_ID;
	
	@Value("227f514f-b13a-4597-9e12-3a00cd857092")
	private String PHONE_INVITE_TEMPLATE_ID;
	
	@Value("227f514f-b13a-4597-9e12-3a00cd857092")  // Use Above
	private String PHONE_REMINDER_TEMPLATE_ID;
		
	@Value("5c9cf714-579d-417e-8ef3-6759de5851b9")
	private String FORGOT_PASSWORD_TEMPLATE_ID;
	
	@Value("b1b493ac-f3ef-4605-bb22-6f42e8c3ac00")
	private String NEW_ACCOUNT_TEMPLATE_ID;	

	@Value("a23150f8-1ca3-4eb2-ab1b-095cc1dc444a")
	private String RESULTS_TEMPLATE_ID;
	
	@Value("6a571c21-7ce9-445e-875b-feb104db5c0c")
	private String RESULTS_DETAIL_TEMPLATE_ID;

	@Value("ab39745a-c848-4a2f-b626-15f8d7b6be3a")
	private String GRADER_NOTIFICATION_TEMPLATE_ID; 

	@Value("c2415b25-4ede-488b-8642-a06d7dd26726") 
	private String REFERENCE_TEMPLATE_ID;
	
	@Value("c2415b25-4ede-488b-8642-a06d7dd26726") // Use Above
	private String REFERENCE_REMINDER_TEMPLATE_ID;
	
	@Value("a89525e6-ab8a-4ab1-b3d8-afa0225feb7b") // Not Created Yet
	
	private String INVOICE_TEMPLATE_ID;
	
	@Value("a89525e6-ab8a-4ab1-b3d8-afa0225feb7b") //  Not Created Yet
	private String INVOICE_REMINDER_TEMPLATE_ID;

	
	@Value("${com.talytica.apis.sendgrid:null}")
	private String SG_API;
	
	@Value("${email.delivery.override.enabled:false}")
	private Boolean emailDeliveryOverrideEnabled;
	
	@Value("${email.delivery.override.address:email-delivery-overrides@talytica.com}")
	private String deliveryOverrideAddress;

	@Value("${email.delivery.whitelist:@talytica.com,@employmeo.com}")
	private String[] deliveryWhitelist;
	
	@Value("${email.delivery.from:no-reply@talytica.com}")
	private String FROM_EMAIL_ADDRESS;
	
	@Value("${email.delivery.replyto:info@talytica.com}")
	private String REPLYTO_EMAIL_ADDRESS;
	
	@Value("${com.talytica.apis.twilio.sid}")
	private String ACCOUNT_SID;
	
	@Value("${com.talytica.apis.twilio.token}")
	private String AUTH_TOKEN;
		
	@Autowired
	ExternalLinksService externalLinksService;

	@Autowired
	AccountSurveyService accountSurveyService;

	@Autowired
	RespondantService respondantService;

	@Autowired
	CorefactorService corefactorService;

	@Autowired
	GraderService graderService;
	
	@Autowired
	PredictionModelService predictionModelService;
	
	@Autowired
	SendGridEventRepository sendGridEventRepository;
	
	
	private final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();
	private Email FROM_ADDRESS;
	private Email REPLYTO_ADDRESS;
	
	@PostConstruct
	private void reportDeliveryConfiguration() {
		if(emailDeliveryOverrideEnabled) {
			log.warn("EMAIL OVERRIDE ENABLED - Emails not addressed to {} be diverted to: {}", deliveryWhitelist, deliveryOverrideAddress);
		} else {
			log.warn("Emails will be DELIVERED to target recipients - no overrides enabled !");
		}
		FROM_ADDRESS = new Email (FROM_EMAIL_ADDRESS);
		REPLYTO_ADDRESS = new Email(REPLYTO_EMAIL_ADDRESS);
		log.info("Emails will be from: {} with reply to: {} by default", FROM_ADDRESS.getEmail(),REPLYTO_ADDRESS.getEmail());
	}

	@Override
	public void sendMessage(String from, String to, String subject, String text, String html) {
		
		// for now - do nothing with BCC
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
		
		if(! emailDeliveryOverrideEnabled) return new Email(to);
		for (String match : deliveryWhitelist) {
			if (to.toLowerCase().indexOf(match.toLowerCase()) > -1) return new Email(to);
		}
		
		log.warn("DIVERTED EMAIL TO: {} INSTEAD OF: {}", deliveryOverrideAddress, to);		
		return new Email(deliveryOverrideAddress);
	}

	@Override
	public void sendEmailInvitation(Respondant respondant) {
		sendEmailInvitation(respondant, false, null);
	}
	
	@Override
	public void sendEmailInvitation(Respondant respondant, String bcc) {
		sendEmailInvitation(respondant, false, bcc);
		
	}

	@Override
	public void sendEmailReminder(Respondant respondant) {
		sendEmailInvitation(respondant, true, null);	
	}

	@Override
	public void sendEmailReminder(Respondant respondant, String bcc) {
		sendEmailInvitation(respondant, true, bcc);	
		
	}
	
	public void sendEmailInvitation(Respondant respondant, boolean reminder, String bcc){	
		Mail email = new Mail();
		if ((respondant.getAccount().getDefaultEmail() != null) && (!respondant.getAccount().getDefaultEmail().isEmpty()) ){
			email.setFrom(new Email(FROM_EMAIL_ADDRESS, respondant.getAccount().getAccountName()));
			email.setReplyTo(new Email(respondant.getAccount().getDefaultEmail()));
		} else {
			email.setFrom(FROM_ADDRESS);			
		}
		Personalization pers = new Personalization();
		String link = externalLinksService.getAssessmentLink(respondant);
		String body = null;

		AccountSurvey as = respondant.getAccountSurvey();
		if (respondant.getRespondantStatus() >= Respondant.STATUS_ADVANCED) as = accountSurveyService.getAccountSurveyById(respondant.getSecondStageSurveyId());
		if (Survey.TYPE_PHONE == as.getSurvey().getSurveyType()) {
		
			String idnum = respondant.getPayrollId();
			if (as.getPrice() == 50d) idnum = respondant.getId().toString();
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
		pers.addSubstitution("[REF_LINK]", externalLinksService.getReferenceShareableLink(respondant));
		
		if (respondant.getPosition() != null) pers.addSubstitution("[JOB_TITLE]", respondant.getPosition().getPositionName());		
		pers.addTo(getEmailDeliveryAddress(respondant.getPerson().getEmail()));
		if (bcc != null && !emailDeliveryOverrideEnabled) pers.addBcc(getEmailDeliveryAddress(bcc));
		email.addCustomArg("person_id", respondant.getPersonId().toString());
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
		String html = "<pre>"+body+"</pre>";;
		String template = RESULTS_TEMPLATE_ID;
		
		ReferenceCheckConfig rcConfig = respondant.getAccountSurvey().getRcConfig();
		log.info("RC config: {}", rcConfig);
		if (rcConfig != null && rcConfig.getSendEmailDetails()) {
			log.debug("Creating detailed response for candidate: {}", respondant.getPerson().getEmail());
			body = createResultsDetailBody(respondant);
			html = "<pre>"+body+"</pre>";//createResultsDetailHTML(respondant);
			template = RESULTS_DETAIL_TEMPLATE_ID;
		}
		
		Mail email = new Mail();
		email.setFrom(FROM_ADDRESS);
		email.setSubject("Assessment Results Available: " + fullname);
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", html));
		email.setTemplateId(template);

		Personalization pers = new Personalization();
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[FULL_NAME]", fullname);
		pers.addSubstitution("[ACCOUNT_NAME]",respondant.getAccount().getAccountName());
		pers.addTo(getEmailDeliveryAddress(respondant.getEmailRecipient()));
		
		email.addCustomArg("person_id", respondant.getPersonId().toString());
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
		
		if ((grader.getPerson().getEmail() == null) && (grader.getPerson().getPhone() != null)) {
			sendReferenceText(grader);
			return;
		}
		
		Mail email = new Mail();
		if ((grader.getRespondant().getAccount().getDefaultEmail() != null) && (!grader.getRespondant().getAccount().getDefaultEmail().isEmpty())) {
			email.setFrom(new Email(FROM_EMAIL_ADDRESS, grader.getRespondant().getAccount().getAccountName()));
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
			email.setSubject("Reminder: " + fullname + " asked for your referral");
			email.setTemplateId(REFERENCE_REMINDER_TEMPLATE_ID);
		} else {
			email.setSubject("Reference Request from: " + fullname);
			email.setTemplateId(REFERENCE_TEMPLATE_ID);
		}
		if (grader.getRcConfig() != null) {
			if ((grader.getRcConfig().getInviteTemplate() != null) &&  (!grader.getRcConfig().getInviteTemplate().isEmpty())) {
				email.setTemplateId(grader.getRcConfig().getInviteTemplate());
			}
		}
		
		Personalization pers = new Personalization();
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[DECLINE_LINK]", declineLink );
		pers.addSubstitution("[APPLICANT]", fullname );
		pers.addSubstitution("[POSITION_NAME]", positionName );
		pers.addSubstitution("[GRADER_NAME]", grader.getPerson().getFirstName());
		pers.addSubstitution("[GRADER_LASTNAME]", grader.getPerson().getLastName());
		pers.addSubstitution("[ACCOUNT_NAME]",respondant.getAccount().getAccountName());
		pers.addTo(getEmailDeliveryAddress(grader.getPerson().getEmail()));	
		
		email.addCustomArg("person_id", grader.getPersonId().toString());
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
	
	@Override
	public void sendInvoice(Account account, Invoice invoice, String email) {
		sendInvoice(account, invoice, email, false);
	}
	@Override
	public void sendInvoiceReminder(Account account, Invoice invoice, String email) {
		 sendInvoice(account, invoice, email, true);	
	}

	public void sendInvoice(Account account, Invoice invoice, String to, Boolean reminder) {
		Mail email = new Mail();
		email.setFrom(FROM_ADDRESS);
		String link = "null"; // need a link! externalLinksService.getGraderEmailLink(grader);
		
		String body = "Your card will be billed for " + invoice.getAmountDue() + " on " + invoice.getDueDate();
		email.addContent(new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
		if (reminder) {
			email.setSubject("Reminder: Invoice Due For Talytica");
			email.setTemplateId(INVOICE_REMINDER_TEMPLATE_ID);
		} else {
			email.setSubject("Invoice Due For Talytica");
			email.setTemplateId(INVOICE_TEMPLATE_ID);
		}
		
		Personalization pers = new Personalization();
		pers.addSubstitution("[LINK]", link );
		pers.addSubstitution("[AMOUNT]", String.format("$#,###.00",invoice.getAmountDue()));
		log.debug("Invoice data: {}",invoice);
		pers.addTo(getEmailDeliveryAddress(to));		
		
		email.addPersonalization(pers);
		asynchSend(email);	
	}
	
	private void asynchSend(Mail email) {
		if (email.getReplyto() == null) email.setReplyTo(REPLYTO_ADDRESS);
	    SendGrid sg = new SendGrid(SG_API);
	    Request request = new Request();
        request.method = Method.POST;
        request.endpoint = "mail/send";
	    
		TASK_EXECUTOR.submit(new Runnable() {
			@Autowired
			EmailService emailService;
			
			@Override
			public void run() {	
			    try {
			        request.body = email.build();
			        log.debug("Sending Email with request body {}", request.body);
			        Response resp = sg.api(request);
			        log.debug("Sent Email with Response code {} Response Body {}", resp.statusCode, resp.body);
			        if (resp.statusCode >= 300) throw new Exception(resp.body);
			      } catch (Exception e) {
			    	log.error("Sent Email failed with request {}, exception {}", request.body, e);
			    	List<SendGridEmailEvent> events = Lists.newArrayList();
			    	List<Personalization> recipieints = email.getPersonalization();
			    	for (Personalization pers : recipieints) {
				    	SendGridEmailEvent sge = new SendGridEmailEvent();
				    	sge.setPersonId(new Long(email.getCustomArgs().get("person_id")));
				    	sge.setEmail(pers.getTos().get(0).getEmail());
				    	sge.setEvent("failed");
				    	sge.setSg_event_id(sge.email + new Date().toString());
				    	sge.setSg_message_id(e.getMessage());
				    	sge.setTimeStamp(new Date());
				    	events.add(sge);
			    	}
			    	emailService.saveAll(events);
			      }
			}
		});
	}

	
	public void sendReferenceText(Grader grader) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		String link = externalLinksService.getReferenceEmailLink(grader);
		Respondant respondant = grader.getRespondant();
		String fullname = respondant.getPerson().getFirstName() + " " + respondant.getPerson().getLastName();

	    PhoneNumber to = new PhoneNumber(grader.getPerson().getPhone());
	    PhoneNumber from = new PhoneNumber(respondant.getAccountSurvey().getPhoneNumber());

	    SendGridEmailEvent sge = new SendGridEmailEvent();
    	sge.setPersonId(grader.getPersonId());
    	sge.setEmail(grader.getPerson().getPhone());
    	sge.setSg_event_id("txt_to_" + sge.email + new Date().toString());
    	sge.setTimeStamp(new Date());
    	sge.setEvent("failed");
    	
	    try {
	    	String message = "Hi " +grader.getPerson().getFirstName() + ", " + fullname + " requested a reference " + link;
			Message smsMessage = Message.creator(to, from, message).create();	
		    log.debug("SMS sent via twilio: {}",smsMessage.getSid());
	    	sge.setEvent("sent");
	    } catch (Exception e) {
	    	log.error("Failed to send text message: {}", e.getMessage());
	    	sge.setSg_message_id(e.getMessage());
	    } finally {
	    	sendGridEventRepository.save(sge);
	    }
	}
	
	
	@Override
	public Iterable<SendGridEmailEvent> saveAll(Iterable<SendGridEmailEvent> events) {
		
		return sendGridEventRepository.saveAll(events);
//		return sendGridEventRepository.save(events);
	}

	private String createResultsDetailBody(Respondant respondant) {
		StringBuffer results = new StringBuffer();
		
		List<String> warnings = respondantService.getWarningMessages(respondant);
		for (String warning : warnings) {
			results.append("WARNING: ");
			results.append(warning);
			results.append("\n\n");
		}
		
		CustomProfile customProfile = respondant.getAccount().getCustomProfile();
		results.append("Category: ");
		results.append(customProfile.getName(respondant.getProfileRecommendation()));
		results.append(" (");
		results.append(respondant.getCompositeScore());
		results.append(")\n\n");
		
		if (respondant.getPredictions().size() > 0) {
			results.append("Prediction Results:\n");		
			for (Prediction prediction : respondant.getPredictions()) {
				PredictionTarget target = predictionModelService.getTargetById(prediction.getTargetId());
				results.append(target.getLabel());
				results.append(": ");
				results.append(String.format("%.2f", prediction.getPredictionScore()));
				results.append("\n");
			}
			results.append("\n");
		}
		
		List<RespondantScore> scores = new ArrayList<RespondantScore>( respondant.getRespondantScores());		
		scores.sort(new Comparator<RespondantScore>() {
			public int compare (RespondantScore a, RespondantScore b) {
				Corefactor corefactorA = corefactorService.findCorefactorById(a.getId().getCorefactorId());
				Corefactor corefactorB = corefactorService.findCorefactorById(b.getId().getCorefactorId());
				double aCoeff = 1d;
				double bCoeff = 1d;
				if (corefactorA.getDefaultCoefficient() != null) aCoeff = Math.abs(corefactorA.getDefaultCoefficient());
				if (corefactorB.getDefaultCoefficient() != null) bCoeff = Math.abs(corefactorB.getDefaultCoefficient());
				// first sort by coefficient - descending
				if (aCoeff != bCoeff) return (int)(bCoeff - aCoeff);
				// otherwise just sort by name
				return corefactorA.getName().compareTo(corefactorB.getName());
			}
		});
		results.append("Summary Scores:\n");		
		for (RespondantScore score : scores) {
			Corefactor cf = corefactorService.findCorefactorById(score.getId().getCorefactorId());
			results.append(cf.getName());
			results.append(" : ");
			
//			this is awful but, for now, always format as two digit leading zero
//			if ((int) score.getValue().doubleValue() == score.getValue()) {
				results.append(String.format("%02d",(int) score.getValue().doubleValue()));
//			} else {
//				notes.append(String.format("%.1f",score.getValue()));
//			}
			results.append("\n");
		}
		results.append("\n");
		
		Set<RespondantNVP> customQuestions = respondantService.getDisplayNVPsForRespondant(respondant.getId());
		if (!customQuestions.isEmpty()) {
			results.append("Candidate Questions:\n");
			for (RespondantNVP nvp : customQuestions) {
				results.append(nvp.getName());
				results.append(" : ");
				results.append(nvp.getValue());
				results.append("\n");
			}
			results.append("\n");
		}
		
		List<Grader> graders = graderService.getGradersByRespondantId(respondant.getId());
		if (!graders.isEmpty()) {

			StringBuffer references = new StringBuffer();
			StringBuffer evaluators = new StringBuffer();
			for (Grader grader : graders) {
				switch (grader.getType()) {
				case Grader.TYPE_PERSON:
					references.append(grader.getPerson().getFirstName());
					references.append(" ");
					references.append(grader.getPerson().getLastName());
					if ((null != grader.getRelationship()) && (!grader.getRelationship().isEmpty()))
					  references.append(" ("+grader.getRelationship()+")");
					references.append(" : ");
					references.append(grader.getSummaryScore());
					references.append("\n");
					break;
				case Grader.TYPE_SUMMARY_USER:
				case Grader.TYPE_USER:
				default:
					evaluators.append(grader.getUser().getFirstName());
					evaluators.append(" ");
					evaluators.append(grader.getUser().getLastName());
					evaluators.append(" : ");
					evaluators.append(grader.getSummaryScore());
					evaluators.append("\n");
					break;
				}
			}
			if (references.length() > 0) {
				results.append("References:\n");
				results.append(references);
				results.append("\n");
			}
			if (evaluators.length() > 0) {
				results.append("Evaluated By:\n");
				results.append(evaluators);
				results.append("\n");
			}
			HashMap<Question,StringBuffer> quotes = Maps.newHashMap();
			List<Grade> grades = graderService.getAllGradesByRespondantId(respondant.getId());
			for (Grade grade : grades){
				Question ques = grade.getQuestion();
				if (ques.getQuestionType() == 27) {
					if (!quotes.containsKey(ques)) quotes.put(ques, new StringBuffer(ques.getQuestionText()));
					quotes.get(ques).append("\n - \""+grade.getGradeText()+"\"");
				}
			}	
			if (quotes.entrySet().size() > 0) {
				results.append("Comments:\n");
				quotes.entrySet().forEach(set -> results.append(set.getValue().toString()+"\n"));
				results.append(evaluators);
				results.append("\n");
			}		
		}
		return results.toString();

	}
	private String createResultsDetailHTML(Respondant respondant) {		
		StringBuffer results = new StringBuffer();
			
			List<String> warnings = respondantService.getWarningMessages(respondant);
			for (String warning : warnings) {
				results.append("WARNING: ");
				results.append(warning);
				results.append("\n");
			}
			
			CustomProfile customProfile = respondant.getAccount().getCustomProfile();
			results.append("Category: ");
			results.append(customProfile.getName(respondant.getProfileRecommendation()));
			results.append(" (");
			results.append(respondant.getCompositeScore());
			results.append(")\n");
			
			if (respondant.getPredictions().size() > 0) {
				results.append("Summary Scores:\n");		
				for (Prediction prediction : respondant.getPredictions()) {
					PredictionTarget target = predictionModelService.getTargetById(prediction.getTargetId());
					results.append(target.getLabel());
					results.append(": ");
					results.append(String.format("%.2f", prediction.getPredictionScore()));
					results.append("\n");
				}
			}
			
			List<RespondantScore> scores = new ArrayList<RespondantScore>( respondant.getRespondantScores());		
			scores.sort(new Comparator<RespondantScore>() {
				public int compare (RespondantScore a, RespondantScore b) {
					Corefactor corefactorA = corefactorService.findCorefactorById(a.getId().getCorefactorId());
					Corefactor corefactorB = corefactorService.findCorefactorById(b.getId().getCorefactorId());
					double aCoeff = 1d;
					double bCoeff = 1d;
					if (corefactorA.getDefaultCoefficient() != null) aCoeff = Math.abs(corefactorA.getDefaultCoefficient());
					if (corefactorB.getDefaultCoefficient() != null) bCoeff = Math.abs(corefactorB.getDefaultCoefficient());
					// first sort by coefficient - descending
					if (aCoeff != bCoeff) return (int)(bCoeff - aCoeff);
					// otherwise just sort by name
					return corefactorA.getName().compareTo(corefactorB.getName());
				}
			});
			results.append("Summary Scores:\n");		
			for (RespondantScore score : scores) {
				Corefactor cf = corefactorService.findCorefactorById(score.getId().getCorefactorId());
				results.append(cf.getName());
				results.append(" : ");
				
//				this is awful but, for now, always format as two digit leading zero
//				if ((int) score.getValue().doubleValue() == score.getValue()) {
					results.append(String.format("%02d",(int) score.getValue().doubleValue()));
//				} else {
//					notes.append(String.format("%.1f",score.getValue()));
//				}
				results.append("\n");
			}

			Set<RespondantNVP> customQuestions = respondantService.getDisplayNVPsForRespondant(respondant.getId());
			if (!customQuestions.isEmpty()) {
				results.append("Candidate Questions:\n");
				for (RespondantNVP nvp : customQuestions) {
					results.append(nvp.getName());
					results.append(" : ");
					results.append(nvp.getValue());
					results.append("\n");
				}
			}
			
			List<Grader> graders = graderService.getGradersByRespondantId(respondant.getId());
			if (!graders.isEmpty()) {
				StringBuffer references = new StringBuffer();
				StringBuffer evaluators = new StringBuffer();
				for (Grader grader : graders) {
					switch (grader.getType()) {
					case Grader.TYPE_PERSON:
						references.append(grader.getPerson().getFirstName());
						references.append(" ");
						references.append(grader.getPerson().getLastName());
						if ((null != grader.getRelationship()) && (!grader.getRelationship().isEmpty()))
						  references.append(" ("+grader.getRelationship()+")");
						references.append(" : ");
						references.append(grader.getSummaryScore());
						references.append("\n");
						break;
					case Grader.TYPE_SUMMARY_USER:
					case Grader.TYPE_USER:
					default:
						evaluators.append(grader.getUser().getFirstName());
						evaluators.append(" ");
						evaluators.append(grader.getUser().getLastName());
						evaluators.append(" : ");
						evaluators.append(grader.getSummaryScore());
						evaluators.append("\n");
						break;
					}
				}
				if (references.length() > 0) {
					results.append("References:\n");
					results.append(references);
				}
				if (evaluators.length() > 0) {
					results.append("Evaluated By:\n");
					results.append(evaluators);
				}
			}
			
		return "<pre>" + results.toString() + "</pre>";
	}
	
	
	
}