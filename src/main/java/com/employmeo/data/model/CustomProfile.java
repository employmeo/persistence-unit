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
@Table(name = "custom_profiles")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CustomProfile implements Serializable {	
	
	@Transient  
	private static final long serialVersionUID = 2651429433861495454L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "custom_profile_id")
		private Long id;

		@Column(name = "custom_profile_a_name")
		private String aName = ProfileDefaults.aName;
		@Column(name = "custom_profile_a_icon")
		private String aIcon = ProfileDefaults.aIcon;
		@Column(name = "custom_profile_a_class")
		private String aClass = ProfileDefaults.aClass;
		
		@Column(name = "custom_profile_b_name")
		private String bName = ProfileDefaults.bName;
		@Column(name = "custom_profile_b_icon")
		private String bIcon = ProfileDefaults.bIcon;
		@Column(name = "custom_profile_b_class")
		private String bClass = ProfileDefaults.bClass;
		
		@Column(name = "custom_profile_c_name")
		private String cName = ProfileDefaults.cName;
		@Column(name = "custom_profile_c_icon")
		private String cIcon = ProfileDefaults.cIcon;
		@Column(name = "custom_profile_c_class")
		private String cClass = ProfileDefaults.cClass;
		
		@Column(name = "custom_profile_d_name")
		private String dName = ProfileDefaults.dName;
		@Column(name = "custom_profile_d_icon")
		private String dIcon = ProfileDefaults.dIcon;
		@Column(name = "custom_profile_d_class")
		private String dClass = ProfileDefaults.dClass;


		public String getName(String profile){
			switch (profile) {
				case ProfileDefaults.PROFILE_A:	return getAName();
				case ProfileDefaults.PROFILE_B:	return getBName();
				case ProfileDefaults.PROFILE_C:	return getCName();
				case ProfileDefaults.PROFILE_D:	return getDName();
				default: return ProfileDefaults.uName;
			}
		}

		public String getIcon(String profile){
			switch (profile) {
				case ProfileDefaults.PROFILE_A:	return getAIcon();
				case ProfileDefaults.PROFILE_B:	return getBIcon();
				case ProfileDefaults.PROFILE_C:	return getCIcon();
				case ProfileDefaults.PROFILE_D:	return getDIcon();
				default: return ProfileDefaults.uIcon;
			}
		}
		
		public String getCssClass(String profile){
			switch (profile) {
				case ProfileDefaults.PROFILE_A:	return getAClass();
				case ProfileDefaults.PROFILE_B:	return getBClass();
				case ProfileDefaults.PROFILE_C:	return getCClass();
				case ProfileDefaults.PROFILE_D:	return getDClass();
				default: return ProfileDefaults.uClass;
			}
		}
		
		public String getHighlight(String profile){
			switch (profile) {
				case ProfileDefaults.PROFILE_A:	return ProfileDefaults.aHighlight;
				case ProfileDefaults.PROFILE_B:	return ProfileDefaults.bHighlight;
				case ProfileDefaults.PROFILE_C:	return ProfileDefaults.cHighlight;
				case ProfileDefaults.PROFILE_D:	return ProfileDefaults.dHighlight;
				default: return ProfileDefaults.uHighlight;
			}
		}
		
		public String getOverlay(String profile){
			switch (profile) {
			case ProfileDefaults.PROFILE_A:	return ProfileDefaults.aOverlay;
			case ProfileDefaults.PROFILE_B:	return ProfileDefaults.bOverlay;
			case ProfileDefaults.PROFILE_C:	return ProfileDefaults.cOverlay;
			case ProfileDefaults.PROFILE_D:	return ProfileDefaults.dOverlay;
			default: return ProfileDefaults.uOverlay;
			}
		}
		
		public String getColor(String profile){
			switch (profile) {
			case ProfileDefaults.PROFILE_A:	return ProfileDefaults.aColor;
			case ProfileDefaults.PROFILE_B:	return ProfileDefaults.bColor;
			case ProfileDefaults.PROFILE_C:	return ProfileDefaults.cColor;
			case ProfileDefaults.PROFILE_D:	return ProfileDefaults.dColor;
			default: return ProfileDefaults.uColor;
			}
		}

}