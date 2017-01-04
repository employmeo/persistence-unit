package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscription_plan")
@Data
@NoArgsConstructor
public class SubscriptionPlan implements Serializable {
	
	@Transient
	private static final long serialVersionUID = -3620924830853928073L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscription_plan_id")
	private Long id;

	@Column(name = "plan_name")
	private String name ;
	
	@Column(name = "plan_reference")
	private String reference ;	
	
	@Column(name = "label")
	private String label ;	

	@Column(name = "description")
	private String description;

	@Column(name = "listable")
	private Boolean listable;
	
	@Column(name = "term_value")
	private Integer termValue;

	@Column(name = "term_unit")
	private Integer termUnit;
	
	@Column(name = "total_cost")
	private Double totalCost;
	
	@Column(name = "installment_value")
	private Double installmentValue;	
	
	@Column(name = "num_installments")
	private Double installmentCount;
	
	@Column(name = "installment_frequency")
	private Double installmentFrequency;
	
	@Column(name = "active_assessments_limit")
	private Long allowableAssessments;	

	@Column(name = "scored_respondants_monthly_limit")
	private Long scoredRespondantsMonthlyLimit;	
	
	@Column(name = "scored_respondants_total_limit")
	private Long scoredRespondantsTotalLimit;	

	@Column(name = "total_benchmarks_limit")
	private Long allowableBenchmarks;	
	
	@Column(name = "active")
	private Boolean active;

	@Column(name = "created_date")
	private Date createdDate;	
}
