package lu.nidhogg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid credentials : incorrect username and / or password")
public class InvalidCredentialsException extends RuntimeException {

}
