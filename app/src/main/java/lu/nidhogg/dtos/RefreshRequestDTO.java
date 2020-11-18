package lu.nidhogg.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class RefreshRequestDTO {

	@NotBlank
	private String	accessToken;

	@NotBlank
	@Pattern(regexp = "^[a-z0-9]{32}", message = "Length must be 32")
	private String	clientToken;

	// Not implemented by Mojang yet
	// private Profile selectedProfile;

	private boolean	requestUser;

}
