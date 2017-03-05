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
@Table(name = "scoring_scales")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ScoringScale implements Serializable {
	
	@Transient  
	private static final long serialVersionUID = -3982888825348555354L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "scoring_scale_id")
		private Long id;

		@Column(name = "scale_a_low")
		private Double scaleALow = 90.0d;
		
		@Column(name = "scale_b_low")
		private Double scaleBLow = 65.0d;
		
		@Column(name = "scale_c_low")
		private Double scaleCLow = 40.0d;
		
		public String getProfile(Double compositeScore) {
			if (compositeScore < scaleCLow) {
				return ProfileDefaults.PROFILE_D;
			} else if (compositeScore < scaleBLow) {
				return ProfileDefaults.PROFILE_C;
			} else if (compositeScore < scaleALow) {
				return ProfileDefaults.PROFILE_B;
			} else {
				return ProfileDefaults.PROFILE_A;
			}
		}
}