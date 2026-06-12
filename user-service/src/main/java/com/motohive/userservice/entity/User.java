package com.motohive.userservice.entity;

import com.motohive.userservice.enums.Gender;
import com.motohive.userservice.enums.Role;
import com.motohive.userservice.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;
	
	@Column(nullable = false, unique = true, length = 150)
	private String email;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Column(unique = true, length = 15)
	private String phone;
	
	@Column(name = "profile_photo_url", length = 500)
	private String profilePhotoUrl;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Gender gender;
	
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 25)
	@Builder.Default
	private UserStatus status = UserStatus.ACTIVE;
	// ── Hybrid JWT refresh token ──────────────────────
	@Column(name = "refresh_token", length = 500)
	private String refreshToken;
	
	@Column(name = "refresh_token_expiry")
	private LocalDateTime refreshTokenExpiry;
	// ── Roles (stored in user_roles table) ───────────
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "role", length = 10)
	@Builder.Default
	private Set<Role> roles = new HashSet<>();
	// ── Relationships ─────────────────────────────────
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<UserAddress> addresses = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<VehicleAppointment> appointments = new ArrayList<>();
	// ── Audit ─────────────────────────────────────────
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
}