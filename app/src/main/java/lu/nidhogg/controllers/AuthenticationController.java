package lu.nidhogg.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lu.nidhogg.dtos.AuthenticateRequestDTO;
import lu.nidhogg.dtos.InvalidateRequestDTO;
import lu.nidhogg.dtos.RefreshRequestDTO;
import lu.nidhogg.dtos.SignoutRequestDTO;
import lu.nidhogg.dtos.ValidateRequestDTO;
import lu.nidhogg.services.AuthenticationService;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/authenticate")
	public void authenticate(@RequestBody @Valid AuthenticateRequestDTO dto) {
		authenticationService.authenticate(dto);
	}

	@PostMapping("/refresh")
	public void refresh(@RequestBody @Valid RefreshRequestDTO dto) {
		authenticationService.refresh(dto);
	}

	@PostMapping("/validate")
	public void validate(@RequestBody @Valid ValidateRequestDTO dto) {
		authenticationService.validate(dto);
	}

	@PostMapping("/signout")
	public void signout(@RequestBody @Valid SignoutRequestDTO dto) {
		authenticationService.signout(dto);
	}

	@PostMapping("/invalidate")
	public void invalidate(@RequestBody @Valid InvalidateRequestDTO dto) {
		authenticationService.invalidate(dto);
	}

}
