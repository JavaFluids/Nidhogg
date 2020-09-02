package lu.nidhogg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lu.nidhogg.dtos.AuthenticateRequestDTO;
import lu.nidhogg.dtos.AuthenticateResponseDTO;
import lu.nidhogg.dtos.InvalidateRequestDTO;
import lu.nidhogg.dtos.RefreshRequestDTO;
import lu.nidhogg.dtos.SignoutRequestDTO;
import lu.nidhogg.dtos.ValidateRequestDTO;
import lu.nidhogg.entities.UserEntity;
import lu.nidhogg.exceptions.InvalidCredentialsException;
import lu.nidhogg.repositories.UserRepository;
import lu.nidhogg.utils.JwtUtil;

@Service
public class AuthenticationService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;

	@SneakyThrows(Exception.class)
	public void authenticate(AuthenticateRequestDTO dto) {
		UserEntity user = userRepository.findByUsername(dto.getUsername()).or(() -> userRepository.findByEmail(dto.getUsername())).orElseThrow(InvalidCredentialsException::new);	
		boolean authSuccess = passwordEncoder.matches(dto.getPassword(), user.getPassword());
		if(!authSuccess) {
			throw new InvalidCredentialsException(); 
		}
		AuthenticateResponseDTO response = new AuthenticateResponseDTO();
		response.setAccessToken(jwtUtil.generateAccessToken());
		System.out.println(response.getAccessToken());
	}

	public void refresh(RefreshRequestDTO dto) {
	}

	public void validate(ValidateRequestDTO dto) {
	}

	public void signout(SignoutRequestDTO dto) {
	}

	public void invalidate(InvalidateRequestDTO dto) {
	}

}
