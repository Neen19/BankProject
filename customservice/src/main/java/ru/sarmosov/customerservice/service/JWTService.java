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
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.customerservice.dto.AuthDTO;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.customerservice.dto.TokenResponse;
import ru.sarmosov.customerservice.entity.CustomerEntity;
import ru.sarmosov.customerservice.security.CustomerDetails;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class JWTService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CustomerDetailsService customerDetailsService;
    private final ModelMapper modelMapper;

    @Value("${jwt.customer-subject}")
    private String CUSTOMER_SUBJECT;

    @Value("${jwt.phone-number-claim}")
    private String PHONE_NUMBER_CLAIM;

    @Value("${jwt.bank-account-id-claim}")
    private String BANK_ACCOUNT_ID_CLAIM;

    @Value("${jwt.id-claim}")
    private String ID_CLAIM;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.issuer}")
    private String ISSUER;



    public TokenResponse getCustomerToken(AuthDTO authDTO) throws BadCredentialsException {

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getPhoneNumber(),
                        authDTO.getPassword());

        authenticationManager.authenticate(authInputToken);

        System.out.println(authDTO.getPhoneNumber());

        CustomerDetails customerDetails = (CustomerDetails) customerDetailsService.loadUserByUsername(authDTO.getPhoneNumber());

        CustomerDTO dto = modelMapper.map(customerDetails.getCustomerEntity(), CustomerDTO.class);

        System.out.println(dto.toString());

        return new TokenResponse(generateToken(dto));
    }

    public CustomerEntity getCustomerByToken(String token) throws BadCredentialsException {
        String jwtToken = token.replace("Bearer ", "");
        try {
            String phoneNumber = jwtUtil.verifyTokenAndRetrievePhoneNumber(jwtToken).getPhoneNumber();
            CustomerDetails details = (CustomerDetails) customerDetailsService.loadUserByUsername(phoneNumber);
            return details.getCustomerEntity();

        } catch (JWTVerificationException e) {
            throw new BadCredentialsException("Invalid JWT token");
        }
    }

    public String generateToken(CustomerDTO customerDTO) {

        return JWT.create()
                .withSubject(CUSTOMER_SUBJECT)
                .withClaim(PHONE_NUMBER_CLAIM, customerDTO.getPhoneNumber())
                .withClaim(BANK_ACCOUNT_ID_CLAIM, customerDTO.getBankAccountId())
                .withClaim(ID_CLAIM, customerDTO.getId())
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .sign(Algorithm.HMAC256(SECRET));
    }

}
