package codingtechniques.service;

import java.util.List;
import java.util.Optional;

import codingtechniques.model.Employee;

public interface EmployeeService {

    void saveEmployee(Employee employee);

    Iterable<Employee> findEmployees();

        Optional<Employee> getEmployeeById(Long id);
    
    void deleteEmployee(Long id);

    List<Employee> findByFirstname(String firstname); 
    List<Employee> findByemail(String email); 
}
