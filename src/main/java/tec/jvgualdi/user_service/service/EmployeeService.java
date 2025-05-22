package tec.jvgualdi.user_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tec.jvgualdi.user_service.domain.entity.Employee;
import tec.jvgualdi.user_service.domain.entity.User;
import tec.jvgualdi.user_service.domain.enums.UserRole;
import tec.jvgualdi.user_service.dto.EmployeeRequest;
import tec.jvgualdi.user_service.dto.EmployeeResponse;
import tec.jvgualdi.user_service.mapper.EmployeeMapper;
import tec.jvgualdi.user_service.repository.EmployeeRepository;

@Service
public class EmployeeService
{
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UserService userService;

    public EmployeeService (
            EmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper
            , UserService userService
    ) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.userService = userService;
    }

    public EmployeeResponse getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest req) {
        User user = userService.createUser(req.email(),
                req.password(),
                UserRole.EMPLOYEE);

        userService.activateUser(user.getId());

        var emp = new Employee();
        emp.setUser(user);
        emp.setFullName(req.fullName());

        var saved = employeeRepository.save(emp);

        return employeeMapper.toResponse(saved);
    }
}
