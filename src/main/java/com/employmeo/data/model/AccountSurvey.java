package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

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

	@Column(name = "as_thankyou_text")
	private String thankyouText;

	@Column(name = "as_redirect_page")
	private String redirectPage;

	@Column(name = "as_status")
	private Integer accountSurveyStatus;

	@ManyToOne
	@JoinColumn(name = "as_survey_id", insertable = false, updatable = false)
	private Survey survey;

	@Column(name = "as_survey_id")
	private Long surveyId;

}
