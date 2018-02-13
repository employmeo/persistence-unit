package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "sendgrid_event")
@NoArgsConstructor
@EqualsAndHashCode
public class SendGridEmailEvent implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 361536473629795725L;

	@Id
	@Basic(optional = false)
	@Column(name = "sg_event_id")
    public String sg_event_id;
	
	@Column(name = "ip")
    public String ip;
	
	@Column(name = "sg_user_id")
    public Long sg_user_id;
	
	@Column(name = "person_id")
    public Long person_id;
	
	@Column(name = "sg_message_id")
    public String sg_message_id;
	
	@Column(name = "useragent")
    public String useragent;
	
	@Column(name = "event")
    public String event;
	
	@Column(name = "email")
    public String email;
	
	@Column(name = "time_stamp")
    public Long timestamp;
	
	@Column(name = "asm_group_id")
    public String asm_group_id;
	
	@Column(name = "url")
    public String url;
    
    @SerializedName("smtp-id")
    @Column(name = "smtp_id")
    public String smtpId;
}
