package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "api_transactions")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ApiTransaction implements Serializable {

	@Transient
	private static final long serialVersionUID = 5929284508395534715L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "api_key")
	private Long id;

	@Column(name = "api_name")
	private String apiName;

	@Column(name = "api_object_id")
	private Long objectId;

	@Column(name = "api_reference_id")
	private String referenceId;

	@Column(name = "api_created_date")
	private Date createdDate;

	@PrePersist
	public void setCreatedDate() {
		this.createdDate = new Date();
	}
}
