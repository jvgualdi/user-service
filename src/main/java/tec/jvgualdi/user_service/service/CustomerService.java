package tec.jvgualdi.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tec.jvgualdi.user_service.domain.entity.CustomerAddress;
import tec.jvgualdi.user_service.domain.enums.UserRole;
import tec.jvgualdi.user_service.dto.CustomerAddressRequest;
import tec.jvgualdi.user_service.dto.CustomerRegistrationRequest;
import tec.jvgualdi.user_service.domain.entity.User;
import tec.jvgualdi.user_service.domain.entity.Customer;
import tec.jvgualdi.user_service.dto.CustomerResponse;
import tec.jvgualdi.user_service.dto.EmailRequest;
import tec.jvgualdi.user_service.mapper.CustomerMapper;
import tec.jvgualdi.user_service.repository.CustomerRepository;
import tec.jvgualdi.user_service.security.TokenServiceJWT;


@Service
@RequiredArgsConstructor
public class CustomerService {

    @Value("${app.base-url}")
    private String baseUrl;
    @Value("${app.email.from}")
    private String emailFrom;
    private final UserService userService;
    private final TokenServiceJWT tokenService;
    private  final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final EmailPublisher emailPublisher;

    @Transactional
    public CustomerResponse registerCustomer(CustomerRegistrationRequest dto) {
        User user = userService.createUser(
                dto.email(),
                dto.password(),
                UserRole.CUSTOMER
        );

        String emailToken = tokenService.generateEmailVerificationToken(user);

        Customer customer = createProfile(
                user,
                dto.name(),
                dto.phoneNumber(),
                dto.address()
        );
        String link = baseUrl + "/customers/verify?token=" + emailToken;
        EmailRequest emailReq = new EmailRequest(
                user.getEmail(),
                "Please verify your account",
                "Hi " + dto.name() + ",\n\nClick here to verify: " + link,
                emailFrom,
                "JVGualdi User Service <" +emailFrom + ">"
        );

        // publish to RabbitMQ
        emailPublisher.publish(emailReq);

        return customerMapper.toResponse(customer);
    }

    private Customer createProfile(
            User user,
            String name,
            String phoneNumber,
            CustomerAddressRequest adrDto
    ) {
        CustomerAddress adr = new CustomerAddress();
        adr.setStreet(adrDto.street());
        adr.setCity(adrDto.city());
        adr.setState(adrDto.state());
        adr.setCountry(adrDto.country());
        adr.setZipCode(adrDto.zipCode());
        adr.setNeighborhood(adrDto.neighborhood());
        adr.setNumber(adrDto.number());

        Customer c = new Customer();
        c.setUser(user);
        c.setName(name);
        c.setPhoneNumber(phoneNumber);
        c.setAddress(adr);
        return customerRepository.save(c);
    }


    @Transactional
    public void verifyCustomer(String token) {
        Long userId = tokenService.validateEmailVerificationToken(token);
        userService.activateUser(userId);
    }
}
