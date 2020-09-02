package lu.nidhogg.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.gson.io.GsonSerializer;

@Component
public class JwtUtil {

	@Autowired
	private KeyStoreUtil keystoreUtil;

	public String generateAccessToken() throws Exception {
		String jws = Jwts.builder().serializeToJsonWith(new GsonSerializer(new Gson())).setSubject("Bob").signWith(keystoreUtil.getAuthenticationKey()).compact();
		return jws;
	}

}
