package lu.nidhogg.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;
import lu.nidhogg.utils.UuidUtil;

@Entity(name = "Profile")
@Getter
@Setter
public class ProfileEntity {

	@Id
	@GeneratedValue(generator = UuidUtil.UUID)
	@GenericGenerator(name = UuidUtil.UUID, strategy = UuidUtil.GEN_CLASS)
	@Column(updatable = false, nullable = false)
	@ColumnDefault(UuidUtil.GEN_FUNCTION)
	@Type(type = UuidUtil.UUID_TYPE)
	private UUID		id;

	@Column(nullable = false)
	private String		name;

	@Column(nullable = false)
	@Type(type = UuidUtil.UUID_TYPE)
	private UUID		userId;

	@Column(nullable = false)
	private long		createdAt;

	@Column(nullable = false)
	private boolean		legacyProfile;

	@Column(nullable = false)
	private boolean		suspended;

	@Column(nullable = false)
	private boolean		paid;

	@Column(nullable = false)
	private boolean		migrated;

	@Column(nullable = false)
	private boolean		legacy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
	private UserEntity	user;

}
