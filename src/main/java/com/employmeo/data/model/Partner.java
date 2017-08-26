package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partners")
@Data
@NoArgsConstructor
public class Partner implements Serializable {

	@Transient
	private static final long serialVersionUID = 2666990578569439519L;

	@Id
	@Column(name = "partner_id")
	private Long id;

	@Column(name = "partner_name")
	private String partnerName;

	@Column(name = "partner_login")
	private String login;

	@Column(name = "partner_password")
	private String password;

	@Column(name = "partner_prefix")
	private String prefix;
	
	@Column(name = "partner_api_login")
	private String apiLogin;

	@Column(name = "partner_api_pass")
	private String apiPass;

	@Column(name = "partner_api_key")
	private String apiKey;

}
