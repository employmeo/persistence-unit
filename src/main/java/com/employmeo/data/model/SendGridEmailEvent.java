package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.talytica.common.json.UNIXTimeDeserializer;

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
	
	
	public static final Date LONG_AGO = new Date(1337969592);
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
	
	@JsonProperty("person_id")
	@Column(name = "person_id")
    public Long personId;
	
	@Column(name = "sg_message_id")
    public String sg_message_id;
	
	@Column(name = "useragent")
    public String useragent;
	
	@Column(name = "event")
    public String event;
	
	@Column(name = "email")
    public String email;
	
	@JsonProperty("timestamp")
	@JsonDeserialize(using = UNIXTimeDeserializer.class)
	@Column(name = "time_stamp")
	public Date timeStamp;
	
	@Column(name = "asm_group_id")
    public String asm_group_id;
	
	@Column(name = "url")
    public String url;
    
	@JsonProperty("smtp-id")
	@Column(name = "smtp_id")
    public String smtpId;
	
	@PrePersist
	public void updateTimeStamp() {
		if (this.timeStamp.before(LONG_AGO)) {
			this.timeStamp = new Date();
		}
	}
}
