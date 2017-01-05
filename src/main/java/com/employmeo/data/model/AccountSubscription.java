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
@Table(name = "account_subscription")
@Data
@NoArgsConstructor
public class AccountSubscription implements Serializable {
	
	@Transient
	private static final long serialVersionUID = -5510994495303827066L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_subscription_id")
	private Long id;

	@Column(name = "account_id")
	private Long accountId ;
	
	@Column(name = "plan_id")
	private Long planId ;	
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "next_installment_date")
	private Date nextInstallmentDate;	
	
	@Column(name = "plan_status")
	private Integer planStatusId;	

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
	
    public PlanStatus getPlanStatus() {
        return PlanStatus.getStatus(this.planStatusId);
    }

    public void setPlanStatus(PlanStatus planStatus) {
        if (planStatus == null) {
            this.planStatusId = null;
        } else {
            this.planStatusId = planStatus.getStatusId();
        }
    }		
}
