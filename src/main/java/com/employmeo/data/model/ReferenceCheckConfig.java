package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reference_check_configs")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ReferenceCheckConfig implements Serializable {	
	
	@Transient
	private static final long serialVersionUID = 3701505544655449159L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "rcc_id")
		private Long id;

		@Column(name = "rcc_invite_template")
		private String inviteTemplate; 
		
		@Column(name = "rcc_preamble")
		private String preamble; 
				  
		@Column(name = "rcc_min_references")
		private Integer minReferences;
						  
		@Column(name = "rcc_redirect_page")
		private String redirectPage;
		
		@Column(name = "rcc_allow_anonymous")
		private Boolean allowAnonymous = Boolean.FALSE;
		
}