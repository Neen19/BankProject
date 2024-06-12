package ru.sarmosov.customerservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.bankstarter.dto.AuthDTO;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.dto.TokenResponseDTO;
import ru.sarmosov.customerservice.entity.CustomerEntity;
import ru.sarmosov.customerservice.security.CustomerDetails;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class JWTService {

    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsService customerDetailsService;
    private final ModelMapper modelMapper;

    @Value("${jwt.customer-subject}")
    private String CUSTOMER_SUBJECT;

    @Value("${jwt.email-claim}")
    private String EMAIL_CLAIM;

    @Value("${jwt.bank-account-id-claim}")
    private String BANK_ACCOUNT_ID_CLAIM;

    @Value("${jwt.id-claim}")
    private String ID_CLAIM;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.issuer}")
    private String ISSUER;



    @Logging(value = "Проверка наличия пользователя и попытка загрузить токен")
    public TokenResponseDTO getCustomerToken(AuthDTO authDTO) throws BadCredentialsException {

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getEmail(),
                        authDTO.getPassword());

        authenticationManager.authenticate(authInputToken);

        CustomerDetails customerDetails = (CustomerDetails) customerDetailsService.loadUserByUsername(authDTO.getEmail());

        CustomerDTO dto = modelMapper.map(customerDetails.getCustomerEntity(), CustomerDTO.class);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(generateToken(dto));
        return tokenResponseDTO;
    }


    @Logging(value = "Генерация токена")
    public String generateToken(CustomerDTO customerDTO) {

        return JWT.create()
                .withSubject(CUSTOMER_SUBJECT)
                .withClaim(EMAIL_CLAIM, customerDTO.getEmail())
                .withClaim(BANK_ACCOUNT_ID_CLAIM, customerDTO.getBankAccountId())
                .withClaim(ID_CLAIM, customerDTO.getId())
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .sign(Algorithm.HMAC256(SECRET));
    }

}
