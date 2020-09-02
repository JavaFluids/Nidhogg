package lu.nidhogg.dtos;

import java.util.List;

import lombok.Data;
import lu.nidhogg.models.Profile;
import lu.nidhogg.models.User;

@Data
public class AuthenticateResponseDTO {

	private User user;

	private String accessToken;

	private String clientToken;

	private List<Profile> availableProfiles;

	private Profile selectedProfile;

}
