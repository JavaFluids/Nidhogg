package lu.nidhogg.entities;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;
import lu.nidhogg.utils.UuidUtil;

@Entity(name = "User")
@Getter
@Setter
public class UserEntity {

	@Id
	@GeneratedValue(generator = UuidUtil.UUID)
	@GenericGenerator(name = UuidUtil.UUID, strategy = UuidUtil.GEN_CLASS)
	@Column(updatable = false, nullable = false)
	@ColumnDefault(UuidUtil.GEN_FUNCTION)
	@Type(type = UuidUtil.UUID_TYPE)
	private UUID				id;

	@Column(nullable = false)
	private String				email;

	@Column(nullable = false)
	private String				username;

	@Column(nullable = false)
	private String				password;

	@Column(nullable = false)
	private String				registerIp;

	@Column(nullable = false)
	private String				migratedFrom;

	@Column(nullable = false)
	private Date				migratedAt;

	@Column(nullable = false)
	private Date				registeredAt;

	@Column(nullable = false)
	private Date				passwordChangedAt;

	@Column(nullable = false)
	private Date				dateOfBirth;

	@Column(nullable = false)
	private boolean				suspended;

	@Column(nullable = false)
	private boolean				blocked;

	@Column(nullable = false)
	private boolean				secured;

	@Column(nullable = false)
	private boolean				migrated;

	@Column(nullable = false)
	private boolean				emailVerified;

	@Column(nullable = false)
	private boolean				legacyUser;

	@Column(nullable = false)
	private boolean				verifiedByParent;

	// @Column(nullable= false)
	// private Map<String, String> properties;

	@OneToMany(mappedBy = "user")
	private Set<ProfileEntity>	profiles;

}
