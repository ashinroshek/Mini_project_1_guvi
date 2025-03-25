package codingtechniques.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codingtechniques.model.Employee;
import codingtechniques.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	public Iterable<Employee> findEmployees() {
		return employeeRepository.findAll();

	}

	@Override
	public Optional<Employee> getEmployeeById(Long id) {
		return employeeRepository.findById(id); 
	}

	@Override
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);

	}

	@Override
	public List<Employee> findByFirstname(String firstname) {
		return employeeRepository.findByFirstname(firstname); 
	}

	@Override
	public List<Employee> findByemail(String email) {
		return employeeRepository.findByEmail(email); 
	}

}
