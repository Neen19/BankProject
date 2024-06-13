package ru.sarmosov.bankstarter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.properties.BankStarterProperties;
import ru.sarmosov.bankstarter.util.JWTUtil;

@ExtendWith(MockitoExtension.class)
public class JWTUtilTest {

    @Mock
    private BankStarterProperties bankStarterProperties;

    @InjectMocks
    private JWTUtil jwtUtil;

    private String sampleToken;
    private final String secret = "mysecret";
    private final String customerSubject = "customer";
    private final String issuer = "issuer";
    private final String idClaim = "id";
    private final String bankAccountIdClaim = "bankAccountId";
    private final String phoneNumberClaim = "phoneNumber";
    private final Long id = 123L;
    private final Long bankAccountId = 456L;
    private final String phoneNumber = "1234567890";

    @BeforeEach
    public void setup() {
        when(bankStarterProperties.getSECRET()).thenReturn(secret);
        when(bankStarterProperties.getCUSTOMER_SUBJECT()).thenReturn(customerSubject);
        when(bankStarterProperties.getISSUER()).thenReturn(issuer);
        when(bankStarterProperties.getID_CLAIM()).thenReturn(idClaim);
        when(bankStarterProperties.getBANK_ACCOUNT_ID_CLAIM()).thenReturn(bankAccountIdClaim);
        when(bankStarterProperties.getEMAIL_CLAIM()).thenReturn(phoneNumberClaim);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        sampleToken = JWT.create()
                .withSubject(customerSubject)
                .withIssuer(issuer)
                .withClaim(idClaim, id)
                .withClaim(bankAccountIdClaim, bankAccountId)
                .withClaim(phoneNumberClaim, phoneNumber)
                .sign(algorithm);
    }

    @Test
    public void testVerifyTokenAndRetrievePhoneNumber() {
        CustomerDTO customerDTO = jwtUtil.verifyTokenAndRetrievePhoneNumber(sampleToken);

        assertEquals(id, customerDTO.getId());
        assertEquals(bankAccountId, customerDTO.getBankAccountId());
        assertEquals(phoneNumber, customerDTO.getEmail());
    }
}
