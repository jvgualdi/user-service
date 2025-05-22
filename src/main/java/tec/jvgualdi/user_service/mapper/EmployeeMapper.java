package tec.jvgualdi.user_service.mapper;

import org.mapstruct.Mapper;
import tec.jvgualdi.user_service.domain.entity.Employee;
import tec.jvgualdi.user_service.dto.EmployeeRequest;
import tec.jvgualdi.user_service.dto.EmployeeResponse;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeResponse toResponse(Employee employee);
    Employee toEntity(EmployeeRequest employeeRequest);

}
