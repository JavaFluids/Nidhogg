package lu.nidhogg.dtos;

import lombok.Data;
import lu.nidhogg.models.Profile;

@Data
public class RefreshRequestDTO {

	private String accessToken;

	private String clientToken;

	private Profile selectedProfile;

	private boolean requestUser;

}
