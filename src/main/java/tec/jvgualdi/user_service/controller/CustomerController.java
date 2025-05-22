package tec.jvgualdi.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.validation.Valid;
import tec.jvgualdi.user_service.dto.CustomerRegistrationRequest;
import tec.jvgualdi.user_service.dto.CustomerResponse;
import tec.jvgualdi.user_service.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(
            @Valid @RequestBody CustomerRegistrationRequest dto,
            UriComponentsBuilder uriBuilder
    ) {
        var customer = customerService.registerCustomer(dto);

        var uri = uriBuilder.path("/customers/{id}").buildAndExpand(customer.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("token") String token) {
        customerService.verifyCustomer(token);
        return ResponseEntity.ok("Customer verified successfully");
    }
}
