package com.employmeo.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"account"})
@ToString(exclude={"account"})
public class User implements Serializable {

	@Transient
	private static final long serialVersionUID = 8267893893548964337L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(name = "USER_AVATAR_URL")
	private String avatarUrl;

	@Column(name = "USER_EMAIL")
	private String email;

	@Column(name = "USER_FNAME")
	private String firstName;

	@Column(name = "USER_LNAME")
	private String lastName;

	@Column(name = "USER_LOCALE")
	private String userLocale;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "USER_PASSWORD")
	private String password;

	@Column(name = "USER_STATUS")
	private Integer userStatus;

	@Column(name = "USER_TYPE")
	private Integer userType;

	// bi-directional many-to-one association to Account
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "USER_ACCOUNT_ID", updatable = false, insertable = false)
	private Account account;

	// direct access to account id
	@Column(name = "USER_ACCOUNT_ID", updatable = false, insertable = true)
	private Long userAccountId;

}