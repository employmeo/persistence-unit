package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grader_configs")
@Data
@NoArgsConstructor
public class GraderConfig implements Serializable {
	
	@Transient
	private static final long serialVersionUID = -458053978061052746L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grader_config_id")
	private Long id;  
	  
	// bi-directional many-to-one association to Person
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "grader_config_asid", insertable = false, updatable = false)
	private AccountSurvey accountSurvey;
	
	@Column(name = "grader_config_asid", insertable = true, updatable = true)
	private Long asid;
	
	// bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "grader_config_user_id", insertable=false, updatable=false)
	private User user;

	@Column(name = "grader_config_user_id")
	private Long userId;
	
	@Column(name = "grader_location_limitation_id")
	private Long locationLimitationId;
	
	@Column(name = "grader_easyignore")
	private Boolean easyignore = true;

	@Column(name = "grader_summarize")
	private Boolean summarize = true;
	
	@Column(name = "grader_notify")
	private Boolean notify = true;
	
	@JsonProperty("userName")
	public String getUserName() {
		if (this.user == null) return null;
		return this.user.getFirstName() + ' ' + this.user.getLastName();
	}
}
