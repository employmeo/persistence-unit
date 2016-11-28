package com.employmeo.data.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "account_surveys")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"account","survey"})
@ToString(exclude={"account","survey"})
public class AccountSurvey implements Serializable {

	@Transient
	private static final long serialVersionUID = 7399707522994002079L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "as_id")
	private Long id;

	@Column(name = "as_uuid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Convert(disableConversion = true)  // hibernate specific mapping
	@Type(type="pg-uuid") // hibernate specific mapping
	private UUID uuId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "as_account_id", insertable = false, updatable = false)
	private Account account;

	@Column(name = "as_account_id")
	private Long accountId;

	@Column(name = "as_display_name")
	private String displayName;

	@Column(name = "as_price")
	private Double price;

	@Column(name = "as_preamble_text")
	private String preambleText;

	@Column(name = "as_preamble_media")
	private String preambleMedia;

	@Column(name = "as_thankyou_text")
	private String thankyouText;

	@Column(name = "as_thankyou_media")
	private String thankyouMedia;

	@Column(name = "as_redirect_page")
	private String redirectPage;

	@Column(name = "as_status")
	private Integer accountSurveyStatus;
	
	@Column(name = "as_phone_number")
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "as_survey_id", insertable = false, updatable = false)
	private Survey survey;

	@Column(name = "as_survey_id")
	private Long surveyId;
	
	@PrePersist
	void generateUUID() {
		if(null == uuId) {
			uuId = UUID.randomUUID();
			log.debug("Generating account survey uuId randomly PrePersist as {}", uuId);
		}
	}
}
