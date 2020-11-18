package lu.nidhogg.utils;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lu.nidhogg.entities.UserEntity;

@Component
public class JwtUtil {

	private static final long	TOKEN_EXPIRATION	= 172800000;		// 48H

	public static final String	CLAIM_YGGT			= "yggt";
	public static final String	CLAIM_SEL_PROFILE	= "spr";
	public static final String	CLAIM_ISSUER		= "Nidhogg-Auth";

	@Autowired
	private KeyStoreUtil		keystoreUtil;

	@Autowired
	private ApplicationContext	context;

	public String generateAccessToken(UserEntity user) throws Exception {
		JwtBuilder jwtBuilder = context.getBean(JwtBuilder.class);
		jwtBuilder.setSubject(UuidUtil.withoutDashUUID(user.getId()));
		jwtBuilder.setIssuedAt(new Date());
		jwtBuilder.claim(CLAIM_YGGT, UUID.randomUUID());
		jwtBuilder.claim(CLAIM_SEL_PROFILE, UUID.randomUUID()); // TODO : Selected profile
		jwtBuilder.setIssuer(CLAIM_ISSUER);
		jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION));
		jwtBuilder.signWith(keystoreUtil.getAuthenticationKey());
		return jwtBuilder.compact();
	}

	public Jws<Claims> getClaims(String jws) throws Exception {
		JwtParser parser = Jwts.parserBuilder().setSigningKey(keystoreUtil.getAuthenticationKey()).build();
		
		return parser.parseClaimsJws(jws);
	}

}
