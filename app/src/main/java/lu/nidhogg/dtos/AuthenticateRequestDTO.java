package lu.nidhogg.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lu.nidhogg.models.Agent;

@Data
public class AuthenticateRequestDTO {

	private Agent agent;

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	private boolean requestUser;
	
}
