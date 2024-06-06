package ru.sarmosov.customerservice.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sarmosov.customerservice.dto.AuthDTO;
import ru.sarmosov.customerservice.dto.TokenResponse;
import ru.sarmosov.customerservice.entity.Customer;
import ru.sarmosov.customerservice.security.CustomerDetails;
import ru.sarmosov.customerservice.util.JWTUtil;



@Log4j2
@Service
@RequiredArgsConstructor
public class JWTService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CustomerDetailsService customerDetailsService;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse getCustomerToken(AuthDTO authDTO) throws BadCredentialsException {

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getPhoneNumber(),
                        authDTO.getPassword());

        authenticationManager.authenticate(authInputToken);

        return new TokenResponse(jwtUtil.generateToken(authDTO.getPhoneNumber()));
    }

    public Customer getCustomerByToken(String token) throws BadCredentialsException {
        String jwtToken = token.replace("Bearer ", "");
        try {
            String phoneNumber = jwtUtil.verifyTokenAndRetrievePhoneNumber(jwtToken);
            CustomerDetails details = (CustomerDetails) customerDetailsService.loadUserByUsername(phoneNumber);
            return details.getCustomer();

        } catch (JWTVerificationException e) {
            throw new BadCredentialsException("Invalid JWT token");
        }
    }

}
