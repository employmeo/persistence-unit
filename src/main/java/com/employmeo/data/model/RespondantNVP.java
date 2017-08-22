package com.employmeo.data.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
	
@Entity
@Table(name = "respondant_nvps")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RespondantNVP implements Serializable{

		@Transient
		private static final long serialVersionUID = -5509821936230426296L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "nvp_id")
		private Long id;

		@Column(name = "nvp_respondant_id")
		private Long respondantId;
		
		@Column(name = "nvp_name")
		private String name;

		@Column(name = "nvp_name_id")
		private Long nameId;

		@Column(name = "nvp_value")
		private String value;
		
		@Column(name = "nvp_use_in_model")
		private Boolean useInModel = Boolean.TRUE;

		@Column(name = "nvp_show_in_portal")
		private Boolean showInPortal = Boolean.FALSE;


}
