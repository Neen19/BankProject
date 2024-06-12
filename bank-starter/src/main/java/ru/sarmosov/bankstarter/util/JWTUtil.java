package ru.sarmosov.bankstarter.util;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;


import com.auth0.jwt.JWT;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.exception.TokenVerificationException;
import ru.sarmosov.bankstarter.properties.BankStarterProperties;

@RequiredArgsConstructor
public class JWTUtil {

    private final BankStarterProperties bankStarterProperties;

    @Logging(value = "Получение тела токена")
    public String trimToken(String token) {
        return token.replace("Bearer ", "");
    }

    @Logging(value = "Верификация токена ")
    public CustomerDTO verifyTokenAndRetrievePhoneNumber(String token) throws IllegalArgumentException {

        DecodedJWT jwt = null;

        try {

            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(bankStarterProperties.getSECRET()))
                    .withSubject(bankStarterProperties.getCUSTOMER_SUBJECT())
                    .withIssuer(bankStarterProperties.getISSUER())
                    .build();

            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new TokenVerificationException("invalid token");
        }

        return new CustomerDTO(
                jwt.getClaim(bankStarterProperties.getID_CLAIM()).asLong(),
                jwt.getClaim(bankStarterProperties.getBANK_ACCOUNT_ID_CLAIM()).asLong(),
                jwt.getClaim(bankStarterProperties.getEMAIL_CLAIM()).asString()
        );
    }

}
