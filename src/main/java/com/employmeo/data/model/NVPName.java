package com.employmeo.data.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
	
@Entity
@Table(name = "nvp_names")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class NVPName implements Serializable{

	@Transient
	private static final long serialVersionUID = -217661139819877368L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nvp_name_id")
	private Long id;
	
	@Column(name = "nvp_name")
	private String name;

}
