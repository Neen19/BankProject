package ru.sarmosov.bankstarter.util;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;


import com.auth0.jwt.JWT;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.properties.BankStarterProperties;

@RequiredArgsConstructor
public class JWTUtil {

    private final BankStarterProperties bankStarterProperties;

    public String trimToken(String token) {
        return token.replace("Bearer ", "");
    }

    public CustomerDTO verifyTokenAndRetrievePhoneNumber(String token) {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(bankStarterProperties.getSECRET()))
                .withSubject(bankStarterProperties.getCUSTOMER_SUBJECT())
                .withIssuer(bankStarterProperties.getISSUER())
                .build();
        DecodedJWT jwt = verifier.verify(token);


        return new CustomerDTO(
                jwt.getClaim(bankStarterProperties.getID_CLAIM()).asLong(),
                jwt.getClaim(bankStarterProperties.getBANK_ACCOUNT_ID_CLAIM()).asLong(),
                jwt.getClaim(bankStarterProperties.getPHONE_NUMBER_CLAIM()).asString()
        );
    }

}
