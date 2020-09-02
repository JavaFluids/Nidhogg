package lu.nidhogg.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "User")
@Data
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int id;

	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable= false)
	private String registerIp;

	@Column(nullable= false)
	private String migratedFrom;

	@Column(nullable= false)
	private Date migratedAt;

	@Column(nullable= false)
	private Date registeredAt;

	@Column(nullable= false)
	private Date passwordChangedAt;

	@Column(nullable= false)
	private Date dateOfBirth;

	@Column(nullable= false)
	private boolean suspended;

	@Column(nullable= false)
	private boolean blocked;

	@Column(nullable= false)
	private boolean secured;

	@Column(nullable= false)
	private boolean migrated;

	@Column(nullable= false)
	private boolean emailVerified;

	@Column(nullable= false)
	private boolean legacyUser;

	@Column(nullable= false)
	private boolean verifiedByParent;

	//@Column(nullable= false)
	//private Map<String, String> properties;
	
}
