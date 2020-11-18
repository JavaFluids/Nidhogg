package lu.nidhogg.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lu.nidhogg.dtos.AuthenticateRequestDTO;
import lu.nidhogg.dtos.AuthenticateResponseDTO;
import lu.nidhogg.dtos.InvalidateRequestDTO;
import lu.nidhogg.dtos.RefreshRequestDTO;
import lu.nidhogg.dtos.RefreshResponseDTO;
import lu.nidhogg.dtos.SignoutRequestDTO;
import lu.nidhogg.dtos.ValidateRequestDTO;
import lu.nidhogg.models.Agent;
import lu.nidhogg.services.AuthenticationService;
import lu.nidhogg.utils.UuidUtil;

@RestController
public class AuthenticationController extends BaseController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/authenticate")
	@ResponseStatus(value = HttpStatus.OK)
	public AuthenticateResponseDTO authenticate(@RequestBody @Valid AuthenticateRequestDTO dto) {
		dto.setAgent(Optional.ofNullable(dto.getAgent()).orElse(Agent.DEFAULT));
		dto.setClientToken(Optional.ofNullable(dto.getClientToken()).orElse(UuidUtil.withoutDashUUID(UUID.randomUUID())));
		return authenticationService.authenticate(dto);
	}

	@PostMapping("/refresh")
	@ResponseStatus(value = HttpStatus.OK)
	public RefreshResponseDTO refresh(@RequestBody @Valid RefreshRequestDTO dto) {
		return authenticationService.refresh(dto);
	}

	@PostMapping("/validate")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void validate(@RequestBody @Valid ValidateRequestDTO dto) {
		authenticationService.validate(dto);
	}

	@PostMapping("/signout")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void signout(@RequestBody @Valid SignoutRequestDTO dto) {
		authenticationService.signout(dto);
	}

	@PostMapping("/invalidate")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void invalidate(@RequestBody @Valid InvalidateRequestDTO dto) {
		authenticationService.invalidate(dto);
	}
	

}
