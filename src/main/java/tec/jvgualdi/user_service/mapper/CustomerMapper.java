package tec.jvgualdi.user_service.mapper;

import org.mapstruct.Mapper;
import tec.jvgualdi.user_service.domain.entity.Customer;
import tec.jvgualdi.user_service.dto.CustomerRegistrationRequest;
import tec.jvgualdi.user_service.dto.CustomerResponse;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer);

    Customer toEntity(CustomerRegistrationRequest customerRegistrationRequest);

}
