package ru.sarmosov.bankstarter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("jwt")
public class BankStarterProperties {

    public String PHONE_NUMBER_CLAIM = "phoneNumber";
    public String CUSTOMER_SUBJECT = "customerSubject";
    public String BANK_ACCOUNT_ID_CLAIM = "bankid";
    public String ID_CLAIM = "id";
    public String SECRET = "secret-string";
    public String ISSUER = "admin";
}
