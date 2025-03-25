package codingtechniques.repository;

import codingtechniques.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	 List<Employee> findByFirstname(String firstname);
	 List<Employee> findByEmail(String email);
	 }