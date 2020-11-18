package lu.nidhogg.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.SneakyThrows;
import lu.nidhogg.dtos.AuthenticateRequestDTO;
import lu.nidhogg.dtos.AuthenticateResponseDTO;
import lu.nidhogg.dtos.InvalidateRequestDTO;
import lu.nidhogg.dtos.RefreshRequestDTO;
import lu.nidhogg.dtos.RefreshResponseDTO;
import lu.nidhogg.dtos.SignoutRequestDTO;
import lu.nidhogg.dtos.ValidateRequestDTO;
import lu.nidhogg.entities.ProfileEntity;
import lu.nidhogg.entities.SessionEntity;
import lu.nidhogg.entities.UserEntity;
import lu.nidhogg.exceptions.EmptyProfileException;
import lu.nidhogg.exceptions.InvalidCredentialsException;
import lu.nidhogg.exceptions.ResourceNotFoundException;
import lu.nidhogg.models.Profile;
import lu.nidhogg.models.User;
import lu.nidhogg.repositories.SessionRepository;
import lu.nidhogg.repositories.UserRepository;
import lu.nidhogg.utils.JwtUtil;

@Service
public class AuthenticationService {

	@Autowired
	private PasswordEncoder		passwordEncoder;

	@Autowired
	private UserRepository		userRepository;

	@Autowired
	private SessionRepository	sessionRepository;

	@Autowired
	private ModelMapper			modelMapper;

	@Autowired
	private JwtUtil				jwtUtil;

	@Transactional
	@SneakyThrows(Exception.class)
	public AuthenticateResponseDTO authenticate(AuthenticateRequestDTO dto) {
		UserEntity user = userRepository.findByUsername(dto.getUsername()).or(() -> userRepository.findByEmail(dto.getUsername())).orElseThrow(InvalidCredentialsException::new);
		boolean authSuccess = passwordEncoder.matches(dto.getPassword(), user.getPassword());
		if (!authSuccess) {
			throw new InvalidCredentialsException();
		}

		Set<ProfileEntity> profiles = user.getProfiles();
		if (profiles.isEmpty()) {
			throw new EmptyProfileException();
		}

		List<Profile> availableProfiles = new ArrayList<>();
		for (ProfileEntity profile : profiles) {
			availableProfiles.add(modelMapper.map(profile, Profile.class));
		}

		String accessToken = jwtUtil.generateAccessToken(user);
		this.createSession(dto.getClientToken(), accessToken);

		AuthenticateResponseDTO response = new AuthenticateResponseDTO();
		response.setAccessToken(accessToken);
		response.setUser(modelMapper.map(user, User.class));
		response.setAvailableProfiles(availableProfiles);
		response.setSelectedProfile(availableProfiles.get(0));
		response.setClientToken(dto.getClientToken());
		return response;
	}

	@Transactional
	@SneakyThrows(Exception.class)
	public RefreshResponseDTO refresh(RefreshRequestDTO dto) {
		SessionEntity session = checkSession(dto.getClientToken(), dto.getAccessToken());
		
		UserEntity user = userRepository.findById(session.getUserId()).orElseThrow(ResourceNotFoundException::new);
		String clientToken = dto.getClientToken();
		String accessToken = jwtUtil.generateAccessToken(user);

		this.createSession(clientToken, accessToken);

		RefreshResponseDTO response = new RefreshResponseDTO();
		response.setAccessToken(accessToken);
		response.setClientToken(clientToken);
		response.setUser(dto.isRequestUser() ? modelMapper.map(user, User.class) : null);
		return response;
	}

	public void validate(ValidateRequestDTO dto) {
		this.checkSession(dto.getClientToken(), dto.getAccessToken());
	}

	@Transactional
	public void signout(SignoutRequestDTO dto) {
		UserEntity user = userRepository.findByUsername(dto.getUsername()).or(() -> userRepository.findByEmail(dto.getUsername())).orElseThrow(InvalidCredentialsException::new);
		boolean authSuccess = passwordEncoder.matches(dto.getPassword(), user.getPassword());
		if (!authSuccess) {
			throw new InvalidCredentialsException();
		}
		sessionRepository.deleteAllByUserId(user.getId());
	}
	
	@Transactional
	@SneakyThrows(Exception.class)
	public void invalidate(InvalidateRequestDTO dto) {
		SessionEntity session = checkSession(dto.getClientToken(), dto.getAccessToken());
		sessionRepository.delete(session);
	}

	@SneakyThrows(Exception.class)
	private void createSession(String clientToken, String accessToken) {
		Jws<Claims> claims = jwtUtil.getClaims(accessToken);
		Claims jwtBody = claims.getBody();
		Date expiration = jwtBody.getExpiration();
		sessionRepository.deleteAllByClientToken(clientToken);
		SessionEntity session = new SessionEntity();
		session.setClientToken(clientToken);
		session.setAccessToken(accessToken);
		session.setExpireAt(expiration);
		sessionRepository.save(session);
	}
	
	@SneakyThrows(Exception.class)
	private SessionEntity checkSession(String clientToken, String accessToken) {
		jwtUtil.getClaims(accessToken); // Parsing JWS make sure signature is correct
		SessionEntity session = sessionRepository.findByClientToken(clientToken).orElseThrow(ResourceNotFoundException::new);
		if (!session.getAccessToken().equals(accessToken)) {
			throw new ResourceNotFoundException();
		}
		return session;
	}

}
