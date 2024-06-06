//package ru.sarmosov.account;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import ru.sarmosov.account.dto.CustomerDTO;
//
//public class Test {
//    public static void main(String[] args) {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        String url = "http://localhost:8080/api/auth/customer";
//        HttpHeaders headers = new HttpHeaders();
//        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdXN0b21lclN1YmplY3QiLCJwaG9uZU51bWJlciI6Ijc3Nzc3NzciLCJpc3MiOiJhZG1pbiIsImV4cCI6MTcxNzMzNzA3MSwiaWF0IjoxNzE3MzMzNDcxfQ.l6PT84ADcEOLFqEZtiDdXoPdfmhoDztg0jhxbyJoO-k";
//        headers.add("Authorization", token);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<CustomerDTO> dto = restTemplate.exchange(
//                url,                // URL
//                HttpMethod.GET,    // Метод HTTP
//                entity,             // HttpEntity (заголовки и параметры)
//                CustomerDTO.class          // Ожидаемый тип ответа
//        );
//        System.out.println(dto.getBody());
//    }
//}
