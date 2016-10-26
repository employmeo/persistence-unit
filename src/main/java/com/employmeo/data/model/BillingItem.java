package com.employmeo.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "billing_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingItem implements Serializable {

	@Transient
	private static final long serialVersionUID = -8197029455671270468L;

	@Id
	@Column(name = "billing_item_id")
	private Long id;

	@Column(name = "billing_item_amount")
	private Double amount;

	@Column(name = "billing_item_date")
	private Timestamp billingItemDate;

	@Column(name = "billing_item_description")
	private String description;

	@Column(name = "billing_item_reference")
	private Long billingItemReference;

	@Column(name = "billing_item_status")
	private Integer billingItemStatus;

	// bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name = "billing_item_account_id")
	private Account account;


}
