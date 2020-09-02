package lu.nidhogg.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "Profile")
@Data
public class ProfileEntity {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private long createdAt;

	@Column(nullable = false)
	private boolean legacyProfile;

	@Column(nullable = false)
	private boolean suspended;

	@Column(nullable = false)
	private boolean paid;

	@Column(nullable = false)
	private boolean migrated;

	@Column(nullable = false)
	private boolean legacy;

}
