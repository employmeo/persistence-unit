package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "graders")
@Data
@NoArgsConstructor
public class Grader implements Serializable {
	
	public static final int STATUS_NEW = 1;
	public static final int STATUS_REMINDED = 2;
	public static final int STATUS_STARTED = 5;
	public static final int STATUS_COMPLETED = 10;
	public static final int STATUS_IGNORED = 20;
	public static final int TYPE_USER = 1;
	public static final int TYPE_SUMMARY_USER = 2;
	public static final int TYPE_PERSON = 100;
	
	@Transient
	private static final long serialVersionUID = -8654344203277805503L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grader_id")
	private Long id;

	@Column(name = "grader_uuid", insertable = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Convert(disableConversion = true)  // hibernate specific mapping
	@Type(type="pg-uuid") // hibernate specific mapping
	private UUID uuId;
	
	@Column(name = "grader_type", insertable = true, updatable = true)
	private Integer type;
	
	@Column(name = "grader_status", insertable = true, updatable = true)
	private Integer status;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "grader_response_id", insertable = false, updatable = false)
	private Response response;
	
	@Column(name = "grader_response_id")
	private Long responseId;
	
	// bi-directional many-to-one association to Question
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "grader_question_id", insertable=false, updatable=false)
	private Question question;
	
	@Column(name = "grader_question_id")
	private Long questionId;
	
	// bi-directional many-to-one association to Person
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "grader_person_id", insertable=false, updatable=false)
	private Person person;

	@Column(name = "grader_person_id")
	private Long personId;
	
	// bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "grader_user_id", insertable=false, updatable=false)
	private User user;

	@Column(name = "grader_user_id")
	private Long userId;
	
	// bi-directional many-to-one association to Respondant
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "grader_respondant_id", insertable=false, updatable=false)
	private Respondant respondant;
	
	@Column(name = "grader_respondant_id")
	private Long respondantId;
	
	@Column(name = "grader_notes")
	private String notes;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "grader_created_date")
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "grader_modified_date")
	private Date modifiedDate;
	  
	@PrePersist
	void generateUUIDandDate()  {
		if(null == uuId) {
			uuId = UUID.randomUUID();
			log.debug("Generating grader uuId randomly PrePersist as {}", uuId);
		}	
		createdDate = new Date();
	}
	
	@PreUpdate
	void generateModdDate()  {
		modifiedDate = new Date();
	}	
	@JsonProperty("userName")
	public String getUserName() {
		if (this.user == null) return null;
		return this.user.getFirstName() + ' ' + this.user.getLastName();
	}
}
