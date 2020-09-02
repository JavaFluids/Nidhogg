package lu.nidhogg.dtos;

import lombok.Data;
import lu.nidhogg.models.Profile;
import lu.nidhogg.models.User;

@Data
public class RefreshResponseDTO {

	private User user;
	
	private String accessToken;

	private String clientToken;
	
	private Profile selectedProfile;

}
