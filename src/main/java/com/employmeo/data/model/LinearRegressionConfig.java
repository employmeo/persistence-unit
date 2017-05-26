package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The persistent class for the predictive_model database table.
 *
 */
@Slf4j
@Entity
@Table(name = "linear_regression_config")
@Data
@NoArgsConstructor
public class LinearRegressionConfig implements Serializable {

	@Transient
	private static final long serialVersionUID = -5239863484138193543L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "config_id")
	private Long configId;

	@Column(name = "model_id")
	private Long modelId;

	@Column(name = "corefactor_id")
	private Long corefactorId;

	@Column(name = "coefficient")
	private Double coefficient;

	@Column(name = "significance")
	private Double significance;

	@Column(name = "exponent")
	private Double exponent;

	@Column(name = "config_type")
	private Integer configTypeId;

	@Column(name = "required")
	private Boolean required = Boolean.TRUE;

	@Column(name = "active")
	private Boolean active = Boolean.TRUE;

	@Column(name = "created_date")
	private Date createdDate;

    public LinearRegressionConfigType getConfigType() {
        return LinearRegressionConfigType.getConfigType(this.configTypeId);
    }
    
	@PrePersist
	private void setCreatedDate() {
		log.debug("Generating create date as {}", createdDate);
		createdDate = new Date();
	}
	
    public void setConfigType(LinearRegressionConfigType configType) {
        if (configType == null) {
            this.configTypeId = null;
        } else {
            this.configTypeId = configType.getTypeId();
        }
    }

}