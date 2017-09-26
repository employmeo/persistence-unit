package com.employmeo.data.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "account_surveys")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"account","survey","benchmark"})
@ToString(exclude={"account","survey","benchmark"})
public class AccountSurvey implements Serializable {

	@Transient
	private static final long serialVersionUID = 7399707522994002079L;

	public static final int TYPE_APPLICANT = 100;
	public static final int TYPE_BENCHMARK = 200;
	public static final int TYPE_STAGE2 = 300;
	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_DISABLED = 99;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "as_id")
	private Long id;

	@Column(name = "as_uuid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Convert(disableConversion = true)  // hibernate specific mapping
	@Type(type="pg-uuid") // hibernate specific mapping
	private UUID uuId;

	@ManyToOne
	@JoinColumn(name = "as_account_id", insertable = false, updatable = false)
	private Account account;

	@Column(name = "as_account_id")
	private Long accountId;

	@Column(name = "as_display_name")
	private String overRideDisplayName;

	@Column(name = "as_price")
	private Double price;

	@Column(name = "as_preamble_text")
	private String preambleText;

	@Column(name = "as_preamble_media")
	private String preambleMedia;

	@Column(name = "as_thankyou_text")
	private String thankyouText;
	
	@Column(name = "as_permalink")
	private String permalink;

	@Column(name = "as_static_link_view")
	private String staticLinkView;

	@Column(name = "as_invite_template_id")
	private String inviteTemplateId;
	
	@Column(name = "as_thankyou_media")
	private String thankyouMedia;

	@Column(name = "as_redirect_page")
	private String overRideRedirectPage;

	@Column(name = "as_status")
	private Integer accountSurveyStatus = STATUS_ACTIVE;
	
	@Column(name = "as_type")
	private Integer type = TYPE_APPLICANT;

	@Column(name = "as_phone_number")
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "as_survey_id", insertable = false, updatable = false)
	private Survey survey;

	@Column(name = "as_survey_id")
	private Long surveyId;
	
	@JsonBackReference(value="as-benchmark")
	@ManyToOne
	@JoinColumn(name = "as_benchmark_id", insertable = false, updatable = false)
	private Benchmark benchmark;

	@Column(name = "as_benchmark_id")
	private Long benchmarkId;
	
	@Column(name = "as_min_graders")
	private Integer minGraders = 0;
	
	@PrePersist
	void generateUUID() {
		if(null == uuId) {
			uuId = UUID.randomUUID();
			log.debug("Generating account survey uuId randomly PrePersist as {}", uuId);
		}
	}
			
	@JsonProperty("displayName")
	public String getDisplayName(){
		if (null != this.overRideDisplayName) return this.overRideDisplayName;
		return this.survey.getName();
	}
	
	@JsonProperty("redirectPage")
	public String getRedirectPage(){
		if (null != this.overRideRedirectPage) return this.overRideRedirectPage;
		return this.account.getDefaultRedirect();
	}

}
