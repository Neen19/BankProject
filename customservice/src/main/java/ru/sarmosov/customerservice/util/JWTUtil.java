package ru.sarmosov.customerservice.util;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

import com.auth0.jwt.JWT;

@Log4j2
@Component
public class JWTUtil {

    private final String PHONE_NUMBER_CLAIM = "phoneNumber";
    private final String CUSTOMER_SUBJECT = "customerSubject";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    public String generateToken(String phoneNumber) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusHours(1).toInstant());
        return JWT.create()
                .withSubject(CUSTOMER_SUBJECT)
                .withClaim(PHONE_NUMBER_CLAIM, phoneNumber)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String verifyTokenAndRetrievePhoneNumber(String token) throws JWTVerificationException {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(CUSTOMER_SUBJECT)
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(PHONE_NUMBER_CLAIM).asString();
    }

}
