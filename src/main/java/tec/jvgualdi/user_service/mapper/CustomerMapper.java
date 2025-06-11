package tec.jvgualdi.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tec.jvgualdi.user_service.domain.entity.Customer;
import tec.jvgualdi.user_service.dto.CustomerRegistrationRequest;
import tec.jvgualdi.user_service.dto.CustomerResponse;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(source = "customer.user.email", target = "email")
    CustomerResponse toResponse(Customer customer);

    Customer toEntity(CustomerRegistrationRequest customerRegistrationRequest);

}
