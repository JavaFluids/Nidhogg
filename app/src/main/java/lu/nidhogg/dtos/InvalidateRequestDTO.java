package lu.nidhogg.dtos;

import lombok.Data;

@Data
public class InvalidateRequestDTO {

	private String accessToken;

	private String clientToken;

}
