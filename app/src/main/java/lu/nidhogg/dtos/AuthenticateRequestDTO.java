package lu.nidhogg.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lu.nidhogg.models.Agent;

@Data
public class AuthenticateRequestDTO {

	private Agent	agent;

	@NotBlank
	private String	username;

	@NotBlank
	private String	password;

	@Pattern(regexp = "^[a-z0-9]{32}", message = "Length must be 32")
	private String	clientToken;

	private boolean	requestUser;

}
